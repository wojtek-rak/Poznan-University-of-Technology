#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>

#include<unistd.h>

#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include <stdlib.h>


#define LoggingMessageType 101
#define SubscribeMessageType 102
#define PrimaryIdMessageType 103
#define MainMessageQueuesId 110

typedef struct SubscribeMessage {
    long type;
    char name[100];
    long typeToSubscribe;
    int notification;
    int transition;
} SubscribeMessage;

typedef struct Message {
    long type;
    char text[256];
} Message;

typedef struct PrimaryIdMessage {
    long type;
    char name[100];
    int primaryId;
} PrimaryIdMessage;

typedef struct LoggingMessage {
    long type;
    int id;
    char name[100];
} LoggingMessage;

struct LoggingMessage loggingMessage;
struct SubscribeMessage subscribeMessage;
struct Message message;
struct PrimaryIdMessage primaryIdMessage;

int msgId;
int primaryId;

void SendMessage(int type, char text[256])
{
    message.type = type;
    strcpy(message.text, text);
    
    if(msgsnd(msgId, &message, 256, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

void SendLoggingMessage(int id, char name[100])
{
    loggingMessage.type = LoggingMessageType;
    strcpy(loggingMessage.name, name);
    loggingMessage.id = id;
    
    if(msgsnd(msgId, &loggingMessage, 104, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

void SendSubscribeMessage(char name[100], long typeToSubscribe, short notification, short transition)
{
    subscribeMessage.type = SubscribeMessageType;
    strcpy(subscribeMessage.name, name);
    subscribeMessage.typeToSubscribe = typeToSubscribe;
    subscribeMessage.notification = notification;
    subscribeMessage.transition = transition;
    
    if(msgsnd(msgId, &subscribeMessage, 117, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

int main(int argc, char* argv[])
{
    printf("Welcome to Client app!\n");
    int id;
    char name[100];
    printf("Set Id (number): ");
    scanf("%d", &id);
    printf("Set Name : ");
    scanf("%s", name);
    printf("Id : %d\n",id);
    printf("NAME : %s\n",name);
    
    msgId = msgget(MainMessageQueuesId, 0600 | IPC_CREAT);
    if(msgId == -1)
    {
        printf("Unexpected error\n");
        exit(1);
    }
    
    SendLoggingMessage(id, name);
    
    int getResponse = 1;
    int deadCount = 0;
    while (getResponse)
    {
        if(deadCount > 5)
        {
            printf("(Un)expected logging error\n");
            exit(1);
        }
        int response = msgrcv(msgId, &primaryIdMessage, 104, PrimaryIdMessageType, 0);
        if(response == -1)
        {
            perror("Unexpected logging error: ");
            exit(1);
        }
        else
        {
            if(strcmp(name, primaryIdMessage.name) == 0)
            {
                primaryId = primaryIdMessage.primaryId;
                printf("Primary Id: %d\n", primaryId);
                getResponse = 0;
            }
            else
            {
                deadCount++;
                printf("(Un)expected logging error\n");
            }
        }
    }
    
    while(1)
    {
        
        printf("Press s for suscribe, or m for read message: ");
        char choice = getchar();
        if(choice == 's')
        {
            long subType;
            int subNotyfication;
            int subTransition;
            printf("Subscribe\n");
            printf("Subscribe type: ");
            scanf("%li", &subType);
            printf("Subscribe notification 1 or 0: ");
            scanf("%d", &subNotyfication);
            printf("Subscribe transition 1 or 0: ");
            scanf("%d", &subTransition);
            // WALIDACJA! NUMERU!
            SendSubscribeMessage(name, subType, subNotyfication, subTransition);
            printf("Subscribed Sucessfully!\n");
        }
        else if (choice == 'm')
        {
            
        }
        
            
    }
    
//    char buf[1000];
//    char buf2[1000];
//    char buf3[1000];
//    char in[100];
//    int x;
//    //char* y = argv[argc - 1];
//    //printf("\nargc %d\n ",argc);
//    
//    int mid = msgget(0x123, 0777 | IPC_CREAT);
//    printf("\nMID: %d\n ", mid);
//    struct msgbuf
//    {
//        long type;
//        char k[100];
//    } my_msg;
//
//
//    //int fd = open("fl1", O_WRONLY);
//
//    //write(fd, "Hello", 5);
//
//    //dup2(fd, 1);
//    //execlp("ls", "ls", "-l", 0);
//
//    //strcpy(my_msg.text, i);
//    printf("\nInput: ");
//    scanf("%s", my_msg.k);
//    my_msg.type = 5;
//    //my_msg.k = in;
//    msgsnd(mid, &my_msg, 100, 0);
    
    
    
    return 0;
}







