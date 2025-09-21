package com.share.order.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.OrderLogMapper;
import com.share.order.domain.OrderLog;
import com.share.order.service.IOrderLogService;

/**
 * 订单操作日志记录Service业务层处理
 *
 * @author duck
 * @date 2025-09-21
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLog> implements IOrderLogService
{
    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 查询订单操作日志记录列表
     *
     * @param orderLog 订单操作日志记录
     * @return 订单操作日志记录
     */
    @Override
    public List<OrderLog> selectOrderLogList(OrderLog orderLog)
    {
        return orderLogMapper.selectOrderLogList(orderLog);
    }

}
