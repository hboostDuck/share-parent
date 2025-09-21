package com.share.order.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.UserLoginLogMapper;
import com.share.order.domain.UserLoginLog;
import com.share.order.service.IUserLoginLogService;

/**
 * 用户登录记录Service业务层处理
 *
 * @author atguigu
 * @date 2025-09-07
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements IUserLoginLogService
{
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    /**
     * 查询用户登录记录列表
     *
     * @param userLoginLog 用户登录记录
     * @return 用户登录记录
     */
    @Override
    public List<UserLoginLog> selectUserLoginLogList(UserLoginLog userLoginLog)
    {
        return userLoginLogMapper.selectUserLoginLogList(userLoginLog);
    }

}
