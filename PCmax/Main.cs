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
        public static string WindowsPath =
            @"C:\Users\Wolix\Documents\A   Projects\c# pprojects\PCmax\PCmax\PCmax\bin\Debug\Results\";

        public static string MacPath = "/Users/wolix/Documents/c#_project/PCmax/PCmax/bin/Debug/Results/";
        public static string ActualPath => WindowsPath;

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
        public List<(int index, int task, int machineId)> Tasks {
            get{
                return new List<(int index, int task, int machineId)>(tasks);
            }
        }
        public List<int> TaskMachines => new List<int>(taskMachines);
        public int numberOfThreads;
        public int numberOfTasks;
        private List<(int index, int task, int machineId)> tasks = new List<(int index, int task, int machineId)>();
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
            int k = 0;
            //Initialize data from file
            using (var reader = new StreamReader(path))
            {
                numberOfThreads = Int32.Parse(reader.ReadLine());
                numberOfTasks = Int32.Parse(reader.ReadLine());
                while ((line = reader.ReadLine()) != null)
                {
                    try
                    {
                        tasks.Add((k, Int32.Parse(line), -1));
                        k++;
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
            //Init tasks

            InitializeTasks(Tasks, TaskMachines);
            // Greedy without sort
            var greedy = new Greedy() {tasks = Tasks, taskMachines = TaskMachines};
            var greedyValue = greedy.Calculate();
            max = (greedyValue, "greedy no sort");

            // Greedy with sort
            var sortedTask = Tasks;
            sortedTask = sortedTask.OrderByDescending(x => x.task).ToList();
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
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(ActualPath + path.Substring(0, path.Length - 3) + "result.txt"))
            {
                foreach (var value in data.BestVector)
                {
                    file.WriteLine(value);
                }
                file.WriteLine("ACTUAL:");
                file.WriteLine(data.BestScore);
            }

        }


        private void InitializeTasks(List<(int index, int task, int machineId)> tasksLocal, List<int> taskMachinesLocal)
        {
            ;
            int time = 0;
            while (tasksLocal.Count > 0)
            {
                for (int i = 0; i < taskMachinesLocal.Count; i++)
                {
                    if (tasksLocal.Count == 0) break;
                    if (taskMachinesLocal[i] <= time)
                    {
                        var task = tasksLocal.First();
                        taskMachinesLocal[i] += task.task;
                        var taskEdit = tasks.First(x => x.index == task.index);

                        var index = tasks.IndexOf(taskEdit);
                        taskEdit = (taskEdit.index, taskEdit.task, i);
                        if (index != -1)
                            tasks[index] = taskEdit;
                            
                        tasksLocal.Remove(task);
                    }
                }
                time++;
            }
            //Console.WriteLine(@"Max time with greedy algorithm: {0}", taskMachines.Max());
            }
    }
}