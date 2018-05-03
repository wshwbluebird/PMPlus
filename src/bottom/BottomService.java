package bottom;

public class BottomService {
    private char[] freeMemory;
    private BottomMonitor bottomMonitor;

    public BottomService(BottomMonitor bottomMonitor){
        freeMemory = new char[Constant.FREE_MEM_SIZE];
        this.bottomMonitor = bottomMonitor;
    }

    public int getCpuNumber(){
        return bottomMonitor.getCpuNumber();
    }

    /**
     * 对自有内存的读
     * @param offset
     * @return
     */
    public char readFreeMemory(int offset){
        assert offset < Constant.FREE_MEM_SIZE && offset >= 0 : "bad offset";
        return freeMemory[offset];
    }

    /**
     * 对自由内存的写
     * @param offset
     * @param x
     */
    public void writeFreeMemory(int offset, char x){
        assert offset < Constant.FREE_MEM_SIZE && offset >= 0 : "bad offset";
        freeMemory[offset] = x;
    }

    /**int
     * 根据调度算法运行cpu
     * @param cpuOperates
     */
    public void runCpu(int[] cpuOperates) throws Exception {
        bottomMonitor.runCpu(cpuOperates);
    }

    /**
     * 获得时间片
     * @return
     */
    public int getTimeTick(){
        return bottomMonitor.getTimeTick();
    }









}
