using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace NetworkCalendar.Models
{
    public class EventDetailed
    {
        [StringLength(20, MinimumLength = 3, ErrorMessage = "Bad length")]
        [RegularExpression(@"^[^\s\,\.]+$", ErrorMessage = "Title Cannot Have this characters")]
        public string Title { get; set; }
        [StringLength(10, MinimumLength = 3, ErrorMessage = "Bad length")]
        [RegularExpression(@"^[^\s\,\.]+$", ErrorMessage = "Owner Cannot Have this characters")]
        public string Owner { get; set; }
        public DateTime Date { get; set; }
        [RegularExpression(@"^[^\,\.]+$", ErrorMessage = "Owner Cannot Have this characters")]
        [StringLength(200, MinimumLength = 3, ErrorMessage = "Invalid")]
        public string Description { get; set; }
    }
}
