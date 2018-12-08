using System;
using System.Collections.Generic;

namespace PCmax
{
    public class Data
    {
        public int BestScore = 999999999;
        private List<int> bestVector;
        public List<int> BestVector
        {
            get
            {
                return bestVector;
            }
            set
            {
                bestVector = new List<int>(value);
            }
        }

        public Data()
        {
        }
    }
}
