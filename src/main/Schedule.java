package main;

import bottom.BottomService;
import bottom.Task;


/**
 * 调度类
 */
public abstract class Schedule {

    private BottomService bottomService;

    final public void setBottomService(BottomService bottomService) {
        this.bottomService = bottomService;
    }

    public Schedule(){

    }

    /**
     * 获得当前的时间片
     * @return
     */
    final protected int getTimeTick(){
        return bottomService.getTimeTick();
    }

    /**
     * 获得cpu数量
     * @return
     */
    final protected int getCpuNumber(){
        return bottomService.getCpuNumber();
    }

    /**
     * 从自由内存中读取一个byte
     * 如果offset超过范围则返回0
     * @param offset
     * @return
     */
    final protected byte readFreeMemory(int offset){
        return bottomService.readFreeMemory(offset);
    }

    /**
     * 向自由内存中写一个byte
     * 如果offset超过范围则写无效
     * @param offset
     * @param x
     */
    final protected void writeFreeMemory(int offset, byte x){
        bottomService.writeFreeMemory(offset, x);
    }

    /**
     * 进程调度代码
     * @param arrivedTask 到达任务数组， 数组长度不定
     * @param cpuOperate  （返回值）cpu操作数组  数组长度为cpuNumber
     *                    cpuOperate[0] = 1 代表cpu0在当前时间片要执行任务1
     *                    cpuOperate[1] = 2 代表cpu1在当前时间片要执行任务2
     *                    cpuOperate[2] = 0 代表cpu1在当前时间片空闲什么也不做
     */
     public abstract void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate);
}
