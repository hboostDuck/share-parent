package com.share.order.api.factory;


import com.share.common.core.domain.R;
import com.share.order.api.RemoteUserService;
import com.share.order.api.domain.UpdateUserLogin;
import com.share.order.api.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RemoteUserFallbackFactory implements RemoteUserService {
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);


    @Override
    public R<UserInfo> wxLogin(String code, String source) {
        return R.fail("微信登录失败");
    }

    @Override
    public R<Boolean> updateUserLogin(UpdateUserLogin updateUserLogin, String source) {
        return R.fail("微信登录失败");
    }

    @Override
    public R<UserInfo> getUserInfo(Long id, String source) {
        return R.fail("获取用户信息失败");
    }

    @Override
    public R<Map<String, Object>> getUserCount(String source) {
        return R.fail("获取用户信息失败");
    }
}
