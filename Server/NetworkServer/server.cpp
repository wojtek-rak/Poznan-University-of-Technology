#include "server.hpp"

int counter = 0;

struct cln
{
    int cfd;
    struct sockaddr_in caddr;
};

struct threadFd
{
    int clientFd;
};

fd_set mask;
fd_set wmask;
fd_set rmask;
int fdmax, fda, rc;
struct timeval timeoutVal;
Event events[50];
int lockedEvent[50];
int numberOfEvents;

void* readMessage(void *t_data)
{
    //free resources when killed
    pthread_detach(pthread_self());
    struct threadFd *th_data = (struct threadFd*)t_data;
    
    
    char buf [256];
    char buffer[256];
    int connection = 1;
    int clientFd = (*th_data).clientFd;
    int ctn = 0;
    while(connection == 1)
    {
        try
        {
            memset(buffer, 0, sizeof(buffer));
            read(clientFd, buffer, 256);
            std::string message(buffer);
            std::cout << "FD: " << clientFd << " get message: " << message <<  std::endl;
            if(message != "")
            {
                if ((message.substr(0, 12).compare("GET_CALENDAR")) == 0)
                {
                    //ZWRACA LICZBĘ STRON A NASTĘPNIE TYLE WIADOMOŚCI
                    std::string s = std::to_string(numberOfEvents / 7 + 1);
                    s += "<XD>";
                    strcpy(buf, s.c_str());
                    
                    //FD_SET((*th_data).clientFd, &mask);
                    write((*th_data).clientFd, buf, 256);
                    std::cout << "FD: " << clientFd << " send message: " << buf <<  std::endl;
                    memset(buf,0,strlen(buf));
                    
                    int lastVal = 50;
                    std::string res = "";
                    for (int i = 0; i < numberOfEvents / 7 + 1; i++)
                    {
                        int counter = 0;
                        for(int j = lastVal - 1; j > -1; j--)
                        {
                            lastVal = j;
                            if(lockedEvent[j] != -1)
                            {
                                if(counter > 0) res += ".";
                                res += std::to_string(j) + "," + events[j].date + "," + events[j].title;
                                
                                counter++;
                                if(counter >= 7) break;
                            }
                        }
                        
                        res += "<XD>";
                        strcpy(buf, res.c_str());
                        res = "";
                        //FD_SET(clientFd, &mask);
                        usleep(30);
                        write(clientFd, buf, 256);
                        std::cout << "FD: " << clientFd << " send message: " << buf <<  std::endl;
                        memset(buf,0,strlen(buf));
                    }
                }
                else if ((message.substr(0, 16).compare("GET_SINGLE_EVENT")) == 0)
                {
                    //DONE
                    int event_id = atoi(message.substr(17, 20).c_str());

                    std::string mes = events[event_id].title + "." + events[event_id].owner + "." + events[event_id].date + "." + events[event_id].description;
                    
                    mes += "<XD>";
                    strcpy(buf, mes.c_str());
                    
                    //FD_SET(clientFd, &mask);
                    
                    write(clientFd, buf, 256);
                    memset(buf,0,strlen(buf));
                }
                else if ((message.substr(0, 9).compare("ADD_EVENT")) == 0)
                {
                    std::string title = message.substr(10, 20);
                    std::string owner = message.substr(31, 10);
                    std::string date = message.substr(42, 6);
                    std::string description = message.substr(49, 200);
                    for (int i = 0; i < 50; i++)
                    {
                        if(lockedEvent[i] == -1)
                        {
                            lockedEvent[i] = i;
                            events[i].title = title;
                            events[i].owner = owner;
                            events[i].date = date;
                            events[i].description = description;
                            numberOfEvents++;
                            SaveManager::SaveEvents(numberOfEvents, events);
                            strcpy(buf, "DONE<XD>");
                            //FD_SET(clientFd, &mask);
                            
                            write(clientFd, buf, 256);
                            std::cout << "FD: " << clientFd << " send message: " << buf <<  std::endl;
                            memset(buf,0,strlen(buf));
                            break;
                        }
                    }
                }
                else if ((message.substr(0, 12).compare("REMOVE_EVENT")) == 0)
                {
                    int event_id = atoi(message.substr(13, 16).c_str());
                    numberOfEvents--;
                    lockedEvent[event_id] = -1;
                    SaveManager::SaveEvents(numberOfEvents, events, true, event_id);
                    strcpy(buf, "DONE<XD>");
                    //FD_SET(clientFd, &mask);
                    
                    write(clientFd, buf, 256);
                    std::cout << "FD: " << clientFd << " send message: " << buf <<  std::endl;
                    memset(buf,0,strlen(buf));
                }
                else {
                    strcpy(buf, "ERROR NOT FOUND<XD>");
                    //FD_SET(clientFd, &mask);
                    write(clientFd, buf, 256);
                    std::cout << "FD: " << clientFd << " send message: " << buf <<  std::endl;
                    memset(buf,0,strlen(buf));

                }
            }
            else{
                ctn++;
                if(ctn > 100)
                {
                    close(clientFd);
                    pthread_exit(NULL);
                }
            }
        }
        catch(...)
        {
            close(clientFd);
            pthread_exit(NULL);
        }
        
    }
    close(clientFd);
    pthread_exit(NULL);
}

