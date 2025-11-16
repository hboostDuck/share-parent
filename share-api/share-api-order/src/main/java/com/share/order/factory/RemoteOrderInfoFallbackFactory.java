package com.share.order.factory;

import com.share.common.core.domain.R;
import com.share.order.api.RemoteOrderInfoService;
import com.share.order.domain.OrderInfo;
import com.share.order.domain.OrderSqlVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteOrderInfoFallbackFactory implements RemoteOrderInfoService
{
    private static final Logger log = LoggerFactory.getLogger(RemoteOrderInfoFallbackFactory.class);


    @Override
    public R<OrderInfo> getNoFinishOrder(Long userId, String source) {
        return null;
    }

    @Override
    public R<OrderInfo> getByOrderNo(String orderNo, String source) {
        return null;
    }

    @Override
    public R getOrderCount(OrderSqlVo orderSqlVo, String source) {
        return null;
    }
}
