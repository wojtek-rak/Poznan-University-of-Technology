using System;
using System.Collections.Generic;

namespace PCmax
{
    public class Data
    {
        public List<int> BestVector
        {
            get
            {
                return BestVector;
            }
            set
            {
                BestVector = new List<int>(value);
            }
        }

        public Data()
        {
        }
    }
}
