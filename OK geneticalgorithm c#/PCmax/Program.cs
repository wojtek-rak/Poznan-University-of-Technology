using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PCmax
{
    class Program
    {
        private const bool onePath = true;
        private const bool liveInstances = true;
        private static string[] paths = new string[]{
            "m10.txt",
            "m20.txt",
            "m25.txt",
            "m50.txt",
            "m10n200.txt",
            "m50n200.txt",
            "m50n200lpt.txt",
            "m50n1000.txt",
        };

        static void Main(string[] args)
        {
            
            if(liveInstances)
            {
                if (onePath)
                {
                    paths = new string[]{
                        //"m50n200.txt",
                        //"m10.txt",
                        "m50.txt",
                        //"m10n200.txt",

                    };
                }
                foreach (var path in paths)
                {
                    var main = new Main();
                    Console.WriteLine();
                    Console.WriteLine(path);
                    main.Startup(path);
                }

            }
            else
            {
                var main = new Main();
                main.Startup(null);
            }
            Console.ReadKey();

        }
    }
}
