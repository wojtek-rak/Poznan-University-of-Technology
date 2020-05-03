#ifndef calendar_hpp
#define calendar_hpp

#include <string>

class Calendar
{
public:
    Calendar();
    
    bool privateCal;
    std::string events[50];
    std::string owner;

};



#endif
