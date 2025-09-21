package com.share.order.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.OrderStatisticsMapper;
import com.share.order.domain.OrderStatistics;
import com.share.order.service.IOrderStatisticsService;

/**
 * 订单统计Service业务层处理
 *
 * @author duck
 * @date 2025-09-21
 */
@Service
public class OrderStatisticsServiceImpl extends ServiceImpl<OrderStatisticsMapper, OrderStatistics> implements IOrderStatisticsService
{
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    /**
     * 查询订单统计列表
     *
     * @param orderStatistics 订单统计
     * @return 订单统计
     */
    @Override
    public List<OrderStatistics> selectOrderStatisticsList(OrderStatistics orderStatistics)
    {
        return orderStatisticsMapper.selectOrderStatisticsList(orderStatistics);
    }

}
