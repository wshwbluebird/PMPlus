package bottom;

public class BottomService {
    private byte[] freeMemory;
    private BottomMonitor bottomMonitor;

    public BottomService(BottomMonitor bottomMonitor){
        freeMemory = new byte[Constant.FREE_MEM_SIZE];
        this.bottomMonitor = bottomMonitor;
    }

    public int getCpuNumber(){
        return bottomMonitor.getCpuNumber();
    }

    /**
     * 对自有内存的读
     * 如果offset超过范围则返回0
     * @param offset
     * @return
     */
    public byte readFreeMemory(int offset){
        bottomMonitor.recordMemoryRead();
        if (offset >= Constant.FREE_MEM_SIZE || offset < 0){
            return 0;
        }
        return freeMemory[offset];
    }

    /**
     * 对自由内存的写
     * 如果offset超过范围则写无效
     * @param offset
     * @param x
     */
    public void writeFreeMemory(int offset, byte x){
        bottomMonitor.recordMemoryWrite();
        if(offset >= Constant.FREE_MEM_SIZE || offset < 0){
            return;
        }
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
