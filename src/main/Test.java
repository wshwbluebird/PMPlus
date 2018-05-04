package main;

import bottom.BottomMonitor;
import bottom.BottomService;
import bottom.Task;

import java.io.IOException;

/**
 * 测试用例类
 */
public class Test {

    /**
     * 测试样例
     */
    public void testSample(){
        try {
            System.out.println("TestCase: testSample");
            System.out.print("result: ");
            runTest(2,"src/testFile/textSample.txt");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //TODO 将打印信息 改成统计信息
    private void runTest(int cpuNumber, String fileName) throws IOException {
        BottomMonitor bottomMonitor = new BottomMonitor(fileName,cpuNumber);
        BottomService bottomService = new BottomService(bottomMonitor);
        Schedule schedule = new Schedule(bottomService);

        for(int i = 0 ; i < 1000 ; i++){
            Task[] tasks = bottomMonitor.getTaskArrived();
            int[] cpuOperate = new int[cpuNumber];
            schedule.ProcessSchedule(tasks,cpuOperate);
            try {
                bottomService.runCpu(cpuOperate);
            } catch (Exception e) {
                System.out.println("Fail! "+e.getMessage());
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
    }
}
