package com.share.order.service.impl;

import java.math.BigDecimal;
import java.util.*;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.domain.R;
import com.share.common.core.exception.ServiceException;
import com.share.common.core.utils.StringUtils;
import com.share.common.core.utils.bean.BeanUtils;
import com.share.common.security.utils.SecurityUtils;
import com.share.order.api.RemoteUserService;
import com.share.order.api.domain.UserInfo;
import com.share.order.domain.*;
import com.share.order.mapper.OrderBillMapper;
import com.share.rule.api.RemoteRuleService;
import com.share.rule.api.domain.FeeRule;
import com.share.rule.api.domain.FeeRuleRequestForm;
import com.share.rule.api.domain.FeeRuleResponseVo;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.order.mapper.OrderInfoMapper;
import com.share.order.service.IOrderInfoService;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RemoteRuleService remoteFeeRuleService;

    @Autowired
    private RemoteUserService remoteUserInfoService;

    @Autowired
    private OrderBillMapper orderBillMapper;

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
        OrderInfo orderInfo = orderInfoMapper.selectById(id);

        //充电中实时计算使用时间与金额
        if ("0".equals(orderInfo.getStatus())) {
            //充电中实时计算使用时间
            int duration = Minutes.minutesBetween(new DateTime(orderInfo.getStartTime()), new DateTime()).getMinutes();
            if (duration > 0) {
                orderInfo.setDuration((long) duration);

                // 费用计算
                FeeRuleRequestForm feeRuleRequestForm = new FeeRuleRequestForm();
                feeRuleRequestForm.setDuration(duration);
                feeRuleRequestForm.setFeeRuleId(orderInfo.getFeeRuleId());
                R<FeeRuleResponseVo> feeRuleResponseVoResult = remoteFeeRuleService.calculateOrderFee(feeRuleRequestForm, SecurityConstants.INNER);
                if (R.FAIL == feeRuleResponseVoResult.getCode()) {
                    throw new ServiceException(feeRuleResponseVoResult.getMsg());
                }
                FeeRuleResponseVo feeRuleResponseVo = feeRuleResponseVoResult.getData();

                // 设置订单金额
                orderInfo.setTotalAmount(feeRuleResponseVo.getTotalAmount());
                orderInfo.setDeductAmount(new BigDecimal(0));
                orderInfo.setRealAmount(feeRuleResponseVo.getTotalAmount());
            } else {
                orderInfo.setDuration(0L);
                orderInfo.setTotalAmount(new BigDecimal(0));
                orderInfo.setDeductAmount(new BigDecimal(0));
                orderInfo.setRealAmount(new BigDecimal(0));
            }
        }

        List<OrderBill> orderBillList = orderBillMapper.selectList(new LambdaQueryWrapper<OrderBill>().eq(OrderBill::getOrderId, id));
        orderInfo.setOrderBillList(orderBillList);

        R<UserInfo> userInfoResult = remoteUserInfoService.getUserInfo(orderInfo.getUserId(), SecurityConstants.INNER);
        if (StringUtils.isNull(userInfoResult) || StringUtils.isNull(userInfoResult.getData())) {
            throw new ServiceException("获取用户信息失败");
        }
        if (R.FAIL == userInfoResult.getCode()) {
            throw new ServiceException(userInfoResult.getMsg());
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfoResult.getData(), userInfoVo);
        orderInfo.setUserInfoVo(userInfoVo);
        return orderInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveOrder(SubmitOrderVo orderForm) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(orderForm.getUserId());
        orderInfo.setOrderNo(RandomUtil.randomString(8));
        orderInfo.setPowerBankNo(orderForm.getPowerBankNo());
        orderInfo.setStartTime(new Date());
        orderInfo.setStartStationId(orderForm.getStartStationId());
        orderInfo.setStartStationName(orderForm.getStartStationName());
        orderInfo.setStartCabinetNo(orderForm.getStartCabinetNo());
        // 费用规则
        FeeRule feeRule = remoteFeeRuleService.getFeeRule(orderForm.getFeeRuleId(), SecurityConstants.INNER).getData();
        orderInfo.setFeeRuleId(orderForm.getFeeRuleId());
        orderInfo.setFeeRule(feeRule.getDescription());
        orderInfo.setStatus("0");
        orderInfo.setCreateTime(new Date());
        orderInfo.setCreateBy(SecurityUtils.getUsername());
        //用户昵称
        UserInfo userInfo = remoteUserInfoService.getUserInfo(orderInfo.getUserId(), SecurityConstants.INNER).getData();
        orderInfo.setNickname(userInfo.getNickname());

        orderInfoMapper.insert(orderInfo);
        return orderInfo.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void endOrder(EndOrderVo endOrderVo) {
        // 获取充电中的订单，如果存在，则结束订单； 如果不存在，则返回（初始化插入，无订单）
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getPowerBankNo, endOrderVo.getPowerBankNo())
                .eq(OrderInfo::getStatus, "0") //订单状态：0:充电中
                .orderByDesc(OrderInfo::getCreateTime)
                .last("limit 1")
        );
        if (orderInfo == null) {
            return;
        }

        orderInfo.setEndTime(endOrderVo.getEndTime());
        orderInfo.setEndStationId(endOrderVo.getEndStationId());
        orderInfo.setEndStationName(endOrderVo.getEndStationName());
        orderInfo.setEndCabinetNo(endOrderVo.getEndCabinetNo());
        int duration = Minutes.minutesBetween(new DateTime(orderInfo.getStartTime()), new DateTime(orderInfo.getEndTime())).getMinutes();
        duration = Math.max(duration, 1);
        orderInfo.setDuration((long) duration);

        // 费用计算
        FeeRuleRequestForm feeRuleRequestForm = new FeeRuleRequestForm();
        feeRuleRequestForm.setDuration(duration);
        feeRuleRequestForm.setFeeRuleId(orderInfo.getFeeRuleId());
        R<FeeRuleResponseVo> feeRuleResponseVoResult = remoteFeeRuleService.calculateOrderFee(feeRuleRequestForm, SecurityConstants.INNER);
        if (R.FAIL == feeRuleResponseVoResult.getCode()) {
            throw new ServiceException(feeRuleResponseVoResult.getMsg());
        }
        FeeRuleResponseVo feeRuleResponseVo = feeRuleResponseVoResult.getData();

        // 设置订单金额
        orderInfo.setTotalAmount(feeRuleResponseVo.getTotalAmount());
        orderInfo.setDeductAmount(new BigDecimal(0));
        orderInfo.setRealAmount(feeRuleResponseVo.getTotalAmount());
        if(orderInfo.getRealAmount().subtract(new BigDecimal(0)).doubleValue() == 0) {
            orderInfo.setStatus("2");
        } else {
            orderInfo.setStatus("1");
        }
        orderInfoMapper.updateById(orderInfo);

        // 插入免费订单账单
        OrderBill freeOrderBill = new OrderBill();
        freeOrderBill.setOrderId(orderInfo.getId());
        freeOrderBill.setBillItem(feeRuleResponseVo.getFreeDescription());
        freeOrderBill.setBillAmount(new BigDecimal(0));
        orderBillMapper.insert(freeOrderBill);

        // 插入超出免费订单账单
        if (feeRuleResponseVo.getExceedPrice().doubleValue() > 0) {
            OrderBill exceedOrderBill = new OrderBill();
            exceedOrderBill.setOrderId(orderInfo.getId());
            exceedOrderBill.setBillItem(feeRuleResponseVo.getExceedDescription());
            exceedOrderBill.setBillAmount(feeRuleResponseVo.getExceedPrice());
            orderBillMapper.insert(exceedOrderBill);
        }
    }

    @Override
    public List<OrderInfo> selectUserOrderInfoList(Long userId) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getUserId, userId)
                .orderByDesc(OrderInfo::getId)
        );
        if (!CollectionUtils.isEmpty(orderInfoList)) {
            for (OrderInfo orderInfo : orderInfoList) {
                //充电中实时计算使用时间与金额
                if ("0".equals(orderInfo.getStatus())) {
                    //充电中实时计算使用时间
                    int duration = Minutes.minutesBetween(new DateTime(orderInfo.getStartTime()), new DateTime()).getMinutes();
                    if (duration > 0) {
                        orderInfo.setDuration((long) duration);

                        // 费用计算
                        FeeRuleRequestForm feeRuleRequestForm = new FeeRuleRequestForm();
                        feeRuleRequestForm.setDuration(duration);
                        feeRuleRequestForm.setFeeRuleId(orderInfo.getFeeRuleId());
                        R<FeeRuleResponseVo> feeRuleResponseVoResult = remoteFeeRuleService.calculateOrderFee(feeRuleRequestForm, SecurityConstants.INNER);
                        if (R.FAIL == feeRuleResponseVoResult.getCode()) {
                            throw new ServiceException(feeRuleResponseVoResult.getMsg());
                        }
                        FeeRuleResponseVo feeRuleResponseVo = feeRuleResponseVoResult.getData();

                        // 设置订单金额
                        orderInfo.setTotalAmount(feeRuleResponseVo.getTotalAmount());
                        orderInfo.setDeductAmount(new BigDecimal(0));
                        orderInfo.setRealAmount(feeRuleResponseVo.getTotalAmount());
                    } else {
                        orderInfo.setDuration(0L);
                        orderInfo.setTotalAmount(new BigDecimal(0));
                        orderInfo.setDeductAmount(new BigDecimal(0));
                        orderInfo.setRealAmount(new BigDecimal(0));
                    }

                }
            }
        }
        return orderInfoList;
    }

    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        return orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, orderNo));
    }

    @Override
    public void processPaySucess(String orderNo) {
        //获取订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOrderNo, orderNo).select(OrderInfo::getId, OrderInfo::getStatus));
        //未支付
        if ("1".equals(orderInfo.getStatus())) {
            orderInfo.setStatus("2");
            orderInfo.setPayTime(new Date());
            orderInfoMapper.updateById(orderInfo);
        }
    }

    //传递过来sql语句，根据sql语句查询数据库得到报表数据
    @Override
    public Map<String, Object> getOrderCount(String sql) {
        //调用mapper方法执行sql语句
        List<Map<String,Object>> list = baseMapper.getOrderCount(sql);

        Map<String, Object> dataMap = new HashMap<>();

        List<Object> dateList = new ArrayList<>();
        List<Object> countList = new ArrayList<>();
        //把list集合遍历，得到每个map
        for(Map<String,Object> map : list) {
            //把每个map里面日期得到放到新的list集合里面
            dateList.add(map.get("order_date"));
            //把每个map里面数量得到放到新的list集合里面
            countList.add(map.get("order_count"));
        }
        //把两个list集合放到dataMap中，返回
        dataMap.put("dateList",dateList);
        dataMap.put("countList",countList);
        return dataMap;
    }

}
