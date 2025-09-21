package com.share.order.service;

import java.util.List;
import com.share.order.domain.OrderLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单操作日志记录Service接口
 *
 * @author duck
 * @date 2025-09-21
 */
public interface IOrderLogService extends IService<OrderLog>
{

    /**
     * 查询订单操作日志记录列表
     *
     * @param orderLog 订单操作日志记录
     * @return 订单操作日志记录集合
     */
    public List<OrderLog> selectOrderLogList(OrderLog orderLog);

}
