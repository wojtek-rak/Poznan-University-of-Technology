using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PCmax
{
    public class Greedy
    {
        public List<int> tasks { get; set; }
        public List<int> taskMachines { get; set; }
        public int Calculate()
        {
            int time = 0;
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
            //Console.WriteLine(@"Max time with greedy algorithm: {0}", taskMachines.Max());
            return taskMachines.Max();
        }
    }
}
