namespace PCmax
{
    public class Gene
    {
        public int index { get; set; }
        public int task { get; set; }
        public int machineId { get; set; }

        public Gene()
        {
        }

        public Gene(int index, int task, int machineId)
        {
            this.index = index;
            this.task = task;
            this.machineId = machineId; 
        }
    }
}