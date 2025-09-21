package com.share.order.service;

import java.util.List;
import com.share.order.domain.UserLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户登录记录Service接口
 *
 * @author atguigu
 * @date 2025-09-07
 */
public interface IUserLoginLogService extends IService<UserLoginLog>
{

    /**
     * 查询用户登录记录列表
     *
     * @param userLoginLog 用户登录记录
     * @return 用户登录记录集合
     */
    public List<UserLoginLog> selectUserLoginLogList(UserLoginLog userLoginLog);

}
