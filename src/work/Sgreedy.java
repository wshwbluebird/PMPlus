package work;

import bottom.BottomMonitor;
import bottom.BottomService;
import bottom.Task;
import main.Schedule;

import java.io.IOException;

/**
 *  demo：样例版答案
 *  贪心法进程调度策略
 *  采用位示图的方法存储资源占有情况
 *  使用连续存储存放任务pcb
 *  每轮执行资源可用的，没有执行完的任务
 */

public class Sgreedy extends Schedule{

    // 最新任务ID其实存放地址
    private static final int latestTaskBeginner = 0;
    // cpu状态起始地址
    private static final int cpuStateBeginner = latestTaskBeginner + 4;
    // 资源位示图起始地址
    private static final int resourceBeginner = cpuStateBeginner + 5 * 4;
    // pcb寻址表起始地址
    private static final int pcbBitBeginner = resourceBeginner + 128;
    // pcb存储空间起始地址
    private static final int pcbBeginner = pcbBitBeginner + 1000*4;

    private static final int PCB_tidBeginner = 0;
    private static final int PCB_arrivedTimeBeginner = 4;
    private static final int PCB_cpuTimeBeginner = 8;
    private static final int PCB_leftTimeBeginner = 12;
    private static final int PCB_rsLengthBeginner = 16;
    private static final int PCB_resourceBeginner = 20;


