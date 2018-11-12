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
        private const bool _generate = false;
        private const bool _testPathActive = false;
        private const string _path = "instance.txt";
        private const string _testPath = "TestInstance.txt"; // "TestInstance.txt"



        public void Startup()
        {
            int maxValue = -1;
            String path = _path;
            if (_generate)
            {
                var generator = new Generator() {_path = _path};
                generator.GenerateInstance();
            }
            if (_testPathActive) path = _testPath;


            int numberOfThreads;
            int numberOfTasks;
            string line;
            List<int> tasks = new List<int>();
            List<int> taskMachines = new List<int>();
            //Initialize data from file
            using (var reader = new StreamReader(path))
            {
                numberOfThreads = Int32.Parse(reader.ReadLine());
                numberOfTasks = Int32.Parse(reader.ReadLine());
                while ((line = reader.ReadLine()) != null)
                {
                    try
                    {
                        tasks.Add(Int32.Parse(line));
                    }
                    catch (Exception ex)            
                    {                
                        if (ex is FormatException)
                        {
                            if ((line = reader.ReadLine()) != null)
                            {
                                maxValue = Int32.Parse(line);
                            }
                        }
                        else
                        {
                            throw;
                        }
                    }
                    
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

            var greedy = new Greedy() {tasks = new List<int>(tasks), taskMachines = new List<int>(taskMachines)};
            var greedyValue = greedy.Calculate();

            Console.WriteLine();
            Console.WriteLine($"Max PCMAX value: {maxValue}");
            Thread.Sleep(99999);
        }

        
    }
}
