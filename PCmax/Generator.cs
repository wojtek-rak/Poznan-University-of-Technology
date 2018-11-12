using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PCmax
{
    public class Generator
    {
        public string _path { get; set; }

        private const int _numOfThreads = 10;
        private const int _numOfTasks = 150;
        private const int _randomRange = 50;

        public void GenerateInstance()
        {
            var rnd = new Random();
            int rand, iterator = 0;
            List<int> tasks = new List<int>();
            List<int> taskMachines = new List<int>();
            for (int i = 0; i < _numOfThreads; i++)
            {
                taskMachines.Add(0);
            }
            for (int i = 0; i < _numOfTasks - _numOfTasks / 10; i++)
            {
                rand = rnd.Next(1, _randomRange);
                taskMachines[iterator] += rand;
                tasks.Add(rand);
                iterator++;
                if (iterator == _numOfThreads) iterator = 0;
            }
            taskMachines = taskMachines.OrderByDescending(a => a).ToList();
            var taskMax = taskMachines.First();
            taskMachines[0] += 1;
            iterator = 1;
            for (int i = 0; i < _numOfTasks / 10 - 1; i++)
            {
                rand = taskMax - taskMachines[iterator];
                if (iterator != _numOfThreads - 1)
                {
                    taskMachines[iterator] += rand;
                    tasks.Add(rand);
                    iterator++;
                }
                else 
                {
                    if (_numOfTasks / 10 - 2 == i)
                    {
                        taskMachines[iterator] += rand;
                        tasks.Add(rand);
                        break;
                    }
                    else
                    {
                        taskMachines[iterator] += 1;
                        tasks.Add(1);
                    }
                }
            }
            Console.WriteLine($"Best value is: {taskMax}");
            using (var writer = new StreamWriter(_path, false))
            {
                writer.WriteLine(_numOfThreads);
                writer.WriteLine(_numOfTasks);

                foreach(var task in tasks)
                {
                    writer.WriteLine(task);
                }
                writer.WriteLine("MAX:");
                writer.WriteLine(taskMax);
                writer.Close();
            }
        }
    }
}
