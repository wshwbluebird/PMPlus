package main;

import bottom.BottomMonitor;
import bottom.BottomService;
import bottom.Task;

/**
 * 测试用例类
 */
public class Test {
    public void test01(){
        try {
            runTest(2,"src/testFile/test1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO 将打印信息 改成统计信息
    private void runTest(int cpuNumber, String fileName) throws Exception {
        BottomMonitor bottomMonitor = new BottomMonitor(fileName,cpuNumber);
        BottomService bottomService = new BottomService(bottomMonitor);
        Schedule schedule = new Schedule(bottomService);
        for(int i = 0 ; i < 8 ; i++){
            Task[] tasks = bottomMonitor.getTaskArrived();
            if(tasks.length !=0){
                System.out.println("time: "+i);
                for(int j = 0 ; j < tasks.length ; j++ ){
                    Task task = tasks[j];
                    System.out.println(task.toString());
                }
                System.out.println();
            }
            int[] cpuOperate = new int[cpuNumber];
            schedule.ProcessSchedule(tasks,cpuOperate);
            System.out.print("operate: ");
            for(Integer d : cpuOperate){
                System.out.print(d+" ");
            }
            System.out.println();
            bottomService.runCpu(cpuOperate);
            bottomMonitor.increment();
        }
    }
}
