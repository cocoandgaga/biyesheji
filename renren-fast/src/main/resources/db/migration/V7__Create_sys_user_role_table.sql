-- 用户与角色对应关系
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint COMMENT '用户ID',
                                 `role_id` bigint COMMENT '角色ID',
                                 PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='用户与角色对应关系';
