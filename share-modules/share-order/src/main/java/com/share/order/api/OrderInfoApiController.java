package com.share.order.api;

import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.security.annotation.InnerAuth;
import com.share.common.security.annotation.RequiresLogin;
import com.share.common.security.utils.SecurityUtils;
import com.share.order.domain.OrderInfo;
import com.share.order.service.IOrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单接口管理")
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoApiController extends BaseController
{
    @Autowired
    private IOrderInfoService orderInfoService;

    @Operation(summary = "获取未完成订单")
    @RequiresLogin
    @GetMapping("getNoFinishOrder")
    public AjaxResult getNoFinishOrder() {
        return success(orderInfoService.getNoFinishOrder(SecurityUtils.getUserId()));
    }

    @Operation(summary = "获取未完成订单")
    @InnerAuth
    @GetMapping("getNoFinishOrder/{userId}")
    public R<OrderInfo> getNoFinishOrder(@PathVariable Long userId) {
        return R.ok(orderInfoService.getNoFinishOrder(userId));
    }

    @Operation(summary = "获取订单详细信息")
    @RequiresLogin
    @GetMapping(value = "/getOrderInfo/{id}")
    public AjaxResult getOrderInfo(@PathVariable("id") Long id)
    {
        return success(orderInfoService.selectOrderInfoById(id));
    }
}