void processRequest(int connection_socket_descriptor)
{
    counter++;
    if (counter > 10) return;
    int create_result = 0;

    //thread ref
    pthread_t thread1;

    //create struct to send it to thread
    struct threadFd *t_data = new threadFd;
    (*t_data).clientFd = connection_socket_descriptor;
    
    create_result = pthread_create(&thread1, NULL, readMessage, (void *)t_data);
    if (create_result)
    {
       printf("Error creating thread: %d\n", create_result);
       exit(-1);
    }

    printf("Created thread: %u \n", connection_socket_descriptor);
}




int main(int argc, char *argv[]) {
    //INIT DATA
    
    numberOfEvents = 0;
    for(int i = 0; i < 50; i++)
    {
        lockedEvent[i] = -1;
    }
    
//    events[0].title = "Wdrozenie";
//    events[0].owner = "wrak";
//    events[0].date = "200103";
//    events[0].description = "wdrozenie zaplanowane na godzine 15";
//
//    events[1].title = "Deploy";
//    events[1].owner = "krak";
//    events[1].date = "191229";
//    events[1].description = "Deploy zaplanowane na godzine 17";
//
//    events[2].title = "Retro";
//    events[2].owner = "crak";
//    events[2].date = "200119";
//    events[2].description = "Retro godzina 13";
//
//    lockedEvent[0] = 0;
//    lockedEvent[1] = 1;
//    lockedEvent[2] = 2;
//
//    numberOfEvents++;
//    numberOfEvents++;
//    numberOfEvents++;
//    SaveManager::SaveEvents(numberOfEvents, events);
    std::ifstream infileCustomers;
    char selector;
    std::string title;
    std::string description;
    std::string owner;
    std::string date;
    
    infileCustomers.open("events.txt");
    while (infileCustomers >> selector)
    {
        //std::stringstream ss;
        switch (selector)
        {
        case 't':
            infileCustomers >> title;
            events[numberOfEvents].title = title;
            break;
        case 'o':
            infileCustomers >> owner;
            events[numberOfEvents].owner = owner;
            break;
        case 'd':
            infileCustomers >> date;
            events[numberOfEvents].date = date;
            break;
        case 'c':
            //infileCustomers >> description;
            getline (infileCustomers, description);
            events[numberOfEvents].description = description;
            lockedEvent[numberOfEvents] = numberOfEvents;
            numberOfEvents++;
            break;
        default:
            break;
        }
    }
    infileCustomers.close();
    
    
    //INIT SERVER
    socklen_t slt;
    struct sockaddr_in s_addr;

    s_addr.sin_family = AF_INET;
    s_addr.sin_port = htons(1234);
    s_addr.sin_addr.s_addr = INADDR_ANY;
    int fd = socket(PF_INET, SOCK_STREAM, 0);
    int on = 1;

    setsockopt(fd, SOL_SOCKET, SO_REUSEADDR, (char*)&on, sizeof(on));

    bind(fd, (struct sockaddr*) & s_addr,sizeof(s_addr));
    listen(fd, 15);
    
    FD_ZERO(&rmask);
    fdmax = fd;
    while(1)
    {
        struct cln* c = new cln;
        FD_SET(fd, &rmask);
        //wmask = mask;
        timeoutVal.tv_sec = 5 * 60;
        timeoutVal.tv_usec = 0;
        rc = select(fdmax + 1, &rmask, &wmask, (fd_set*) 0, &timeoutVal);
        if(rc == 0)
        {
            printf("Timeout.\n");
            continue;
        }
        if(FD_ISSET(fd, &rmask))
        {
            slt = sizeof(c->caddr);
            c->cfd = accept(fd, (struct sockaddr*)&c->caddr, &slt);
            printf("new connection from: %s\n", inet_ntoa((struct in_addr)c->caddr.sin_addr));
            
            processRequest(c->cfd);
        }
    }

    close(fd);

    return EXIT_SUCCESS;
}
