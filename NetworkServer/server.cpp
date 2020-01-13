#include "server.hpp"


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

void* readMessage(void *t_data)
{
    //free resources when killed
    pthread_detach(pthread_self());
    struct threadFd *th_data = (struct threadFd*)t_data;
    
    
    char buf [256];
    char buffer[256];
    
    read((*th_data).clientFd, buffer, 256);
    std::string message(buffer);
    
    int clientFd = (*th_data).clientFd;
        
    if ((message.substr(0, 12).compare("GET_CALENDAR")) == 0)
    {
        strcpy(buf, message.substr(12, 14).c_str());
        //ZWRACA LICZBĘ STRON A NASTĘPNIE TYLE WIADOMOŚCI
        //GET_All_events
    }
    else if ((message.substr(0, 12).compare("GET_CALENSSS")) == 0)
    {
        int event_id = atoi(message.substr(13, 16).c_str());
        //GET_Specific_event
    }
    else if ((message.substr(0, 12).compare("ADD_CALENDAR")) == 0)
    {
        strcpy(buf, message.substr(12, 14).c_str());
        //ADD_event
    }
    else if ((message.substr(0, 15).compare("REMOVE_CALENDAR")) == 0)
    {
        strcpy(buf, message.substr(12, 14).c_str());
        //REMOVE_event
    }
    else {
        strcpy(buf, "NIE ZNALEZIONO\n");
    }
    // koniec
    
    
    FD_SET((*th_data).clientFd, &mask);
    
    write((*th_data).clientFd, buf, 20);
    memset(buf,0,strlen(buf));
    close((*th_data).clientFd);
    
    pthread_exit(NULL);
}

void processRequest(int connection_socket_descriptor)
{
    int create_result = 0;

    //thread ref
    pthread_t thread1;

    //create struct to send it to thread
    struct threadFd *t_data;
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
    
    FD_ZERO(&mask);
    FD_ZERO(&wmask);
    FD_ZERO(&rmask);
    fdmax = fd;
    while(1)
    {
        struct cln* c = new cln;
        FD_SET(fd, &rmask);
        wmask = mask;
        timeoutVal.tv_sec = 5 * 60;
        timeoutVal.tv_usec = 0;
        rc = select(fdmax + 1, &rmask, &wmask, (fd_set*) 0, &timeoutVal);
        if(rc == 0)
        {
            printf("Timeout.\n");
            continue;
        }
        fda = rc;
        if(FD_ISSET(fd, &rmask))
        {
            fda -= 1;
            slt = sizeof(c->caddr);
            c->cfd = accept(fd, (struct sockaddr*)&c->caddr, &slt);
            printf("new connection from: %s\n", inet_ntoa((struct in_addr)c->caddr.sin_addr));
            
            processRequest(c->cfd);

        }
    }

    close(fd);

    return EXIT_SUCCESS;
}
