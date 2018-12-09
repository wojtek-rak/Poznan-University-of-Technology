using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace PCmax
{
    public class Genetic
    {

        //GENETIC PROPERTIES
        private const int POPULATION = 10;
        private TimeSpan algorithmTime = TimeSpan.FromSeconds(1);
        //END


        public List<List<int>> population = new List<List<int>>();
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
                    if (File.Exists(main.path.Substring(0, main.path.Length - 3) + "result.txt"))
                    {
                        List<int> oldPopulation = new List<int>();
                        String line;
                        int maxValue = 999999999;
                        using (var reader = new StreamReader(main.path.Substring(0, main.path.Length - 3) + "result.txt"))
                        {
                            while ((line = reader.ReadLine()) != null)
                            {
                                try
                                {
                                    oldPopulation.Add(Int32.Parse(line));
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
                        population.Add(main.Tasks);
                    }
                }
                else
                {
                    population.Add(main.Tasks);
                }


            }
        }

        private void RunLoop()
        {
            List<(List<int> chromosome, int index, int score)> generation = new List<(List<int> chromosome, int index, int score)>();
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
                    generation.Add((chromosome, i, score));
                    i++;
                }
                var parents = generation.OrderBy(x => x.score).Take(2).ToList();
                var supremechild = CrossOver(parents[0], parents[1]);
                List<List<int>> newPopulation = new List<List<int>>();
                foreach (var chromosome in population)
                {

                    var newChromosome = new List<int>();
                    newChromosome = (chromosome);

                    Mutation(newChromosome);

                    newPopulation.Add(newChromosome);
                }
                if(data.BestVector != null) newPopulation[0] = new List<int>(data.BestVector);
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

        private List<int> Mutation(List<int> chromosome)
        {
            var one = random.Next(0, taskRange);
            var two = random.Next(0, taskRange);
            int temp = chromosome[one];
            chromosome[one] = chromosome[two];
            chromosome[two] = temp;
            return chromosome;

        }
        private int Scoring(List<int> tasks)
        {
            greedy.tasks = new List<int>(tasks);
            greedy.taskMachines = new List<int>(main.TaskMachines);
            return greedy.Calculate();
        }
        private void PrintChromosome(List<int> chromosome)
        {
            Console.WriteLine();
            foreach (var value in chromosome)
            {
                Console.Write($"{value} ");
            }
        }
        private (List<int> chromosome, int index, int score) CrossOver((List<int> chromosome, int index, int score) parentOne, (List<int> chromosome, int index, int score) parentTwo)
        {


            return parentOne;
        }
    }
}
