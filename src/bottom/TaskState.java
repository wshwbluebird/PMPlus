package bottom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        this.resource = getUniResource(resource);
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

    private int[] getUniResource(int[] resource){
        Arrays.sort(resource);
        List<Integer> list = new ArrayList<>();
        list.add(resource[0]);
        for(int i=1;i<resource.length;i++){
            if(!(resource[i]==(list.get(list.size()-1)))){
                list.add(resource[i]);
            }
        }

        int[] ans = new int[list.size()];
        for(int i = 0 ; i < list.size() ; i++){
            ans[i] = list.get(i);
        }
        return ans;
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
        else  return (idlWait-24)*(idlWait-24);

    }
}
