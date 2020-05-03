using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using NetworkCalendar.Models;

namespace NetworkCalendar.Controllers
{
    public class HomeController : Controller
    {
        private readonly ITcpService _tcpService;

        public HomeController(ITcpService tcpService)
        {
            _tcpService = tcpService;
        }

        public IActionResult Index()
        {
            ViewBag.Month = DateTime.Now.Month;
            return RedirectToAction("Calendar", "Home", new { year = 2020, month =  1});
        }
        [Route("Calendar/{year}/{month}")]
        public IActionResult Calendar(int year, int month, int delete = -2)
        {
            //request
            var allEvents = _tcpService.GetCalender();

            var daysInMonth =  DateTime.DaysInMonth(year, month);
            var dayList = new List<Day>();
            for (int i = 1; i <= daysInMonth; i++)
            {
                var day = new Day();
                day.Number = i;
                foreach (var calendarEvent in allEvents.Where(x => x.Date.Year == year && x.Date.Month == month && x.Date.Day == i))
                {
                    day.Events.Add(calendarEvent);
                    day.IsEvent = true;
                }
                dayList.Add(day);
            }
            if (delete == -2)
            {
                ViewBag.DeleteMessage = "";
            }
            else if (delete == -4)
            {
                ViewBag.DeleteMessage = "Successfully created event";
            }
            else if (delete == -3)
            {
                ViewBag.DeleteMessage = "Error during creating event";
            }
            else if (delete != -1)
            {
                ViewBag.DeleteMessage = "Successfully deleted event";
            }
            else
            {
                ViewBag.DeleteMessage = "Error during deleting event";
            }
            ViewBag.Month = month;
            ViewBag.Year = year;
            return View(dayList);
        }


        [Route("Event")]
        public IActionResult Event()
        {
            ViewBag.Display = false;
            ViewBag.Delete = false;
            var eventNew = new EventDetailed();
            eventNew.Date = DateTime.Now;
            return View(eventNew);
        }

        [Route("Create")]
        public IActionResult Create(EventDetailed eventDetailed)
        {
            var result = _tcpService.CreateEvent(eventDetailed);
            if (result == "DONE")
            {
                return RedirectToAction("Calendar", "Home", new { year = 2020, month = 1, delete = -4 });
            }
            else
            {
                return RedirectToAction("Calendar", "Home", new { year = 2020, month = 1, delete = -3 });
            }
        }

        [Route("Event/{id}")]
        public IActionResult Event(int id)
        {
            var eventDetailed = _tcpService.GetSingleEvent(id);
            ViewBag.Display = true;
            ViewBag.Disabled = "disabled";
            ViewBag.Delete = true;
            ViewBag.Id = id;
            return View(eventDetailed);
        }

        [Route("Delete/{id}")]
        public IActionResult Delete(int id)
        {
            var result = _tcpService.DeleteEvent(id);
            if (result == "DONE")
            {
                return RedirectToAction("Calendar", "Home", new { year = 2020, month = 1, delete = id });
            }
            else
            {
                return RedirectToAction("Calendar", "Home", new { year = 2020, month = 1, delete = -1 });
            }
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
