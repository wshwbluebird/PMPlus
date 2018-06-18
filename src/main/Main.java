package main;


import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 运行类
 */
public class Main {
    public static void main(String[]  args) throws Exception {
        Files.write(Paths.get("src/result/correctness.csv"), "number,result\n".getBytes());
        Test test = new Test();
        Files.list(Paths.get("src/work")).forEach(t->{
            String tester = t.getFileName().toString();
            if(tester.endsWith(".java")){
                tester = tester.substring(0,tester.length()-5);
                test.setTester(tester);
                test.clearPassNum();
                test.testSample();
            }

        });
    }


}

