using System;
using System.Collections.Generic;

namespace PCmax
{
    public class Genetic
    {

        //GENETIC PROPERTIES
        private const int POPULATION = 10;
        private TimeSpan algorithmTime = TimeSpan.FromSeconds(0);
        //END


        public List<List<int>> population = new List<List<int>>();
        private Data data;
        private Greedy greedy = new Greedy();
        private readonly Main main;
        public Genetic(Main main)
        {
            this.main = main;
            data = new Data();
        }

        public void Start()
        {
            GeneratePopulation();
            RunLoop();
        }

        private void GeneratePopulation()
        {

            for (int i = 0; i < POPULATION; i++)
            {
                population.Add(main.Tasks);
            }
        }

        private void RunLoop()
        {
            var start = DateTime.Now;

            while((DateTime.Now - start) < algorithmTime)
            {

            }
        }

        private int Scoring(List<int> tasks, List<int> taskMachines)
        {
            greedy.tasks = new List<int>(tasks);
            greedy.taskMachines = new List<int>(taskMachines);
            return greedy.Calculate();
        }
    }
}
