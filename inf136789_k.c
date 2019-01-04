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
#define NewTypeMessageType 104
#define NewTypeResponseMessageType 105
#define NormalMessageType 106
#define MainMessageQueuesId 110
#define MaxNumberOfMessageTypes 1000

#define SizeOfNormallMessage 18

typedef struct SubscriptionList {
    long typeOfSubscribe;
    int notification;
    int transition;
} SubscriptionList;

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
    char text[10];
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
struct LoggingMessage loggingMessage;
struct SubscribeMessage subscribeMessage;
struct NormalMessage normalMessage;
struct PrimaryIdMessage primaryIdMessage;
struct SubscriptionList subscriptionList[MaxNumberOfMessageTypes];
struct NormalMessage waitingMessages[500];
int waitingMessagesCount = 0;
int subscriptionCount = 0;

int msgId;
int primaryId;

void SendNormalMessage(int type, int priority, char text[10])
{
    normalMessage.type = NormalMessageType;
    normalMessage.typeMessage = type;
    normalMessage.priority = priority;
    strcpy(normalMessage.text, text);
    
    if(msgsnd(msgId, &normalMessage, SizeOfNormallMessage, 0) == -1)
    {
        perror("Message sending error");
        exit(1);
    }
}

void SendNewTypeMessage(int typeToCreate, char name[100])
{
    newTypeMessage.type = NewTypeMessageType;
    strcpy(newTypeMessage.name, name);
    newTypeMessage.typeToCreate = typeToCreate;
    
    if(msgsnd(msgId, &newTypeMessage, 104, 0) == -1)
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
    
    //Send loggin message
    SendLoggingMessage(id, name);
    
    //get response with primaryId for communication
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
    //main loop
    while(1)
    {
        
        
        char choice = getchar();
        //Send Subscribe message
        if(choice == 's')
        {
            long subType;
            int subNotyfication;
            int subTransition;
            
            printf("Subscribe\n");
            printf("Subscribe type >150 and <1000: ");
            scanf("%li", &subType);
            if(subType < 150) subType = 150;
            if(subType > 1000) subType = 1000;
            printf("Subscribe notification 1 or 0: ");
            scanf("%d", &subNotyfication);
            printf("Subscribe transition 1 or 0: ");
            scanf("%d", &subTransition);
            
            // WALIDACJA! NUMERU!
            subscriptionCount++;
            
            subscriptionList[subscriptionCount].typeOfSubscribe = subType;
            subscriptionList[subscriptionCount].notification = subNotyfication;
            subscriptionList[subscriptionCount].transition = subTransition;
            
            SendSubscribeMessage(name, subType, subNotyfication, subTransition);
            printf("Subscribed Sucessfully!\n");
        }
        //New Type
        else if (choice == 't')
        {
            int newType;
            printf("NewType beetwen 150-1000: ");
            scanf("%d", &newType);
            if(newType < 150) newType = 150;
            if(newType > 1000) newType = 1000;
            
            SendNewTypeMessage(newType, name);
            
            int response = msgrcv(msgId, &newTypeResponseMessage, 256, NewTypeResponseMessageType, 0);
            if(response == -1)
            {
                perror("Unexpected type error: ");
                exit(1);
            }
            printf("Message: %s\n", newTypeResponseMessage.response);
        }
        //Send normall message
        else if (choice == 'y')
        {
            int type;
            int priority;
            char text[10];
            
            printf("Send Message\n");
            printf("Message type >150 and <1000: ");
            scanf("%d", &type);
            if(type < 150) type = 150;
            if(type > 1000) type = 1000;
            printf("Message priority 1-100: ");
            scanf("%d", &priority);
            if(priority < 1) priority = 1;
            if(priority > 100) priority = 100;
            printf("Message Text (max 256 characters): ");
            //scanf("%s", text);
            fseek(stdin,0,SEEK_END);
            scanf("%10[^\n]", text);
            printf("REALLYNYGA");
            SendNormalMessage(type, priority, text);
        }
        //Get Messages and print one with the higher priority
        else if (choice == 'm')
        {
//            msgrcv(msgId, &normalMessage, 266, primaryId, 0);
//            printf("Message Recive Type: %d\n", normalMessage.typeMessage);
//            printf("Priority: %d\n", normalMessage.priority);
//            printf("Message Text:\n %s\n:", normalMessage.text);
            msgrcv(msgId, &normalMessage, 266, primaryId, 0);
            do
            {
                printf("MSGGet\n");
                waitingMessagesCount++;
                waitingMessages[waitingMessagesCount] = normalMessage;
            } while (msgrcv(msgId, &normalMessage, 266, primaryId, IPC_NOWAIT) != -1);
            int maxPriority = 0;
            int maxIndex = 0;
            NormalMessage maxMessage;
            for(int i = 1; i<= waitingMessagesCount; i++)
            {
                if(maxPriority < waitingMessages[i].priority)
                {
                    maxPriority = waitingMessages[i].priority;
                    maxMessage = waitingMessages[i];
                    maxIndex = i;
                }
            }
            //clear array
            waitingMessagesCount--;
            for(int i = 1; i<= waitingMessagesCount; i++)
            {
                if(i >= maxIndex)
                {
                    waitingMessages[i] = waitingMessages[i + 1];
                }
            }
            printf("Message Recive Type: %d\n", maxMessage.typeMessage);
            printf("Priority: %d\n", maxMessage.priority);
            printf("Message Text:\n %s\n:", maxMessage.text);
        }
        //Get Messages Async
        else if (choice == 'a')
        {
//            if(msgrcv(msgId, &normalMessage, 266, primaryId, IPC_NOWAIT) != -1)
//            {
//                printf("Message Recive Type: %d\n", normalMessage.typeMessage);
//                printf("Priority: %d\n", normalMessage.priority);
//                printf("Message Text:\n %s\n:", normalMessage.text);
//            }
            
            while (msgrcv(msgId, &normalMessage, 266, primaryId, IPC_NOWAIT) != -1)
            {
                printf("MSGGet\n");
                waitingMessagesCount++;
                waitingMessages[waitingMessagesCount] = normalMessage;
            }
            int iterator = waitingMessagesCount;
            for(int j = 1; j <= iterator; j++)
            {
                int maxPriority = 0;
                int maxIndex = 0;
                NormalMessage maxMessage;
                
                for(int i = 1; i<= waitingMessagesCount; i++)
                {
                    if(maxPriority < waitingMessages[i].priority)
                    {
                        maxPriority = waitingMessages[i].priority;
                        maxMessage = waitingMessages[i];
                        maxIndex = i;
                    }
                }
                //clear array
                waitingMessagesCount--;
                for(int i = 1; i<= waitingMessagesCount; i++)
                {
                    if(i >= maxIndex)
                    {
                        waitingMessages[i] = waitingMessages[i + 1];
                    }
                }
                printf("Message Recive Type: %d\n", maxMessage.typeMessage);
                printf("Priority: %d\n", maxMessage.priority);
                printf("Message Text:\n %s\n:", maxMessage.text);
            }
            
        }
        else
        {
            printf("\nPress y for send normall message, s for suscribe, or m for read message, a for read async, t for new type: \n");
        }
        
            
    }
    
    //int subPriority;
    //printf("Priority 1-100: ");
    //scanf("%d", &subPriority);
    //int priority;
    
    
    
    return 0;
}







