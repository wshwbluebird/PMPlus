package work;

import bottom.Task;
import main.Schedule;

public class S161250xxx extends Schedule{


    @Override
    public void ProcessSchedule(Task[] arrivedTask, int[] cpuOperate) {
        int time = getTimeTick();
        switch (time) {
            case 0:
                cpuOperate[0] = 1;
                break;
            case 1:
                cpuOperate[0] = 2;
                break;
//            case 2: cpuOperate[0] = 2; break;
            case 3:
                cpuOperate[0] = 3;
                break;
            case 4:
                cpuOperate[0] = 3;
                break;
            case 5:
                cpuOperate[0] = 4;
                break;
            case 10:
                cpuOperate[0] = 4;
                break;
            case 40:
                cpuOperate[0] = 2;
                break;
            default:
        }
    }
}
