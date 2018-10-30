using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
namespace PCmax
{
    public class Main
    {
        //INSTANCE
        private const bool _generate = true;
        private const string _path = "instance.txt";
        private const int _numOfThreads = 10;
        private const int _numOfTasks = 150;
        private const int _randomRange = 50;


        public void Startup()
        {
            if (_generate) GenerateInstance();

            int time = 0;
            int numberOfThreads;
            int numberOfTasks;
            string line;
            List<int> tasks = new List<int>();
            List<int> taskMachines = new List<int>();
            //Initialize data from file
            using (var reader = new StreamReader(_path))
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
                    if (tasks.Count == 0) break;
                    if (taskMachines[i] <= time)
                    {
                        taskMachines[i] += tasks.First();
                        tasks.Remove(tasks.First());
                    }
                }
                time++;
            }
            Console.WriteLine();
            Console.WriteLine(@"{1}Max time with greedy algorithm: {0}", taskMachines.Max(), Environment.NewLine);
            Thread.Sleep(8000);
        }

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
            using (var writer = new StreamWriter(_path, false))
            {
                writer.WriteLine(_numOfThreads);
                writer.WriteLine(_numOfTasks);

                foreach(var task in tasks)
                {
                    writer.WriteLine(task);
                }

                writer.Close();
            }
        }
    }
}
