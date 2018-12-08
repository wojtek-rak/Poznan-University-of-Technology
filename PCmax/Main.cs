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
        //private const string _path = "instance.txt";
        private const string _path = "inst1.txt";
        private const string _testPath = "TestInstance.txt"; // "TestInstance.txt"
        private const bool additionalInfo = false;
        private const bool additionalInfoMAX = false;
        private const bool greedyGO = true;

        public String path;
        public List<int> Tasks => new List<int>(tasks);
        public List<int> TaskMachines => new List<int>(taskMachines);
        public int numberOfThreads;
        public int numberOfTasks;
        private List<int> tasks = new List<int>();
        private List<int> taskMachines = new List<int>();

        public void Startup(string pathLive)
        {
            var max =  (value: -1,alg: "default");
            int maxValue = -1;
            path = _path;
            if (_generate)
            {
                var generator = new Generator() {_path = _path};
                generator.GenerateInstance();
            }
            if (_testPathActive) path = _testPath;

            if (pathLive != null) path = pathLive;


            string line;

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
            if(additionalInfo)
            {
                Console.WriteLine(@"Number of Tasks: {0}, Number of Threads {1}", numberOfTasks, numberOfThreads);
                foreach (var task in tasks)
                {
                    Console.Write(@"{0} ", task);
                }
            }

            // Greedy without sort
            var greedy = new Greedy() {tasks = Tasks, taskMachines = TaskMachines};
            var greedyValue = greedy.Calculate();
            max = (greedyValue, "greedy no sort");

            // Greedy with sort
            var sortedTask = Tasks;
            sortedTask.Sort();
            var greedySort = new Greedy() { tasks = sortedTask, taskMachines = TaskMachines };
            var greedySortedValue = greedySort.Calculate();
            if (max.value > greedySortedValue)
            {
                max = (greedySortedValue, "greedy sorted");
            }

            if (additionalInfoMAX)
            {
                Console.WriteLine();
                Console.WriteLine($"Max PCMAX value: {maxValue}");
            }

            Console.WriteLine($"Max value {max.value} with {max.alg}");
            if(greedyGO)
            {
                if (max.alg == "greedy no sort")
                {
                    InitPopulations();
                }
                else
                {
                    tasks.Sort();
                    InitPopulations();
                }
            }

        }

        public void InitPopulations()
        {
            Data data = new Data();
            var genetic = new Genetic(this, data);
            genetic.Start();
            Console.WriteLine($"Best Score: {data.BestScore}");
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(path.Substring(0, path.Length - 3) + "result.txt"))
            {
                foreach (var value in data.BestVector)
                {
                    file.WriteLine(value);
                }
                file.WriteLine("ACTUAL:");
                file.WriteLine(data.BestScore);
            }
        }
    }
}