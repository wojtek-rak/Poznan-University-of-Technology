﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading;

namespace PCmax
{
    public class Genetic
    {

        //GENETIC PROPERTIES
        private bool AUTO_FITTING = false;
        private int MAX_POPULATION = 41;
        private int MAX_MUTATION_CHANCE = 11;
        private int MAX_SUPER_POPULATION = 31;

        //MANUAL
        private int POPULATION = 10;
        private int MUTATION_CHANCE = 6;
        private int SUPER_POPULATION = 23;

        private TimeSpan algorithmTime = TimeSpan.FromSeconds(360);

        public static int UpgradeGenome_Range = 2;
        //END


        public List<List<Gene>> population = new List<List<Gene>>();
        public List<List<Gene>> superPopulation = new List<List<Gene>>();
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
            if (AUTO_FITTING)
            {
                var result = new List<(List<Gene> vector, int score, int populationRange, int mutationChanceRange, int superPopulationRange)>();
                int count = 0;
                for (int populationRange = 5; populationRange < MAX_POPULATION; populationRange += 5)
                {
                    for (int mutationChanceRange = 0; mutationChanceRange < MAX_MUTATION_CHANCE; mutationChanceRange += 2)
                    {
                        for (int superPopulationRange = 5; superPopulationRange < MAX_SUPER_POPULATION; superPopulationRange += 6)
                        {
                            data.BestScore = 999999999;
                            data.BestVector = null;
                            population = new List<List<Gene>>();
                            superPopulation = new List<List<Gene>>();
                            GeneratePopulationNoData();
                            RunLoop();
                            result.Add((data.BestVector, data.BestScore, populationRange, mutationChanceRange, superPopulationRange));
                            Console.WriteLine(data.BestScore);
                            Console.WriteLine(count++);
                        }
                    }
                }

                result = result.OrderBy(x => x.score).ToList();
                foreach (var tuple in result)
                {
                    Console.WriteLine($"Score: {tuple.score} {tuple.populationRange} {tuple.mutationChanceRange} {tuple.superPopulationRange}");
                }

                using (System.IO.StreamWriter file = new System.IO.StreamWriter(Main.ActualPath + "fittingResult.txt"))
                {
                    foreach (var tuple in result)
                    {
                        file.WriteLine($"Score: {tuple.score} {tuple.populationRange} {tuple.mutationChanceRange} {tuple.superPopulationRange}");
                    }
                }

                Console.WriteLine("TO IMPLEMENT!");
                Thread.Sleep(50000);
            }
            else
            {
                GeneratePopulation();
                RunLoop();
            }
            
        }


