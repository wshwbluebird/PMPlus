package main;

import bottom.BottomService;
import bottom.Task;


/**
 * 调度类
 */
public abstract class Schedule {
    protected BottomService bottomService;


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
     * 进程调度代码
     * @param arrivedTask
     * @param cpuOperate
     */
     public abstract void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate);
}
