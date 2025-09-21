package com.share.order.service;

import java.util.List;
import com.share.order.domain.OrderStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单统计Service接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface IOrderStatisticsService extends IService<OrderStatistics>
{

    /**
     * 查询订单统计列表
     *
     * @param orderStatistics 订单统计
     * @return 订单统计集合
     */
    public List<OrderStatistics> selectOrderStatisticsList(OrderStatistics orderStatistics);

}
