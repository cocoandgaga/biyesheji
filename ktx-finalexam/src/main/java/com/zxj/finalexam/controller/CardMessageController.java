package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.CardMessageDao;
import com.zxj.finalexam.dao.StuUserDao;
import com.zxj.finalexam.entity.CardMessageEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("finalexam/cardMessage")
public class CardMessageController {

    @Autowired
    private CardMessageDao cardMessageDao;
    @Autowired
    private StuUserDao stuUserDao;

    @RequestMapping("/queryCardMessage")
    public R queryCardMessage(@RequestBody CardMessageEntity cardMessage, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        Long creator = (Long) request.getSession().getAttribute("creator");

        if (StringUtils.isBlank(cardMessage.getUserTaskId())) {
            return R.error("老师发布的卡片参数错误");
        }
        if (user == null && creator == null) return R.error("请登录");
        List<Long> userIdLst = new ArrayList<>();
        List<CardMessageEntity> cardMessages = null;
        if (user != null) {
            if(cardMessage.getType()==2) {
                if (StringUtils.isBlank(cardMessage.getStuTaskId())) {
                    return R.error("参数错误");
                }
                cardMessages = cardMessageDao.selectList(new QueryWrapper<CardMessageEntity>().eq("STU_TASK_ID", cardMessage.getStuTaskId())
                        .eq("TYPE",2)
                        .orderBy(true, true, "CREATE_TIME"));
                cardMessages.forEach(cardMeg -> cardMeg.setUserName(user.getNickName()));
            }else if (cardMessage.getType()==1){
                cardMessages = cardMessageDao.selectList(new QueryWrapper<CardMessageEntity>()
                        .eq("USER_TASK_ID", cardMessage.getUserTaskId())
                        .eq("TYPE",1)
                        .orderBy(true, true, "CREATE_TIME"));

            }else{
                return R.error("卡片类型未知错误");
            }

        }
        if (creator != null) {
            cardMessages = cardMessageDao.selectList(new QueryWrapper<CardMessageEntity>()
                    .eq("USER_TASK_ID", cardMessage.getUserTaskId())
                    .eq("TYPE",1)
                    .orderBy(true, true, "CREATE_TIME"));
        }
       if(cardMessage.getType()==1) {
           cardMessages.forEach(cardMeg -> userIdLst.add(cardMeg.getUserId()));
           if (!userIdLst.isEmpty()) {
               List<StuUserEntity> stus = stuUserDao.selectBatchIds(userIdLst);
               Map<Long, String> userMap = stus.stream().collect(Collectors.toMap(StuUserEntity::getUserId, StuUserEntity::getNickName));
               cardMessages.forEach(cardMeg -> cardMeg.setUserName(userMap.get(cardMeg.getUserId())));
           }
       }

        return R.ok().put("cardMessages", cardMessages);
    }


    @RequestMapping("/saveCardMessage")
    @Transactional
    public R saveCardMessage(@RequestBody CardMessageEntity cardMessage, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录");
        String content = cardMessage.getContent();
        String stuTaskId = cardMessage.getStuTaskId();
        String userTaskId = cardMessage.getUserTaskId();

        if(cardMessage.getType()==1){//位置在故事
            if (StringUtils.isAnyBlank(content,userTaskId)) {
                return R.error("卡片参数错误");
            }
            cardMessage.setStuTaskId(stuTaskId);
            cardMessage.setType(1);
        }else if (cardMessage.getType()==2){//位置在任务
            if (StringUtils.isAnyBlank(content,stuTaskId,userTaskId)) {
                return R.error("卡片参数错误");
            }
            cardMessage.setType(2);
        }
        cardMessage.setCreateTime(new Date());
        cardMessage.setUserTaskId(userTaskId);
        cardMessage.setContent(content);
        cardMessage.setUserId(user.getUserId());
        cardMessageDao.insert(cardMessage);
        return R.ok().put("cardMessage", cardMessage);
    }

}
