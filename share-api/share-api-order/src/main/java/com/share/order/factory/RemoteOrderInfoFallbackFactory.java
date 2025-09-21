package com.share.order.factory;

import com.share.common.core.domain.R;
import com.share.order.api.RemoteOrderInfoService;
import com.share.order.domain.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteOrderInfoFallbackFactory implements FallbackFactory<RemoteOrderInfoService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteOrderInfoFallbackFactory.class);

    @Override
    public RemoteOrderInfoService create(Throwable throwable)
    {
        log.error("订单服务调用失败:{}", throwable.getMessage());
        return (userId, source) -> R.fail("获取用户未完成订单失败:" + throwable.getMessage());
    }
}
