package com.zxj.finalexam.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.CommentDao;
import com.zxj.finalexam.dao.NotificationDao;
import com.zxj.finalexam.dao.QuestionDao;
import com.zxj.finalexam.dto.PaginationDTO;
import com.zxj.finalexam.entity.*;
import com.zxj.finalexam.inf.ITelnetLoginService;
import com.zxj.finalexam.service.CommentService;
import com.zxj.finalexam.service.NotificationService;
import com.zxj.finalexam.service.QuestionService;
import com.zxj.finalexam.service.StuUserService;
import com.zxj.finalexam.service.impl.NotificationServiceImpl;
import com.zxj.finalexam.vo.CommentVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;


/**
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@RestController
@RequestMapping("finalexam/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ITelnetLoginService telnetLoginService;

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private StuUserService stuUserService;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private NotificationService notificationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = questionService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {


        QuestionEntity question = questionService.getById(id);
        question.setViewCnt(question.getViewCnt()+1);
        questionService.updateById(question);
        StuUserEntity stu = stuUserService.getById(question.getCreator());
        question.setCreatorName(stu.getNickName());
        return R.ok().put("question", question);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody QuestionEntity questionEntity, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("creator");
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (userId != null) {
            R userInfo = telnetLoginService.getUserInfo(userId);
            SysUserEntity sysUserEntity = JSON.parseObject(JSON.toJSONString(userInfo.get("user")), SysUserEntity.class);
            questionEntity.setGmtCreate(new Date());
            questionEntity.setCreator(sysUserEntity.getUserId());
            questionService.save(questionEntity);
            return R.ok();
        } else if (user != null) {
            questionEntity.setGmtCreate(new Date());
            questionEntity.setCreator(user.getUserId());
            questionService.save(questionEntity);
            return R.ok();
        } else {
            return R.error("请登录");
        }
    }


    @RequestMapping("/queryComments")
    public R queryComments(PaginationDTO paginationDTO) {
        List<CommentVO> comments = commentDao.queryCommentsByQuestionId(paginationDTO);
        if (comments.isEmpty()) return R.ok();
        return R.ok().put("questionComments", comments);
    }
    @RequestMapping("/replyComment")
    @Transactional
    public R replyComment(@RequestBody Comment comment, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录");
        comment.setParentId(comment.getCommentPid());
        comment.setType(2);
        comment.setCommentator(user.getUserId());
        comment.setGmtCreate(new Date());

        questionDao.addCommentCnt(comment.getQuestionId());

        Notification notification=new Notification();
        notification.setNotifier(user.getUserId())
                .setReceiver(comment.getReceiver())
                .setType(2).setGmtCreate(new Date())
                .setStatus(0).setNotifierName(user.getNickName())
                .setQuestionId(comment.getQuestionId())
                .setContent(comment.getContent())
                .setCommentPid(comment.getCommentPid());

        commentDao.insert(comment);
        commentDao.addCommentCnt(comment.getCommentPid());
        notificationDao.insert(notification);
       return  R.ok();

    }



    @RequestMapping("/saveComment")
    @Transactional
    public R saveComment(@RequestBody Comment comment, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录");
        comment.setCommentator(user.getUserId());
        comment.setCreatorName(user.getNickName());
        comment.setGmtCreate(new Date());
        Notification notification = new Notification();
        notification.setContent(comment.getContent());
        notification.setGmtCreate(new Date());
        notification.setReceiver(comment.getReceiver());
        notification.setNotifierName(user.getNickName());
        notification.setQuestionId(comment.getQuestionId());
        notification.setStatus(0);
        notification.setNotifier(user.getUserId());
        questionDao.addCommentCnt(comment.getQuestionId());
        if (comment.getType() == 1) {
            notification.setType(1);
            commentDao.insert(comment);
            notification.setCommentPid(comment.getId());
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setChildComments(new ArrayList<>());
            notificationDao.insert(notification);
            return R.ok().put("comment", commentVO);
        }

        if (comment.getType() == 2) {
            notification.setType(2);
            notification.setCommentPid(comment.getCommentPid());
            commentDao.addCommentCnt(comment.getCommentPid());
            commentDao.insert(comment);
            notificationDao.insert(notification);
            return R.ok().put("comment", comment);
        }
        return R.error("回复失败");
    }

    @RequestMapping("/get/notifications")
    public R getNotification(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录");
        Integer status = Integer.valueOf((String) params.get("status"));
          PageUtils page = notificationService.queryPage(params, user.getUserId(), status);
        Integer count=notificationDao.selectCount(new QueryWrapper<Notification>()
                .eq("RECEIVER",user.getUserId()).eq("STATUS",status));
        if (status == 0) {
            List<Notification> notifications = (List<Notification>) page.getList();
            notifications.forEach(notification -> notification.setStatus(1));
            notificationService.updateBatchById(notifications);
        }

        return R.ok().put("page", page).put("count",count);
    }
    @RequestMapping("/deleteComment/{commentId}/{questionId}/{pid}")
    public R deleteComment(@PathVariable("commentId") Long id,@PathVariable("pid") Long commentPid,@PathVariable("questionId")Long questionId) {
        commentDao.deleteById(id);
        if (commentPid!=-1){
            commentDao.subCommentCnt(commentPid);
        }
        questionDao.subCommentCnt(questionId);


        return R.ok();
    }


    @RequestMapping("/likeComment/{commentId}")
    public R likeComment(@PathVariable("commentId") Long id) {
        commentDao.addLikeCnt(id);
        return R.ok();
    }
    @RequestMapping("/unlikeComment/{commentId}")
    public R unlikeComment(@PathVariable("commentId") Long id) {
        commentDao.delLikeCnt(id);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QuestionEntity questionEntity) {
        questionEntity.setGmtModified(new Date());
        questionService.updateById(questionEntity);
        return R.ok();
    }

    @RequestMapping("/addQuestionLikeCnt")
    public R addQuestionLikeCnt(@RequestParam Long id){
        questionDao.addLikeCnt(id);
        QuestionEntity questionEntity = questionDao.selectById(id);
        return R.ok().put("question", questionEntity);
    }

    @RequestMapping("/subQuestionLikeCnt")
    public R subQuestionLikeCnt(@RequestParam Long id){
        questionDao.subLikeCnt(id);
        QuestionEntity questionEntity = questionDao.selectById(id);
        return R.ok().put("question", questionEntity);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        questionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
