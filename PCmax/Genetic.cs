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
        private const int MUTATION_CHANCE = 5;
        private TimeSpan algorithmTime = TimeSpan.FromSeconds(50);
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
                    var newPopulation = main.Tasks;

                    for (int j = 0; j < newPopulation.Count - 1; j++)
                    {
                        
                        var randomIndex = random.Next(0, newPopulation.Count);
                        var temp = newPopulation[j].machineId;
                        newPopulation[j] = (newPopulation[j].index, newPopulation[j].task, newPopulation[randomIndex].machineId);
                        newPopulation[randomIndex] = (newPopulation[randomIndex].index, newPopulation[randomIndex].task, temp);

                    }

                    population.Add(newPopulation);
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
                int mut = 0;
                foreach (var chromosome in population)
                {

                    var newChromosome = new List<(int index, int task, int machineId)>();
                    newChromosome = chromosome;

                    newChromosome = UpgradeGenome(newChromosome);

                    mut++;
                    if (mut > MUTATION_CHANCE)
                    {
                        Mutation(newChromosome);
                        mut = 0;
                    }

                    newPopulation.Add(newChromosome);
                }
                if(data.BestVector != null) newPopulation[1] = new List<(int index, int task, int machineId)>(data.BestVector);
                population = newPopulation;
                Console.WriteLine(data.BestScore);

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
        public void PrintChromosome(List<int> chromosome)
        {
            Console.WriteLine();
            foreach (var value in chromosome)
            {
                Console.Write($"{value} ");
            }
        }

#warning UPGRADE GENOME
        public List<(int index, int task, int machineId)> UpgradeGenome(List<(int index, int task, int machineId)> genToUpgrade)
        {
            var taskMachines = new List<(int sum, int index)>();
            for (int i = 0; i < main.numberOfThreads; i++)
            {
                taskMachines.Add((0, i));
            }

            foreach (var gen in genToUpgrade)
            {
                taskMachines[gen.machineId] = (taskMachines[gen.machineId].sum + gen.task, taskMachines[gen.machineId].index);
            }

            taskMachines = taskMachines.OrderByDescending(x => x.sum).ToList();
            int randomFasted = random.Next(0, 2);
            int randomOverload = 0;//random.Next(0, 3);
            var overloadMachineId = taskMachines.Skip(randomFasted).First().index;
            var fastedMachineId = taskMachines.Skip(randomOverload).Last().index;

            var tempOverload = genToUpgrade.First(x => x.machineId == overloadMachineId);
            genToUpgrade[tempOverload.index] = (genToUpgrade[tempOverload.index].index, genToUpgrade[tempOverload.index].task, fastedMachineId);

            //Console.WriteLine($"Score of child {Scoring(supremeChild)}");
            return genToUpgrade;
        }

#warning CROSSOVER
        public List<(int index, int task, int machineId)> CrossOver((List<(int index, int task, int machineId)> chromosome, int score) parentOne, (List<(int index, int task, int machineId)> chromosome, int score) parentTwo)
        {
            parentOne = (parentOne.chromosome.OrderBy(x => x.index).ToList(), parentOne.score);
            parentTwo = (parentTwo.chromosome.OrderBy(x => x.index).ToList(), parentTwo.score);
            List<(int index, int task, int machineId)> supremeChild = parentOne.chromosome.Take(parentOne.chromosome.Count / 2).ToList();
            foreach (var value in parentTwo.chromosome.Skip(parentOne.chromosome.Count / 2))
            {
                //if (!supremeChild.Exists(x => x.index == value.index)) 
                supremeChild.Add(value);

            }
            //Console.WriteLine($"Score of child {Scoring(supremeChild)}");
            return supremeChild;
        }

#warning MUTACJE
        public List<(int index, int task, int machineId)> Mutation(List<(int index, int task, int machineId)> chromosome)
        {
            var one = random.Next(0, taskRange);
            var two = random.Next(0, taskRange);

            var temp = chromosome[one].machineId;
            chromosome[one] = (chromosome[one].index, chromosome[one].task, chromosome[two].machineId);
            chromosome[two] = (chromosome[two].index, chromosome[two].task, temp);

            return chromosome;

        }
    }
}
