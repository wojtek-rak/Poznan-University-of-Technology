using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NetworkCalendar.Models
{
    public class Day
    {
        public int Number { get; set; }
        public List<Event> Events { get; set; } = new List<Event>();
        public bool IsEvent { get; set; } = false;
    }
}
