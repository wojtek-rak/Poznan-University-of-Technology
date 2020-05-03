using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NetworkCalendar.Models
{
    public class Event
    {
        public string Id { get; set; }
        public string Title { get; set; }
        public string Owner { get; set; }
        public DateTime Date { get; set; }
    }
}
