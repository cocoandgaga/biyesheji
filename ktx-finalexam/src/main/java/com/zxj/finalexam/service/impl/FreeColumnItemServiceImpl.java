package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.RRException;
import com.zxj.finalexam.dao.CardMessageDao;
import com.zxj.finalexam.dao.FreeColumnItemDao;
import com.zxj.finalexam.dao.StuTaskDao;
import com.zxj.finalexam.dao.UserTaskDao;
import com.zxj.finalexam.entity.CardMessageEntity;
import com.zxj.finalexam.entity.FreeColumnItem;
import com.zxj.finalexam.entity.StuTaskEntity;
import com.zxj.finalexam.entity.UserTaskEntity;
import com.zxj.finalexam.service.FreeColumnItemService;
import com.zxj.finalexam.vo.BoardVo;
import com.zxj.finalexam.vo.TaskCard;
import com.zxj.finalexam.vo.UserStoryCard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service("freeColumnItemService")
public class FreeColumnItemServiceImpl extends ServiceImpl<FreeColumnItemDao, FreeColumnItem> implements FreeColumnItemService {


    @Autowired
    private UserTaskDao userTaskDao;
    @Autowired
    private StuTaskDao stuTaskDao;
    @Autowired
    private FreeColumnItemDao freeColumnItemDao;
    @Autowired
    private CardMessageDao cardMessageDao;

    @Override
    public List<UserStoryCard> queryBoardData(BoardVo boardVo) {
        List<UserStoryCard> userStoryCards = userTaskDao.queryByBoardVo(boardVo);
        userStoryCards.forEach(userStoryCard -> userStoryCard.getTaskCardList().sort(Comparator.comparing(taskCard -> taskCard.getPosition().getSort())));
        return userStoryCards;
    }

