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
        int time = getTimeTick();
        switch (time){
//            case 0: cpuOperate[0] = 1; break;
//            case 1: cpuOperate[0] = 2; break;
//            case 2: cpuOperate[0] = 2; break;
//            case 3: cpuOperate[0] = 3; break;
//            case 4: cpuOperate[0] = 3; break;
//            case 5: cpuOperate[0] = 4; break;
//            case 10: cpuOperate[0] = 4; break;
//            case 11: cpuOperate[0] = 2; break;
//            default:
        }
    }
}
