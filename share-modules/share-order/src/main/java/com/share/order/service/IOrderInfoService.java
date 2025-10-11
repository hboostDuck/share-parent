package com.share.order.service;

import java.util.List;

import com.share.order.domain.EndOrderVo;
import com.share.order.domain.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share.order.domain.SubmitOrderVo;

/**
 * 订单Service接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface IOrderInfoService extends IService<OrderInfo>
{

    /**
     * 查询订单列表
     *
     * @param orderInfo 订单
     * @return 订单集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    OrderInfo getNoFinishOrder(Long userId);

    OrderInfo selectOrderInfoById(Long id);

    Long saveOrder(SubmitOrderVo orderForm);

    void endOrder(EndOrderVo endOrderVo);

    List<OrderInfo> selectUserOrderInfoList(Long userId);

    OrderInfo getByOrderNo(String orderNo);


    void processPaySucess(String orderNo);
}
