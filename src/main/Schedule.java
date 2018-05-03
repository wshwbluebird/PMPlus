package main;

import bottom.BottomService;
import bottom.Task;


/**
 * 调度类
 */
public class Schedule {
    private BottomService bottomService;

    public Schedule(BottomService bottomService){
        this.bottomService = bottomService;
    }

    /**
     * 获得当前的时间片
     * @return
     */
    private int getTimeTick(){
        return bottomService.getTimeTick();
    }

    /**
     * 获得cpu数量
     * @return
     */
    private int getCpuNumber(){
        return bottomService.getCpuNumber();
    }

    /**
     * 进程调度代码
     * @param arrivedTask
     * @param cpuOperate
     */
    public void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate){
        /**
         *  write your code
         */
//        if(arrivedTask.length == 0) return;
//        int length = arrivedTask.length;
//        if(length == 1) {
//            cpuOperate[0] = arrivedTask[0].tid;
//            cpuOperate[1] = 1;
//        }
//        else {
//            cpuOperate[0] = arrivedTask[0].tid;
//            cpuOperate[1] = arrivedTask[1].tid;
//        }
    }
}
