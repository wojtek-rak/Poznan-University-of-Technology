#ifndef event_hpp
#define event_hpp

#include <string>

class Event
{
public:
    Event();
    
    std::string title;
    std::string description;
    std::string owner;
    std::string date;
};
#endif /* event_hpp */
