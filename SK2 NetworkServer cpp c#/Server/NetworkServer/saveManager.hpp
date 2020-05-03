#ifndef saveManager_hpp
#define saveManager_hpp

#include <stdio.h>
#include "event.hpp"


class SaveManager
{
public:
    SaveManager();

    static void SaveEvents(int numberOfEvents, Event events[50]);
    static void SaveEvents(int numberOfEvents, Event events[50], bool deadLeatter, int id);
};


#endif /* saveManager_hpp */
