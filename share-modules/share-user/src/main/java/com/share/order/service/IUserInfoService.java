package com.share.order.service;

import java.util.List;
import java.util.Map;

import com.share.order.api.domain.UpdateUserLogin;
import com.share.order.api.domain.UserCountVo;
import com.share.order.api.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户Service接口
 *
 * @author atguigu
 * @date 2025-09-07
 */
public interface IUserInfoService extends IService<UserInfo>
{

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    UserInfo wxLogin(String code);

    Boolean updateUserLogin(UpdateUserLogin updateUserLogin);

    Boolean isFreeDeposit();

    public Map<String, Object> getUserCount();
}
