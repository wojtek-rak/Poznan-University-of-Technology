using System.Collections.Generic;
using System.Linq;
using Moq;
using NUnit.Framework;

namespace PCmax.Tests
{
    [TestFixture]
    public class PCmaxGeneticTests
    {
        [Test]
        public void UpgradeGenome_UpgradeGenome_BetterScoring()
        {
            var main = new Mock<Main>();
            var data = new Mock<Data>(); 
            var mainObject = main.Object;
            mainObject.numberOfThreads = 3;
            var genetic = new Genetic(mainObject, data.Object);

            var testList = new List<Gene>
            {
                new Gene(0, 10, 0),
                new Gene(1, 10, 0),
                new Gene(2, 10, 0),
                new Gene(3, 10, 1),
                new Gene(4, 10, 0),
                new Gene(5, 10, 2),
                new Gene(6, 10, 1),
            };
            var expected = testList.Count(x => x.machineId == 2) + 1;
            testList = genetic.UpgradeGenome(testList);
            var result = testList.Count(x => x.machineId == 2) ;
            Assert.AreEqual(expected, result);
        }

    }
}