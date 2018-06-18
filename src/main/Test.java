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

    private int passNum =500;

    public void setTester(String tester) {
        this.tester = tester;
    }

    public void clearPassNum() {
        this.passNum = 0;
    }

    /**
     * @author liangjiaming
     * 测试样例
     */
    public void testSample(){
        try {
            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_" + i);
            }
            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_long500_" + i);
            }
            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_long750_" + i);
            }

            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_long50_res50_" + i);
            }

            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_long250_res50_" + i);
            }

            for (int i = 1; i <= 20; i++) {
                runTestCsv(4, "rand_long500_res50_" + i);
            }

            for (int i = 150; i <= 200; i++) {
                runTestCsv(4, "rand_dup_res_" + i);
            }

            runTestCsv(4, "test_all_resource");
            runTestCsv(4, "test_long_task");
            runTestCsv(4, "test_multi_res");
            runTestCsv(4, "test_single_res");
            runTestCsv(4, "test_task_500");
            runTestCsv(4, "test_task_750");
            runTestCsv(4, "test_task_1000");

            recordResult(tester,passNum);

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
        //System.out.println("Running test " + testName + " on " + tester);
        Thread t = new Thread(() -> {
            try {
                boolean re = runTest(cpuNumber, "src/testFile/" + testName + ".csv", testName);
                if (re) passNum++;
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


    private boolean runTest(int cpuNumber, String fileName, String testName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
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
                    return false;
                }
                bottomMonitor.increment();
            }
        } catch (Exception e) {
            recordFail(testName, String.valueOf(e));
            return false;
        }


        if(bottomMonitor.isAllTaskFinish()){
            recordSuccess(bottomMonitor.getReadCnt(), bottomMonitor.getWriteCnt(),
                    bottomMonitor.getCpuChangeTimes(), bottomMonitor.getToleranceValue(),
                    testName);
            return true;
        }else{
            recordFail(testName," At least one task has not been completed! ");
            return false;
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

    private void recordResult(String testName, int passNum) throws IOException {
        String res = testName+","+passNum+"\n";
        Files.write(Paths.get("src/result/correctness.csv"), res.getBytes(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