        private void RunLoop()
        {
            List<(List<Gene> chromosome, int score)> generation = new List<(List<Gene> chromosome, int score)>();
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
                List<List<Gene>> newPopulation = new List<List<Gene>>();
                newPopulation.Add(supremechild);
                int mut = 0;
                foreach (var chromosome in population)
                {

                    var newChromosome = new List<Gene>();
                    newChromosome = chromosome;

                    //UPGRADE
                    newChromosome = UpgradeGenome(newChromosome);

                    mut++;
                    if (mut > MUTATION_CHANCE)
                    {
                        //MUTATION
                        Mutation(newChromosome);
                        mut = 0;
                    }

                    newPopulation.Add(newChromosome);
                }
                if(data.BestVector != null) newPopulation[1] = new List<Gene>(data.BestVector);

                List<(List<Gene> chromosome, int score)> newGeneration = new List<(List<Gene> chromosome, int score)>();
                foreach (var chromosome in newPopulation)
                {
                    var score = Scoring(chromosome);
                    //Console.WriteLine(score);
                    //PrintChromosome(chromosome);
                    if (data.BestScore > score)
                    {
                        data.BestScore = score;
                        data.BestVector = chromosome;
                    }
                    newGeneration.Add((chromosome, score));
                    i++;
                }

                newGeneration = newGeneration.OrderBy(x => x.score).ToList();
                if (superPopulation.Count < SUPER_POPULATION)
                {
                    superPopulation.Add(newGeneration.First().chromosome);
                    superPopulation.Add(newGeneration.Skip(1).First().chromosome);
                    superPopulation.Add(newGeneration.Last().chromosome);
                    GeneratePopulation(data);
                }
                else
                {
                    population = new List<List<Gene>>(superPopulation);
                    superPopulation = new List<List<Gene>>();
                }

                //population = newPopulation;
                
                //Score after loop
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

        private int Scoring(List<Gene> tasks)
        {
            var max = 0;
            for (int i = 0; i < main.TaskMachines.Count; i++)
            {
                var maxValue = tasks.Where(x => x.machineId == i).Sum(y => y.task);
                if (max < maxValue) max = maxValue;

            }

            //greedy.tasks = new List<Gene>(tasks);
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
        public List<Gene> UpgradeGenome(List<Gene> genToUpgrade)
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
            int randomFasted = random.Next(0, UpgradeGenome_Range);
            int randomOverload = 0;//random.Next(0, 3);
            var overloadMachineId = taskMachines.Skip(randomOverload).First().index;
            var fastedMachineId = taskMachines.OrderBy(x => x.sum).Skip(randomFasted).First().index;

            var tempOverload = genToUpgrade.First(x => x.machineId == overloadMachineId);
            genToUpgrade[tempOverload.index] = new Gene(genToUpgrade[tempOverload.index].index, genToUpgrade[tempOverload.index].task, fastedMachineId);

            //Console.WriteLine($"Score of child {Scoring(supremeChild)}");
            return genToUpgrade;
        }

#warning CROSSOVER
        public List<Gene> CrossOver((List<Gene> chromosome, int score) parentOne, (List<Gene> chromosome, int score) parentTwo)
        {
            parentOne = (parentOne.chromosome.OrderBy(x => x.index).ToList(), parentOne.score);
            parentTwo = (parentTwo.chromosome.OrderBy(x => x.index).ToList(), parentTwo.score);
            List<Gene> supremeChild = parentOne.chromosome.Take(parentOne.chromosome.Count / 2).ToList();
            foreach (var value in parentTwo.chromosome.Skip(parentOne.chromosome.Count / 2))
            {
                //if (!supremeChild.Exists(x => x.index == value.index)) 
                supremeChild.Add(value);

            }
            //Console.WriteLine($"Score of child {Scoring(supremeChild)}");
            return supremeChild;
        }

#warning MUTACJE
        public List<Gene> Mutation(List<Gene> chromosome)
        {
            var one = random.Next(0, taskRange);
            var two = random.Next(0, taskRange);

            var temp = chromosome[one].machineId;
            chromosome[one] = new Gene(chromosome[one].index, chromosome[one].task, chromosome[two].machineId);
            chromosome[two] = new Gene(chromosome[two].index, chromosome[two].task, temp);

            return chromosome;

        }






        private void GeneratePopulation()
        {

            for (int i = 0; i < POPULATION; i++)
            {
                if (i == 0)
                {
                    if (File.Exists(Main.ActualPath + main.path.Substring(0, main.path.Length - 3) + "result.txt"))
                    {
                        List<Gene> oldPopulation = new List<Gene>();
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
                                    
                                    oldPopulation.Add(new Gene(Int32.Parse(intArray[0]), Int32.Parse(intArray[1]), Int32.Parse(intArray[2])));
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
                        newPopulation[j] = new Gene(newPopulation[j].index, newPopulation[j].task, newPopulation[randomIndex].machineId);
                        newPopulation[randomIndex] = new Gene(newPopulation[randomIndex].index, newPopulation[randomIndex].task, temp);

                    }

                    population.Add(newPopulation);
                }


            }
        }

        private void GeneratePopulation(Data oldData)
        {

            for (int i = 0; i < POPULATION; i++)
            {
                if (i == 0)
                {
                    population.Add(oldData.BestVector);
                }
                else
                {
                    var newPopulation = main.Tasks;

                    for (int j = 0; j < newPopulation.Count - 1; j++)
                    {
                        
                        var randomIndex = random.Next(0, newPopulation.Count);
                        var temp = newPopulation[j].machineId;
                        newPopulation[j] = new Gene(newPopulation[j].index, newPopulation[j].task, newPopulation[randomIndex].machineId);
                        newPopulation[randomIndex] = new Gene(newPopulation[randomIndex].index, newPopulation[randomIndex].task, temp);

                    }

                    population.Add(newPopulation);
                }


            }
        }

        private void GeneratePopulationNoData()
        {

            for (int i = 0; i < POPULATION; i++)
            {
                
                    var newPopulation = main.Tasks;

                    for (int j = 0; j < newPopulation.Count - 1; j++)
                    {
                        
                        var randomIndex = random.Next(0, newPopulation.Count);
                        var temp = newPopulation[j].machineId;
                        newPopulation[j] = new Gene(newPopulation[j].index, newPopulation[j].task, newPopulation[randomIndex].machineId);
                        newPopulation[randomIndex] = new Gene(newPopulation[randomIndex].index, newPopulation[randomIndex].task, temp);

                    }

                    population.Add(newPopulation);

            }
        }

    }
}
