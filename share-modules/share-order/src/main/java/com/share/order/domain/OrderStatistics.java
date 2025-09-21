package com.share.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.share.common.core.annotation.Excel;

/**
 * 订单统计对象 order_statistics
 *
 * @author duck
 * @date 2025-09-21
 */
@Data
@Schema(description = "订单统计")
public class OrderStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 站点id */
    @Excel(name = "站点id")
    @Schema(description = "站点id")
    private Long stationId;

    /** 订单统计日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单统计日期", width = 30, dateFormat = "yyyy-MM-dd")
    @Schema(description = "订单统计日期")
    private Date orderDate;

    /** 总金额 */
    @Excel(name = "总金额")
    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    /** 订单总数 */
    @Excel(name = "订单总数")
    @Schema(description = "订单总数")
    private Long totalNum;

    /** 删除标记（0:不可用 1:可用） */
    @Excel(name = "删除标记", readConverterExp = "0=:不可用,1=:可用")
    @Schema(description = "删除标记")
    private Long isDeleted;

}
