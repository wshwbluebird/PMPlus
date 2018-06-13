package main;

import bottom.BottomMonitor;
import bottom.BottomService;
import bottom.Constant;
import bottom.Task;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 测试用例类
 */
public class Test {

    private String tester = "Sample1";

    public void setTester(String tester) {
        this.tester = tester;
    }

    /**
     * @author liangjiaming
     * 测试样例
     */
    public void testSample(){
        try {
            runTest(2,"src/testFile/textSample.txt", "testSample");
//            for (int i = 1; i <= 1; i++) {
//                runTestCsv(4, "rand_" + i);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author liangjiaming
     *
     * @param cpuNumber  cpu数量
     * @param testName   测试样例名称
     */
    private void runTestCsv(int cpuNumber, String testName) {
        System.out.println("Running test " + testName + " on " + tester);
        Thread t = new Thread(() -> {
            try {
                runTest(cpuNumber, "src/testFile/" + testName + ".csv", testName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            t.join(Constant.RUNNING_MS);
            if (t.isAlive()) {
                System.out.println("Timed out");
                t.stop();
                recordFail(testName, "Timed out");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void runTest(int cpuNumber, String fileName, String testName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BottomMonitor bottomMonitor = new BottomMonitor(fileName,cpuNumber);
        BottomService bottomService = new BottomService(bottomMonitor);
        Class  clazz = Class.forName("work."+tester);
        Schedule schedule = (Schedule) clazz.newInstance();
        schedule.setBottomService(bottomService);

        try {
            for (int i = 0; i < Constant.ITER_NUM; i++) {
                Task[] tasks = bottomMonitor.getTaskArrived();
                int[] cpuOperate = new int[cpuNumber];
                schedule.ProcessSchedule(tasks, cpuOperate);
                try {
                    bottomService.runCpu(cpuOperate);
                } catch (Exception e) {
                    recordFail(testName, e.getMessage());
                    return;
                }
                bottomMonitor.increment();
            }
        } catch (Exception e) {
            recordFail(testName, String.valueOf(e));
            return;
        }

//        //打印统计结果
//        bottomMonitor.printStatistics();
//        System.out.println();
//
//        //打印任务队列
//        bottomMonitor.printTaskArrayLog();
//        System.out.println();
//
//        //打印cpu日志
//        bottomMonitor.printCpuLog();
        if(bottomMonitor.isAllTaskFinish()){
            recordSuccess(bottomMonitor.getReadCnt(), bottomMonitor.getWriteCnt(),
                    bottomMonitor.getCpuChangeTimes(), bottomMonitor.getToleranceValue(),
                    testName);
        }else{
            recordFail(testName," At least one task has not been completed! ");
        }

    }

    private void recordSuccess(long readCnt, long writeCnt, long cpuChange, long tolerance, String testName) throws IOException {
        String sb = "TestCase: " + testName + '\n' +
                "Memory Read Count:  " + readCnt + '\n' +
                "Memory Write Count: " + writeCnt + '\n' +
                "Cpu environment change time: " + cpuChange + '\n' +
                "Tolerance (lower is better): " + tolerance + '\n' +
                "\n";
        Files.write(Paths.get("src/result/"+tester), sb.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);


    }

    private void recordFail( String testName, String cause) throws IOException {
        String sb = "TestCase: " + testName + '\n' +
                "Fail! "+ cause +"\n\n";

        Files.write(Paths.get("src/result/"+tester), sb.getBytes(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);

    }
}
