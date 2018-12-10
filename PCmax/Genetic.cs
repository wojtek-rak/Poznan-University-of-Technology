using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;

namespace PCmax
{
    public class Genetic
    {

        //GENETIC PROPERTIES
        private const int POPULATION = 10;
        private TimeSpan algorithmTime = TimeSpan.FromSeconds(1);
        //END


        public List<List<(int index, int task, int machineId)>> population = new List<List<(int index, int task, int machineId)>>();
        private Data data;
        private Greedy greedy = new Greedy();
        private readonly Main main;
        private int taskRange;
        private Random random = new Random();
        public Genetic(Main main, Data data)
        {
            this.main = main;
            this.data = data;
        }

        public void Start()
        {
            taskRange = main.Tasks.Count;
            GeneratePopulation();
            RunLoop();
        }

        private void GeneratePopulation()
        {

            for (int i = 0; i < POPULATION; i++)
            {
                if (i == 0)
                {
                    if (File.Exists(Main.ActualPath + main.path.Substring(0, main.path.Length - 3) + "result.txt"))
                    {
                        List<(int index, int task, int machineId)> oldPopulation = new List<(int index, int task, int machineId)>();
                        String line;
                        int maxValue = 999999999;
                        using (var reader = new StreamReader(Main.ActualPath + main.path.Substring(0, main.path.Length - 3) + "result.txt"))
                        {
                            while ((line = reader.ReadLine()) != null)
                            {
                                try
                                {
                                    line = line.Replace("(", "");
                                    line = line.Replace(")", "");
                                    line = line.Replace(" ", "");
                                    var intArray = line.Split(',');
                                    
                                    oldPopulation.Add((Int32.Parse(intArray[0]), Int32.Parse(intArray[1]), Int32.Parse(intArray[2])));
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
                        data.BestScore = maxValue;
                        data.BestVector = oldPopulation;
                        population.Add(oldPopulation);
                    }
                    else
                    {
                        population.Add(main.Tasks.OrderByDescending(x => x.task).ToList());
                    }
                }
                else
                {
                    population.Add(main.Tasks.OrderByDescending(x => x.task).ToList());
                }


            }
        }

        private void RunLoop()
        {
            List<(List<(int index, int task, int machineId)> chromosome, int score)> generation = new List<(List<(int index, int task, int machineId)> chromosome, int score)>();
            var start = DateTime.Now;
            int generationCount = 0;
            while((DateTime.Now - start) < algorithmTime)
            {
                generationCount++;
                int i = 0;
                foreach (var chromosome in population)
                {
                    var score = Scoring(chromosome);
                    //Console.WriteLine(score);
                    //PrintChromosome(chromosome);
                    if (data.BestScore > score)
                    {
                        data.BestScore = score;
                        data.BestVector = chromosome;
                    }
                    generation.Add((chromosome, score));
                    i++;
                }
                var parents = generation.OrderBy(x => x.score).Take(2).ToList();
                var supremechild = CrossOver(parents[0], parents[1]);
                List<List<(int index, int task, int machineId)>> newPopulation = new List<List<(int index, int task, int machineId)>>();
                newPopulation.Add(supremechild);
                foreach (var chromosome in population)
                {

                    var newChromosome = new List<(int index, int task, int machineId)>();
                    newChromosome = (chromosome);

                    Mutation(newChromosome);

                    newPopulation.Add(newChromosome);
                }
                if(data.BestVector != null) newPopulation[1] = new List<(int index, int task, int machineId)>(data.BestVector);
                population = newPopulation;


            }
            //foreach (var chromosome in population)
            //{
            //    var score = Scoring(chromosome);
            //    //Console.WriteLine(score);
            //    //PrintChromosome(chromosome);
            //    if (data.BestScore > score)
            //    {
            //        data.BestScore = score;
            //        data.BestVector = chromosome;
            //    }
            //}
        }
#warning MUTACJE
        private List<(int index, int task, int machineId)> Mutation(List<(int index, int task, int machineId)> chromosome)
        {
            var one = random.Next(0, taskRange);
            var two = random.Next(0, taskRange);
            (int, int, int) temp = chromosome[one];
            chromosome[one] = chromosome[two];
            chromosome[two] = temp;
            return chromosome;

        }
        private int Scoring(List<(int index, int task, int machineId)> tasks)
        {
            var max = 0;
            for (int i = 0; i < main.TaskMachines.Count; i++)
            {
                var maxValue = tasks.Where(x => x.machineId == i).Sum(y => y.task);
                if (max < maxValue) max = maxValue;

            }

            //greedy.tasks = new List<(int index, int task, int machineId)>(tasks);
            //greedy.taskMachines = new List<int>(main.TaskMachines);
            return max;
        }
        private void PrintChromosome(List<int> chromosome)
        {
            Console.WriteLine();
            foreach (var value in chromosome)
            {
                Console.Write($"{value} ");
            }
        }
#warning CROSSOVER
        private List<(int index, int task, int machineId)> CrossOver((List<(int index, int task, int machineId)> chromosome, int score) parentOne, (List<(int index, int task, int machineId)> chromosome, int score) parentTwo)
        {
            List<(int index, int task, int machineId)> supremeChild = parentOne.chromosome.Take(parentOne.chromosome.Count / 2).ToList();
            foreach (var value in parentTwo.chromosome)
            {

                if (!supremeChild.Exists(x => x.index == value.index)) supremeChild.Add(value);

            }
            //Console.WriteLine($"Score of child {Scoring(supremeChild)}");
            return supremeChild;
        }
    }
}
