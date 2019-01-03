#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <curses.h>

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
#define NewTypeMessageType 104
#define NewTypeResponseMessageType 105
#define NormalMessageType 106
#define MainMessageQueuesId 110
#define MaxNumberOfMessageTypes 1000
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

typedef struct NormalMessage {
    long type;
    int typeMessage;
    int priority;
    char text[256];
} NormalMessage;

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

typedef struct Subscriptions {
    int primaryId[MaxNumberOfUsers];
    int count;
} Subscriptions;

typedef struct NewTypeMessage {
    long type;
    int typeToCreate;
    char name[100];
} NewTypeMessage;

typedef struct NewTypeResponseMessage {
    long type;
    char response[256];
} NewTypeResponseMessage;

struct NewTypeResponseMessage newTypeResponseMessage;
struct NewTypeMessage newTypeMessage;
struct SubscribeMessage subscribeMessage;
struct LoggingMessage loggingMessage;
struct NormalMessage normalMessage;
struct User users[MaxNumberOfUsers];
struct PrimaryIdMessage primaryIdMessage;
struct Subscriptions subscriptions[MaxNumberOfMessageTypes];

int activeTypes[MaxNumberOfMessageTypes];
int msgId;
int numberOfUsers = 1;

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

void SendNewTypeResponseMessage(char response[256])
{
    newTypeResponseMessage.type = NewTypeResponseMessageType;
    strcpy(newTypeResponseMessage.response, response);
    
    if(msgsnd(msgId, &newTypeResponseMessage, 256, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

// Thread Safe Method Using global variable
void SendNormalMessage(int type)
{
    normalMessage.type = type;
    
    if(msgsnd(msgId, &normalMessage, 266, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

void SetUp()
{
    for (int i = 0; i < MaxNumberOfMessageTypes ; i++)
    {
        subscriptions[i].count = 0;
        activeTypes[i] = 0;
    }
    //msgctl(MainMessageQueuesId, IPC_RMID, 0);
}

int GetUserPrimaryId(char name[100])
{
    int primaryId;
    for(int i = 1; i <= numberOfUsers; i++)
    {
        if(strcmp(name, users[i].name) == 0)
        {
            primaryId = users[i].primaryId;
            //printf("Primary Id: %d\n", primaryId);
        }
    }
    return primaryId;
}

int main(int argc, char* argv[])
{
    SetUp();
    printf("Welcome to Server app!\n");
    
    msgId = msgget(MainMessageQueuesId, 0600 | IPC_CREAT);
    if(msgId == -1)
    {
        printf("Unexpected error\n");
        exit(1);
    }
    
    while(1)
    {
        //printf("%d", kbhit());
        
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
            printf("Name: %s\n", subscribeMessage.name);
            printf("Type To Subscribe: %li\n", subscribeMessage.typeToSubscribe);
            //printf("Notification: %d\n", subscribeMessage.notification);
            //printf("Transition: %d\n", subscribeMessage.transition);
            
            int primaryId = GetUserPrimaryId(subscribeMessage.name);
            
            printf("PId user: %d\n", primaryId);
            subscriptions[subscribeMessage.typeToSubscribe].count++;
            int countSub = subscriptions[subscribeMessage.typeToSubscribe].count;
            subscriptions[subscribeMessage.typeToSubscribe].primaryId[countSub] = primaryId;
        }
        
        //Message about new type
        int newMessageType = msgrcv(msgId, &newTypeMessage, 104, NewTypeMessageType, IPC_NOWAIT);
        if(newMessageType != -1)
        {
            if(activeTypes[newTypeMessage.typeToCreate] == 1)
            {
                //already exist
                //int primaryId = GetUserPrimaryId(newTypeMessage.name);
                printf("Failed with Create New Type %d\n", newTypeMessage.typeToCreate);
                
                char response[256] = "Fail to create new type, already exist";
                SendNewTypeResponseMessage(response);
                
            }
            else
            {
                //succes
                printf("Succes with Create New Type %d\n", newTypeMessage.typeToCreate);
                
                activeTypes[newTypeMessage.typeToCreate] = 1;
                char response[256] = "Succesfull create new type!";
                SendNewTypeResponseMessage(response);
            }
        }
        //Normal message
        int newMessageNormal = msgrcv(msgId, &normalMessage, 264, NormalMessageType, IPC_NOWAIT);
        if(newMessageNormal != -1)
        {
            if(activeTypes[normalMessage.typeMessage] != 1)
            {
                printf("Create New Type %d\n", normalMessage.typeMessage);
                activeTypes[newTypeMessage.typeToCreate] = 1;
            }
            
            printf("NEW MESSAGE FOR RESENDING \n");
            printf("Message Type: %d\n", normalMessage.typeMessage);
            printf("Message Priority: %d \n", normalMessage.priority);
            printf("Message Text:\n%s \n", normalMessage.text);

            //SENDING
            Subscriptions sub = subscriptions[normalMessage.typeMessage];
            for(int i = 1; i <= sub.count; i++)
            {
                printf("\n sending... to PID: %d\n", sub.primaryId[i]);
                SendNormalMessage(sub.primaryId[i]);
            }
            
            
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








