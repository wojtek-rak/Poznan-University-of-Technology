using System;
using System.Collections.Generic;

namespace PCmax
{
    public class Data
    {
        public int BestScore = 999999999;
        private List<(int index, int task)> bestVector;
        public List<(int index, int task)> BestVector
        {
            get
            {
                return bestVector;
            }
            set
            {
                bestVector = new List<(int index, int task)>(value);
            }
        }

        public Data()
        {
        }
    }
}
