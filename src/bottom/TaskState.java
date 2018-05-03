package bottom;

public class TaskState {
    int tid;
    int cpuTime;
    int leftCpuTime;
    int[] resource;
    int arriveTime;
    int finishTime;

    public TaskState(int tid, int cpuTime,int[] resource,int arriveTime){
        this.tid = tid;
        this.cpuTime = cpuTime;
        this.resource = resource;
        this.arriveTime = arriveTime;
        this.leftCpuTime = cpuTime;
        this.finishTime = -1;
    }

    boolean isFinish(){
        return  finishTime!=-1;
    }

    boolean isArrived(int timeTick){
        return timeTick >= arriveTime;
    }

}
