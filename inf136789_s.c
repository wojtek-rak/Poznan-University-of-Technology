#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

#include<unistd.h>

#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>


#define LoggingMessageType 101
#define SubscribeMessageType 102
#define PrimaryIdMessageType 103
#define MainMessageQueuesId 110
#define MaxNumberOfUsers 99

typedef struct User
{
    int primaryId;
    int id;
    char name[100];
} User;

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

struct SubscribeMessage subscribeMessage;
struct LoggingMessage loggingMessage;
struct Message message;
struct User users[MaxNumberOfUsers];
struct PrimaryIdMessage primaryIdMessage;

int msgId;

void SendPrimaryIdMessage(int primaryId, char name[100])
{
    primaryIdMessage.type = PrimaryIdMessageType;
    strcpy(primaryIdMessage.name, name);
    primaryIdMessage.primaryId = primaryId;
    
    if(msgsnd(msgId, &primaryIdMessage, 104, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

int main(int argc, char* argv[])
{
    printf("Welcome to Server app!\n");
    
    msgId = msgget(MainMessageQueuesId, 0600 | IPC_CREAT);
    if(msgId == -1)
    {
        printf("Unexpected error\n");
        exit(1);
    }
    int numberOfUsers = 1;
    while(1)
    {
        //Message about logging
        int logging = msgrcv(msgId, &loggingMessage, 104, LoggingMessageType, IPC_NOWAIT);
        if(logging != -1)
        {
            printf("Id: %d\n", loggingMessage.id);
            printf("Name: %s\n", loggingMessage.name);
            printf("PrimaryId: %d\n", numberOfUsers);
            users[numberOfUsers].id = loggingMessage.id;
            strcpy(users[numberOfUsers].name, loggingMessage.name);
            users[numberOfUsers].primaryId = numberOfUsers;
            SendPrimaryIdMessage(numberOfUsers, loggingMessage.name);
            numberOfUsers++;
        }
        
        //Message about subscription
        int subscribe = msgrcv(msgId, &subscribeMessage, 117, SubscribeMessageType, IPC_NOWAIT);
        if(subscribe != -1)
        {
            int primaryId;
            printf("Name: %s\n", subscribeMessage.name);
            printf("Type To Subscribe: %li\n", subscribeMessage.typeToSubscribe);
            printf("Notification: %d\n", subscribeMessage.notification);
            printf("Transition: %d\n", subscribeMessage.transition);
            
            for(int i = 1; i <= numberOfUsers; i++)
            {
                if(strcmp(subscribeMessage.name, users[i].name) == 0)
                {
                    primaryId = users[i].primaryId;
                    printf("Primary Id: %d\n", primaryId);
                }
            }
            printf("PId user: %d\n", primaryId);
        }
        
        
        sleep(1);
        printf("\n");
        
        
//        int k;
//        printf("Sizeof int: %d\n", sizeof(k));
//        short x;
//        long y;
//        char z[100];
//        printf("Sizeof short: %d\n", sizeof(x));
//        printf("Sizeof long: %d\n", sizeof(y));
//        printf("Sizeof chartab: %d\n", sizeof(z));
//        printf("Sizeof ALL: %d\n", sizeof(z)+sizeof(x)+sizeof(y)+sizeof(x));
    }
    
    
    
    return 0;
}








