using NetworkCalendar.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NetworkCalendar
{
    public interface ITcpService
    {
        List<Event> GetCalender();
        EventDetailed GetSingleEvent(int id);
        string DeleteEvent(int id);
        string CreateEvent(EventDetailed eventDetailed);
    }
}
