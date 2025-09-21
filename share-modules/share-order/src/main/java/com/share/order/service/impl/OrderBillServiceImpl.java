package com.share.order.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.OrderBillMapper;
import com.share.order.domain.OrderBill;
import com.share.order.service.IOrderBillService;

/**
 * 订单账单Service业务层处理
 *
 * @author duck
 * @date 2025-09-21
 */
@Service
public class OrderBillServiceImpl extends ServiceImpl<OrderBillMapper, OrderBill> implements IOrderBillService
{
    @Autowired
    private OrderBillMapper orderBillMapper;

    /**
     * 查询订单账单列表
     *
     * @param orderBill 订单账单
     * @return 订单账单
     */
    @Override
    public List<OrderBill> selectOrderBillList(OrderBill orderBill)
    {
        return orderBillMapper.selectOrderBillList(orderBill);
    }

}
