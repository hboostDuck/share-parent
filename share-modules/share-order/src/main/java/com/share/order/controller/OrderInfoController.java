package com.share.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.share.order.service.IOrderInfoService;
import com.share.common.core.web.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 订单Controller
 *
 * @author duck
 * @date 2025-09-21
 */
@Tag(name = "订单接口管理")
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController
{
    @Autowired
    private IOrderInfoService orderInfoService;


}
