using System;
using System.Collections.Generic;

namespace PCmax
{
    public class Data
    {
        public int BestScore = 999999999;
        private List<Gene> bestVector;
        public List<Gene> BestVector
        {
            get
            {
                return bestVector;
            }
            set
            {
                bestVector = new List<Gene>(value);
            }
        }

        public Data()
        {
        }
    }
}
