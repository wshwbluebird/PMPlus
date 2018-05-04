package bottom;


public class Task {
    public int tid;  //任务ID
    public int cpuTime;  //该任务需要在cpu上执行多长时间
    public int[] resource;  //该任务需要的资源id

    public Task(int tid, int cpuTime, int[] resource){
        this.cpuTime = cpuTime;
        this.tid = tid;
        this.resource = resource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if(resource.length != 0)
        sb.append(resource[0]);

        for(int i = 1 ;i < resource.length ; i++){
            sb.append(",");
            sb.append(resource[i]);
        }
        sb.append(']');
        return "TaskID:"+tid+"  Need cpuTime:"+cpuTime+"  Need resource:"+sb.toString();
    }
}
