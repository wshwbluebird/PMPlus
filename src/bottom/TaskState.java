package bottom;

public class TaskState {
    private int tid;
    private int cpuTime;
    private int leftCpuTime;
    private int[] resource;
    private int arriveTime;
    private int finishTime;

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

    public int[] getResource() {
        return resource;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getLeftCpuTime() {
        return leftCpuTime;
    }

    public void executeCurrentTask(){
        if(leftCpuTime > 0)
            this.leftCpuTime--;
    }
}
