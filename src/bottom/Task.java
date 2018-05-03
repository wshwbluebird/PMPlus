package bottom;

import java.util.Arrays;

public class Task {
    public int tid;
    public int cpuTime;
    public int[] resource;

    public Task(int tid, int cpuTime, int[] resource){
        this.cpuTime = cpuTime;
        this.tid = tid;
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "tid:"+tid+" cpuTime:"+cpuTime+" resource length:"+resource.length;
    }
}
