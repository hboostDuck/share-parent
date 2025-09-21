package com.share.order.controller;

import java.util.List;
import java.util.Arrays;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.share.order.domain.OrderStatistics;
import com.share.order.service.IOrderStatisticsService;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.share.common.core.web.page.TableDataInfo;

/**
 * 订单统计Controller
 *
 * @author duck
 * @date 2025-09-21
 */
@Tag(name = "订单统计接口管理")
@RestController
@RequestMapping("/orderStatistics")
public class OrderStatisticsController extends BaseController
{
    @Autowired
    private IOrderStatisticsService orderStatisticsService;

    /**
     * 查询订单统计列表
     */
    @Operation(summary = "查询订单统计列表")
//    @RequiresPermissions("user:orderStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderStatistics orderStatistics)
    {
        startPage();
        List<OrderStatistics> list = orderStatisticsService.selectOrderStatisticsList(orderStatistics);
        return getDataTable(list);
    }

    /**
     * 导出订单统计列表
     */
    @Operation(summary = "导出订单统计列表")
//    @RequiresPermissions("user:orderStatistics:export")
//    @Log(title = "订单统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderStatistics orderStatistics)
    {
        List<OrderStatistics> list = orderStatisticsService.selectOrderStatisticsList(orderStatistics);
        ExcelUtil<OrderStatistics> util = new ExcelUtil<OrderStatistics>(OrderStatistics.class);
        util.exportExcel(response, list, "订单统计数据");
    }

    /**
     * 获取订单统计详细信息
     */
    @Operation(summary = "获取订单统计详细信息")
//    @RequiresPermissions("user:orderStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderStatisticsService.getById(id));
    }

    /**
     * 新增订单统计
     */
    @Operation(summary = "新增订单统计")
//    @RequiresPermissions("user:orderStatistics:add")
//    @Log(title = "订单统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderStatistics orderStatistics)
    {
        return toAjax(orderStatisticsService.save(orderStatistics));
    }

    /**
     * 修改订单统计
     */
    @Operation(summary = "修改订单统计")
//    @RequiresPermissions("user:orderStatistics:edit")
//    @Log(title = "订单统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderStatistics orderStatistics)
    {
        return toAjax(orderStatisticsService.updateById(orderStatistics));
    }

    /**
     * 删除订单统计
     */
    @Operation(summary = "删除订单统计")
//    @RequiresPermissions("user:orderStatistics:remove")
//    @Log(title = "订单统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderStatisticsService.removeBatchByIds(Arrays.asList(ids)));
    }
}
