using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PCmax
{
    class Program
    {
        static void Main(string[] args)
        {
            int time = 0;
            int numberOfThreads;
            int numberOfTasks;
            string line;
            List<int> tasks = new List<int>();
            List<int> taskMachines = new List<int>();
            //Initialize data from file
            using (var reader = new StreamReader("TestInstance.txt"))
            {
                numberOfThreads = Int32.Parse(reader.ReadLine());
                numberOfTasks = Int32.Parse(reader.ReadLine());
                while ((line = reader.ReadLine()) != null)
                {
                    tasks.Add(Int32.Parse(line));
                }
            }
            //Initialize counting for machines
            for (int i = 0; i < numberOfThreads; i++)
            {
                taskMachines.Add(0);
            }
            Console.WriteLine(@"Number of Tasks: {0}, Number of Threads {1}", numberOfTasks, numberOfThreads);
            foreach (var task in tasks)
            {
                Console.Write(@"{0} ", task);
            }

            while (tasks.Count > 0)
            { 
                for (int i = 0; i < taskMachines.Count; i++)
                {
                    if (taskMachines[i] <= time)
                    {
                        taskMachines[i] += tasks.First();
                        tasks.Remove(tasks.First());
                    }
                }
                time++;
            }
            Console.WriteLine();
            Console.WriteLine(@"{1}Max time with greedy algorithm: {0}",taskMachines.Max(), Environment.NewLine);
            Thread.Sleep(8000);
        }
    }
}
