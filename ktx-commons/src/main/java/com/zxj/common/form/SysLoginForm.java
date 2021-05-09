/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.zxj.common.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录表单
 */
@Data
public class SysLoginForm implements Serializable {
    private String username;
    private String password;
    private String captcha;
    private String uuid;

}
