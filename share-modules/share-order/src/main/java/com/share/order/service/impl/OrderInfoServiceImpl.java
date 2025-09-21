package com.share.order.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.OrderInfoMapper;
import com.share.order.domain.OrderInfo;
import com.share.order.service.IOrderInfoService;

/**
 * 订单Service业务层处理
 *
 * @author duck
 * @date 2025-09-21
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService
{
    @Autowired
    private OrderInfoMapper orderInfoMapper;



    /**
     * 查询订单列表
     *
     * @param orderInfo 订单
     * @return 订单
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo)
    {
        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    @Override
    public OrderInfo getNoFinishOrder(Long userId) {
        // 查询用户是否有使用中与未支付订单
        return orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getUserId, userId)
                .in(OrderInfo::getStatus, Arrays.asList("0", "1"))// 订单状态：0:充电中 1：未支付 2：已支付
                .orderByDesc(OrderInfo::getId)
                .last("limit 1")
        );
    }

    @Override
    public OrderInfo selectOrderInfoById(Long id) {
        return baseMapper.selectById(id);
    }

}
