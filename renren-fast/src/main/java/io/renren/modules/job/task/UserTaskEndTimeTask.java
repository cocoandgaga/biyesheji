package io.renren.modules.job.task;

import io.renren.modules.job.dao.ScheduleJobDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userTaskEndTimeTask")
public class UserTaskEndTimeTask  implements ITask{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScheduleJobDao scheduleJobDao;


    @Override
    public void run(String params){
        scheduleJobDao.updateUserTaskBatch();
    }
}
