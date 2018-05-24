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

    int[] getResource() {
        return resource;
    }

    void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    int getLeftCpuTime() {
        return leftCpuTime;
    }

    void executeCurrentTask(){
        if(leftCpuTime > 0)
            this.leftCpuTime--;
    }

    /**
     * 计算该任务的不满意得分
     * @return
     */
    int getToleranceValue(){
        if(!isFinish()) return -1;
        int idlWait = finishTime - arriveTime + 1;
        assert idlWait >= 0 : "wrong time finish";

        // TODO change magic number
        if(idlWait == 0)  return 0;
        if(idlWait < 10) return 2;
        if(idlWait < 32) return 2 * idlWait;
        else  return 2<<(idlWait-24);

    }
}
