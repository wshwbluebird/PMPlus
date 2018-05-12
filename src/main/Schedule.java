package main;

import bottom.BottomService;
import bottom.Constant;
import bottom.Task;


/**
 * 调度类
 */
public abstract class Schedule {
    private BottomService bottomService;


    public void setBottomService(BottomService bottomService) {
        this.bottomService = bottomService;
    }

    public Schedule(){

    }

    /**
     * 获得当前的时间片
     * @return
     */
    protected int getTimeTick(){
        return bottomService.getTimeTick();
    }

    /**
     * 获得cpu数量
     * @return
     */
    protected int getCpuNumber(){
        return bottomService.getCpuNumber();
    }

    /**
     * 从自由内存中读取一个byte
     * 如果offset超过范围则返回0
     * @param offset
     * @return
     */
    protected byte readFreeMemory(int offset){
        return bottomService.readFreeMemory(offset);
    }

    /**
     * 向自由内存中写一个byte
     * 如果offset超过范围则写无效
     * @param offset
     * @param x
     */
    public void writeFreeMemory(int offset, byte x){
        bottomService.writeFreeMemory(offset, x);
    }

    /**
     * 进程调度代码
     * @param arrivedTask
     * @param cpuOperate
     */
     public abstract void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate);
}
