package com.share.order.mapper;

import java.util.List;
import com.share.order.domain.OrderStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 订单统计Mapper接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics>
{

    /**
     * 查询订单统计列表
     *
     * @param orderStatistics 订单统计
     * @return 订单统计集合
     */
    public List<OrderStatistics> selectOrderStatisticsList(OrderStatistics orderStatistics);

}
