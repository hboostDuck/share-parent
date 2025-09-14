package com.share.user.api.factory;


import com.share.common.core.domain.R;
import com.share.user.api.RemoteUserService;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.api.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

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
}
