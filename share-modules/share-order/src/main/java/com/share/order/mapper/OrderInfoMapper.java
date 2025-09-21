package com.share.order.mapper;

import java.util.List;
import com.share.order.domain.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 订单Mapper接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo>
{

    /**
     * 查询订单列表
     *
     * @param orderInfo 订单
     * @return 订单集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

}
