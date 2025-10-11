package com.share.order.api;

import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.constant.ServiceNameConstants;
import com.share.common.core.domain.R;
import com.share.order.domain.OrderInfo;
import com.share.order.factory.RemoteOrderInfoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteOrderInfoService", value = ServiceNameConstants.ORDER_SERVICE, fallbackFactory = RemoteOrderInfoFallbackFactory.class)
public interface RemoteOrderInfoService {

    @GetMapping("/orderInfo/getNoFinishOrder/{userId}")
    public R<OrderInfo> getNoFinishOrder(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/orderInfo/getByOrderNo/{orderNo}")
    public R<OrderInfo> getByOrderNo(@PathVariable("orderNo") String orderNo, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
