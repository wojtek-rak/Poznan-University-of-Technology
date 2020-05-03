using Microsoft.Extensions.Options;
using NetworkCalendar.Models;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace NetworkCalendar
{
    public class TcpService : ITcpService
    {
        private readonly IpSettings _ipSettings;
        private readonly TcpClient _client;

        public TcpService(IOptions<IpSettings> ipSettings)
        {
            _ipSettings = ipSettings.Value;
            _client = new TcpClient(_ipSettings.Ip, _ipSettings.Port);
        }

        private NetworkStream GetNetworkStream()
        {
            return _client.GetStream();
        }

        public List<Event> GetCalender()
        {
            const string message = "GET_CALENDAR";
            var stream = GetNetworkStream();
            Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
            // Send the message to the connected TcpServer. 
            stream.Write(data, 0, data.Length);
            Console.WriteLine("Sent: {0}", message);
            // Bytes Array to receive Server Response.
            data = new Byte[256];
            String response = String.Empty;
            // Read the Tcp Server Response Bytes.

            Int32 bytes = stream.Read(data, 0, data.Length);
            response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
            var res = response.Split("<XD>")[0];
            Console.WriteLine("Received: {0}", res);

            if (res.Length >  5)
            {
                int.TryParse(res.Split(",")[0], out int estimate);
                var liczbaStron = estimate / 7;
                var eventsNew = new List<Event>();

                var eventsNotParsed = res.Split(".");
                foreach (var eventNotParsed in eventsNotParsed)
                {
                    var eventToAdd = new Event();
                    eventToAdd.Id = eventNotParsed.Split(",")[0];
                    eventToAdd.Date = DateTime.ParseExact(eventNotParsed.Split(",")[1], "yyMMdd", CultureInfo.InvariantCulture);
                    eventToAdd.Title = eventNotParsed.Split(",")[2];

                    eventsNew.Add(eventToAdd);
                }

                for (int i = 0; i < liczbaStron; i++)
                {
                    bytes = stream.Read(data, 0, data.Length);
                    response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                    Console.WriteLine("Received: {0}", response.Split("<XD>")[0]);

                    var eventsNotParsed2 = response.Split("<XD>")[0].Split(".");
                    foreach (var eventNotParsed in eventsNotParsed2)
                    {
                        var eventToAdd = new Event();
                        eventToAdd.Id = eventNotParsed.Split(",")[0];
                        eventToAdd.Date = DateTime.ParseExact(eventNotParsed.Split(",")[1], "yyMMdd", CultureInfo.InvariantCulture);
                        eventToAdd.Title = eventNotParsed.Split(",")[2];

                        eventsNew.Add(eventToAdd);
                    }
                }
                return eventsNew;
            }

            var events = new List<Event>();

            int.TryParse(res, out int pages);
            for (int i = 0; i < pages; i++)
            {
                bytes = stream.Read(data, 0, data.Length);
                response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
                Console.WriteLine("Received: {0}", response.Split("<XD>")[0]);

                var eventsNotParsed = response.Split("<XD>")[0].Split(".");
                foreach (var eventNotParsed in eventsNotParsed)
                {
                    var eventToAdd = new Event();
                    eventToAdd.Id = eventNotParsed.Split(",")[0];
                    eventToAdd.Date = DateTime.ParseExact(eventNotParsed.Split(",")[1], "yyMMdd", CultureInfo.InvariantCulture);
                    eventToAdd.Title = eventNotParsed.Split(",")[2];

                    events.Add(eventToAdd);
                }
            }
            return events;
        }

        public EventDetailed GetSingleEvent(int id)
        {
            string message = "GET_SINGLE_EVENT.";
            if(id < 10) 
            {
                message += "00" + id;
            }
            else
            {
                message += "0" + id;
            }
            var stream = GetNetworkStream();
            Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
            // Send the message to the connected TcpServer. 
            stream.Write(data, 0, data.Length);
            Console.WriteLine("Sent: {0}", message);
            // Bytes Array to receive Server Response.
            data = new Byte[256];
            String response = String.Empty;
            // Read the Tcp Server Response Bytes.

            Int32 bytes = stream.Read(data, 0, data.Length);
            response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
            Console.WriteLine("Received: {0}", response.Split("<XD>")[0]);

            var eventDetailed = new EventDetailed();
            var eventNotParsed = response.Split("<XD>")[0].Split(".");

            eventDetailed.Title = eventNotParsed[0];
            eventDetailed.Owner = eventNotParsed[1];
            eventDetailed.Date = DateTime.ParseExact(eventNotParsed[2], "yyMMdd", CultureInfo.InvariantCulture); ;
            eventDetailed.Description = eventNotParsed[3];

            return eventDetailed;
        }

        public string DeleteEvent(int id)
        {
            string message = "REMOVE_EVENT.";
            if (id < 10)
            {
                message += "00" + id;
            }
            else
            {
                message += "0" + id;
            }
            var stream = GetNetworkStream();

            Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
            // Send the message to the connected TcpServer. 
            stream.Write(data, 0, data.Length);
            Console.WriteLine("Sent: {0}", message);
            // Bytes Array to receive Server Response.
            data = new Byte[256];
            String response = String.Empty;
            // Read the Tcp Server Response Bytes.

            Int32 bytes = stream.Read(data, 0, data.Length);
            response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
            Console.WriteLine("Received: {0}", response.Split("<XD>")[0]);

            return response.Split("<XD>")[0];
        }

        public string CreateEvent(EventDetailed eventDetailed)
        {
            string message = "ADD_EVENT.";

            var title = eventDetailed.Title.PadRight(20, ' ');
            var owner = eventDetailed.Owner.PadRight(10, ' '); ;
            var date = eventDetailed.Date.ToString("yyMMdd");
            var description = eventDetailed.Description.PadRight(200, ' '); ;

            message += title + "." + owner + "." + date + "." + description;

            var stream = GetNetworkStream();

            Byte[] data = System.Text.Encoding.ASCII.GetBytes(message);
            // Send the message to the connected TcpServer. 
            stream.Write(data, 0, data.Length);
            Console.WriteLine("Sent: {0}", message);
            // Bytes Array to receive Server Response.
            data = new Byte[256];
            String response = String.Empty;
            // Read the Tcp Server Response Bytes.

            Int32 bytes = stream.Read(data, 0, data.Length);
            response = System.Text.Encoding.ASCII.GetString(data, 0, bytes);
            Console.WriteLine("Received: {0}", response.Split("<XD>")[0]);

            return response.Split("<XD>")[0];
        }

        //private string FillWithSpaces(string orginal, int length)
        //{

        //}
    }
}
