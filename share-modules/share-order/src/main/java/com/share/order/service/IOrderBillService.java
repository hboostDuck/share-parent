package com.share.order.service;

import java.util.List;
import com.share.order.domain.OrderBill;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单账单Service接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface IOrderBillService extends IService<OrderBill>
{

    /**
     * 查询订单账单列表
     *
     * @param orderBill 订单账单
     * @return 订单账单集合
     */
    public List<OrderBill> selectOrderBillList(OrderBill orderBill);

}