    @Override
    public void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate) {
        if(arrivedTask != null && arrivedTask.length != 0){
            for(Task task : arrivedTask){
                recordTask(task, getTimeTick());
            }
        }

        cleanAllResource();
        int cpuNumber = getCpuNumber()-1;
        int taskNumber = readInteger(latestTaskBeginner);
        for(int i = 1 ; i <= taskNumber && cpuNumber >= 0; i++){
            if(isTaskFinish(i)) continue;
            if(useResource(i)){
                cpuOperate[cpuNumber--] = i;
                countDownLeft(i);
            }
        }
    }


    /**
     * 向自由内存中 读一个int型整数
     * @param beginIndex
     * @return
     */
    private int readInteger(int beginIndex){
        int ans = 0;
        ans += (readFreeMemory(beginIndex)&0xff)<<24;
        ans += (readFreeMemory(beginIndex+1)&0xff)<<16;
        ans += (readFreeMemory(beginIndex+2)&0xff)<<8;
        ans += (readFreeMemory(beginIndex+3)&0xff);
        return ans;
    }

    /**
     * 向自由内存中写一个int型整数
     * @param beginIndex
     * @param value
     */
    private void writeInteger(int beginIndex, int value){
        writeFreeMemory(beginIndex+3, (byte) ((value&0x000000ff)));
        writeFreeMemory(beginIndex+2, (byte) ((value&0x0000ff00)>>8));
        writeFreeMemory(beginIndex+1, (byte) ((value&0x00ff0000)>>16));
        writeFreeMemory(beginIndex, (byte) ((value&0xff000000)>>24));
    }

    /**
     * 在自由内存的pcb中
     * @param task
     * @param arrivedTime
     */
    private void recordTask(Task task, int arrivedTime){
        int newIndex = getNewTaskBeginIndex();
        writeInteger(newIndex+PCB_tidBeginner, task.tid);
        writeInteger(newIndex+PCB_arrivedTimeBeginner, arrivedTime);
        writeInteger(newIndex+PCB_cpuTimeBeginner, task.cpuTime);
        writeInteger(newIndex+PCB_leftTimeBeginner, task.cpuTime);
        writeInteger(newIndex+PCB_rsLengthBeginner, task.resource.length);
        for(int i = 0 ; i < task.resource.length; i++) {
            writeFreeMemory(newIndex+PCB_resourceBeginner+i, (byte) task.resource[i]);
        }
        writeInteger(latestTaskBeginner, task.tid);
        writeInteger(pcbBitBeginner+task.tid*4, newIndex);
    }

    /**
     * 获得这个任务需要的资源长度
     * @param taskID
     * @return
     */
    private int getTaskResourceLength(int taskID){
        return readInteger(getTaskBeginIndex(taskID)+PCB_rsLengthBeginner);
    }

    /**
     * 获得存储该任务pcb的内存地址
     * @param taskID
     * @return
     */
    private int getTaskBeginIndex(int taskID){
        return readInteger(pcbBitBeginner+taskID*4);
    }

    /**
     * 获得新到达任务 存放内存地址
     * @return
     */
    private int getNewTaskBeginIndex(){
        int latestTaskID = readInteger(latestTaskBeginner);
        if(latestTaskID == 0) return pcbBeginner;
        return getTaskBeginIndex(latestTaskID)+getTaskResourceLength(latestTaskID)+PCB_resourceBeginner;
    }



    public void printAllTask(){
        int last =  readInteger(latestTaskBeginner);
        for(int i = 1 ; i <= last ; i++){
            int beginIndex = getTaskBeginIndex(i);
            System.out.println("Task ID: "+readInteger(beginIndex+PCB_tidBeginner));
            System.out.println("Task arrivedTime: "+readInteger(beginIndex+PCB_arrivedTimeBeginner));
            System.out.println("Task cpuTime: "+readInteger(beginIndex+PCB_cpuTimeBeginner));
            System.out.println("Task leftTime: "+readInteger(beginIndex+PCB_leftTimeBeginner));
            System.out.println("Task ResourceLength: "+readInteger(beginIndex+PCB_rsLengthBeginner));
            System.out.print("Task resource: ");
            int length = readInteger(beginIndex+PCB_rsLengthBeginner);
            for(int j = 0 ; j < length ; j++){
                System.out.print(readFreeMemory(beginIndex+PCB_resourceBeginner+j)+", ");
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * 查看资源是否可用
     * @param taskID
     * @return
     */
    private boolean useResource(int taskID){
        int index = getTaskBeginIndex(taskID);
        int length = readInteger(index+PCB_rsLengthBeginner);

        for(int i = 0 ; i < length ; i++){
            byte temp = readFreeMemory(index+PCB_resourceBeginner+i);
            if(readFreeMemory(resourceBeginner+temp-1) != 0) return false;
        }

        for(int i = 0 ; i < length ; i++){
            byte temp = readFreeMemory(index+PCB_resourceBeginner+i);
            writeFreeMemory(resourceBeginner+temp-1, (byte) 1);
        }

        return true;
    }

    /**
     * 记录剩余时间-1
     * @param taskID
     */
    private void countDownLeft(int taskID){
        int index = getTaskBeginIndex(taskID);
        int leftTime = readInteger(index+PCB_leftTimeBeginner);
        if(leftTime == 0) return;
        leftTime--;
        writeInteger(index+PCB_leftTimeBeginner, leftTime);
    }

    /**
     * 判断任务是否执行完毕
     * @param taskID
     * @return
     */
    private boolean isTaskFinish(int taskID){
        int index = getTaskBeginIndex(taskID);
        int leftTime = readInteger(index+PCB_leftTimeBeginner);
        return leftTime == 0;
    }

    /**
     * 将所有资源设为可用
     */
    private void cleanAllResource(){
        for(int i = 0 ; i < 128 ; i++){
            writeFreeMemory(resourceBeginner+i, (byte) 0);
        }
    }


    /**
     * 执行主函数 用于debug
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int cpuNumber = 2;
        BottomMonitor bottomMonitor = new BottomMonitor("src/testFile/textSample.txt",cpuNumber);
        BottomService bottomService = new BottomService(bottomMonitor);
        Schedule schedule =  new Sgreedy();
        schedule.setBottomService(bottomService);

        for(int i = 0 ; i < 1000 ; i++){
            Task[] tasks = bottomMonitor.getTaskArrived();
            int[] cpuOperate = new int[cpuNumber];
            schedule.ProcessSchedule(tasks,cpuOperate);
            try {
                bottomService.runCpu(cpuOperate);
            } catch (Exception e) {
                System.out.println("Fail: "+e.getMessage());
                return;
            }
            bottomMonitor.increment();
        }

        //打印统计结果
        bottomMonitor.printStatistics();
        System.out.println();

        //打印任务队列
        bottomMonitor.printTaskArrayLog();
        System.out.println();

        //打印cpu日志
        bottomMonitor.printCpuLog();
        if(bottomMonitor.isAllTaskFinish()){
//            System.out.println("Success ");
        }else{
            System.out.println(" Fail: At least one task has not been completed! ");
        }


    }
}