    /**
     * 任务卡片移动
     *
     * @param freeColumnItem 卡片信息
     * @return 移动结果，null为正常
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String moveStuTaskCard(FreeColumnItem freeColumnItem) {

        //目标位置
        String stuTaskId = freeColumnItem.getStuTaskId();
        String userTaskId = freeColumnItem.getUserTaskId();
        //老师发布的任务状态判断，截止则不允许卡片移动
        UserTaskEntity userTask = userTaskDao.selectById(userTaskId);
        if (userTask.getUserTaskStatus() == 2) {
            throw new RRException("该老师发布的任务已截止！");
        }
        //目标位置大列信息ID
        String newColumnId = freeColumnItem.getColumnId();
        //目标位置大列序号 1-老师发布的任务 2-待办 3-任务完成情况
        Integer newColumnTypeSort = freeColumnItem.getColumnTypeSort();
        //目标位置小列序号 1-发布中 2-已截止 3-待办任务 4-进行中 5-已完成
        Integer newColumnStatusSort = freeColumnItem.getStatusColumnId();

        //获取原来的位置
        FreeColumnItem oldPosition = freeColumnItemDao.selectOne(new QueryWrapper<FreeColumnItem>()
                .eq("STU_TASK_ID", stuTaskId));
        if (oldPosition != null) {
            String oldColumnId = oldPosition.getColumnId();
            String oldStuTaskId = oldPosition.getStuTaskId();
            if (StringUtils.isEmpty(oldStuTaskId)) {
                throw new RRException("FreeColumnItem的stuTaskId为空");
            }

            //目标位置大列为待办任务，即拖回待办区
            if (newColumnTypeSort == 2) {
                //清空该卡片下的留言
                if (freeColumnItem.getClearTask()) {
                    cardMessageDao.delete(new QueryWrapper<CardMessageEntity>().eq("STU_TASK_ID", stuTaskId));
                }
                //清空开始结束时间
                stuTaskDao.clearStartEndTime(stuTaskId);
            } //从待办任务拖出 目标为任务情况列
            else if (newColumnTypeSort == 3) {
                //跨大列  待办列-->任务情况列
                if (!oldColumnId.equals(newColumnId)) {
                    if (newColumnStatusSort == 5) {
                        throw new RRException("该任务卡片尚未开始");
                    }
                }
                StuTaskEntity taskNew = new StuTaskEntity();
                taskNew.setStuTaskId(stuTaskId);
                StuTaskEntity taskOld = stuTaskDao.selectById(stuTaskId);
                //拖到进行中，将任务置为进行中，如果任务已经有开始时间，则不更新，并更新实际开始时间
                if (newColumnStatusSort == 4) {
                    if (taskOld != null && taskOld.getStartTime() == null) {
                        taskNew.setStartTime(new Date());
                    }
                    taskNew.setStuTaskStatus(4);
                    stuTaskDao.updateById(taskNew);
                } else if (newColumnStatusSort == 5) {
                    //拖到已完成，将任务置为已完成，并更新实际结束时间
                    taskNew.setStuTaskStatus(5);
                    taskNew.setEndTime(new Date());
                    stuTaskDao.updateById(taskNew);
                }
            }

            this.updatePosition(freeColumnItem);
        }

        return null;
    }

    /**
     * 更新卡片排序
     *
     * @param oldPosition 旧位置
     * @param newPosition 新位置
     */
  /*  private void updateSort(String userTaskId, FreeColumnItem oldPosition, FreeColumnItem newPosition) {
        int downFlag = 1;
        int upFlag = -1;
        Integer oldSort = oldPosition.getSort();
        String oldColumnId = oldPosition.getColumnId();
        Integer oldColumnStatusSort = oldPosition.getStatusColumnId();
        Integer newSort = newPosition.getSort();
        String newColumnId = newPosition.getColumnId();
        Integer newColumnStatusSort = newPosition.getStatusColumnId();
        if (oldColumnId.equals(newColumnId)) {
            //在同一个大列里调顺序
            if (oldColumnStatusSort.equals(newColumnStatusSort)) {

                int i = newSort.compareTo(oldSort);
                if (i == downFlag) {
                    //往下调，大于旧位置且小于新位置的-1
                    freeColumnItemDao.goDownSortUpdate(oldSort, newSort, userTaskId, newColumnId, newColumnStatusSort);
                } else if (i == upFlag) {
                    //往上调，大于新位置且小于旧位置的+1
                    freeColumnItemDao.goUpSortUpdate(oldSort, newSort, userTaskId, newColumnId, newColumnStatusSort);
                }
            } else {
                //从旧位置拖出，大于旧位置的-1
                freeColumnItemDao.goOutSortUpdate(oldSort, userTaskId, oldColumnId, oldColumnStatusSort);
                //拖入新位置，大于等于新位置的+1
                freeColumnItemDao.goInSortUpdate(newSort, userTaskId, newColumnId, newColumnStatusSort);
            }
        } else {
            //从旧位置拖出，大于旧位置的-1
            freeColumnItemDao.goOutSortUpdate(oldSort, userTaskId, oldColumnId, oldColumnStatusSort);
            //拖入新位置，大于等于新位置的+1
            freeColumnItemDao.goInSortUpdate(newSort, userTaskId, newColumnId, newColumnStatusSort);
        }


    }*/

    /**
     * 更新位置
     *
     * @param freeColumnItem
     * @return
     */
    private String updatePosition(FreeColumnItem freeColumnItem) {
        //更新排序
        FreeColumnItem oldPosition = freeColumnItemDao.selectOne(new QueryWrapper<FreeColumnItem>().eq("STU_TASK_ID", freeColumnItem.getStuTaskId()));
        if (oldPosition == null) {
            throw new RRException("获取卡片位置失败");
        }
//        this.updateSort(freeColumnItem.getUserTaskId(), oldPosition, freeColumnItem);
        //修改位置
        FreeColumnItem newPosition = new FreeColumnItem();
        newPosition.setUserTaskId(freeColumnItem.getUserTaskId());
        newPosition.setType(2);
        newPosition.setColumnTypeSort(freeColumnItem.getColumnTypeSort());
        newPosition.setStatusColumnId(freeColumnItem.getStatusColumnId());
        newPosition.setColumnId(freeColumnItem.getColumnId());
        newPosition.setSort(freeColumnItem.getSort());
        newPosition.setStuTaskId(freeColumnItem.getStuTaskId());
        freeColumnItemDao.update(newPosition, new QueryWrapper<FreeColumnItem>().eq("STU_TASK_ID", freeColumnItem.getStuTaskId()));
        return null;
    }

}
