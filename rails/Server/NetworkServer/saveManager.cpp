#include "saveManager.hpp"
#include <fstream>
#include <iostream>
using namespace std;

SaveManager::SaveManager()
{
}

void SaveManager::SaveEvents(int numberOfEvents, Event events[50])
{
    fstream outFileEvent("events.txt", ios::out);
    for (int i = 0; i < numberOfEvents; i++)
    {
        if (outFileEvent.good())
        {
            cout << "\n SAVE Event: " << i;
            outFileEvent << "t " << events[i].title << " o " << events[i].owner << " d " << events[i].date << " c " << events[i].description;
            
            outFileEvent << "\n";
            outFileEvent.flush();

        }
    }
    outFileEvent.close();
}


void SaveManager::SaveEvents(int numberOfEvents, Event events[50], bool deadLetter, int id)
{
    fstream outFileEvent("events.txt", ios::out);
    for (int i = 0; i < numberOfEvents; i++)
    {
        if (outFileEvent.good())
        {
            if (deadLetter && id == i) continue;
            cout << "\n SAVE Event: " << i;
            outFileEvent << "t " << events[i].title << " o " << events[i].owner << " d " << events[i].date << " c " << events[i].description;
            
            outFileEvent << "\n";
            outFileEvent.flush();

        }
    }
    outFileEvent.close();
}
