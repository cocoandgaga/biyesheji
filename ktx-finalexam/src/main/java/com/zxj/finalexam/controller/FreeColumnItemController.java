package com.zxj.finalexam.controller;

import com.zxj.common.utils.R;
import com.zxj.common.utils.RRException;
import com.zxj.finalexam.dao.StuTaskDao;
import com.zxj.finalexam.dao.UserTaskDao;
import com.zxj.finalexam.entity.FreeColumnItem;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.entity.UserTaskEntity;
import com.zxj.finalexam.service.FreeColumnItemService;
import com.zxj.finalexam.vo.BoardVo;
import com.zxj.finalexam.vo.TaskCard;
import com.zxj.finalexam.vo.UserStoryCard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/finalexam/board")
public class FreeColumnItemController {

    @Autowired
    private FreeColumnItemService freeColumnItemService;
    @Autowired
    private UserTaskDao userTaskDao;
    @Autowired
    private StuTaskDao stuTaskDao;

    @RequestMapping("/queryBoardData")
    public R queryBoardData(@RequestBody BoardVo boardVo, HttpServletRequest request) {

        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (Objects.nonNull(boardVo.getDeptId())) {
            return R.error("deptId参数错误");
        }
        boardVo.setStuId(user.getUserId());
        boardVo.setDeptId(user.getDeptId());

        List<UserStoryCard> userStoryCardList = freeColumnItemService.queryBoardData(boardVo);
        return R.ok().put("userStoryCardList", userStoryCardList);
    }


    /**
     * 用户故事有效性校验，故事不存在或被删除则返回异常
     */
    public UserTaskEntity validateUserStory(String userStoryId) {
        if (StringUtils.isBlank(userStoryId)) {
            throw new RRException("用户故事参数不能为空");
        }
        UserTaskEntity userStory = userTaskDao.selectById(userStoryId);
        if (userStory == null) {
            throw new RRException("该用户故事不存在或已被删除,请刷新页面");
        }
        return userStory;
    }

    private String validate(FreeColumnItem item) {
        if (StringUtils.isEmpty(item.getStuTaskId())) {
            return "卡片参数错误";
        }
        if (StringUtils.isEmpty(item.getColumnId())) {
            return "位置参数错误";
        }
        if (item.getStatusColumnId() == null) {
            return "位置参数错误";
        }
        if (item.getType() == null) {
            return "类型参数错误";
        }
        return null;
    }

    /**
     * 学生任务卡片移动
     *
     * @param card 卡片
     * @return 结果
     */
    @RequestMapping("/moveCardItem")
    public R moveCardItem(@RequestBody FreeColumnItem card, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(validate(card))) {
            return R.error(validate(card));
        }
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user==null) return R.error("请登录");
        //老师发布的任务有效性验证
        this.validateUserStory(card.getUserTaskId());
        String msg = null;
        int type = card.getType();
        if (type == 1) {
            msg = "学生不可拖动故事卡片";
        } else if (type == 2) {
            msg = freeColumnItemService.moveStuTaskCard(card);
        }
        if (StringUtils.isNotEmpty(msg)) {
            return R.error(msg);
        } else {
            TaskCard taskCard = stuTaskDao.getTaskAndPosition(card.getStuTaskId());
            return R.ok().put("taskCard", taskCard);
        }
    }


}
