package com.zxj.finalexam.service;


import com.zxj.finalexam.entity.FreeColumnItem;
import com.zxj.finalexam.vo.BoardVo;
import com.zxj.finalexam.vo.TaskCard;
import com.zxj.finalexam.vo.UserStoryCard;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
public interface FreeColumnItemService {

    /**
     * 用户故事卡片移动
     * @param card 卡片信息
     * @param appId 租户信息
     * @param userId 操作者信息
     * @return 移动结果，null为正常
     */

    /**
     * 看板卡片查询
     * @param boardVo 查询参数
     * @return 看板卡片集合
     */
    List<UserStoryCard> queryBoardData(BoardVo boardVo);

    /**
     * 任务卡片移动
     * @param card 卡片信息
     * @return 移动结果，null为正常
     */
    String moveStuTaskCard(FreeColumnItem card);


}
