package com.share.order.service;

import java.util.List;
import com.share.order.domain.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
