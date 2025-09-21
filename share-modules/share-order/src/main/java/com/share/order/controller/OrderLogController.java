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
import com.share.order.domain.OrderLog;
import com.share.order.service.IOrderLogService;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.share.common.core.web.page.TableDataInfo;

/**
 * 订单操作日志记录Controller
 *
 * @author duck
 * @date 2025-09-21
 */
@Tag(name = "订单操作日志记录接口管理")
@RestController
@RequestMapping("/orderLog")
public class OrderLogController extends BaseController
{
    @Autowired
    private IOrderLogService orderLogService;

    /**
     * 查询订单操作日志记录列表
     */
    @Operation(summary = "查询订单操作日志记录列表")
//    @RequiresPermissions("user:orderLog:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderLog orderLog)
    {
        startPage();
        List<OrderLog> list = orderLogService.selectOrderLogList(orderLog);
        return getDataTable(list);
    }

    /**
     * 导出订单操作日志记录列表
     */
    @Operation(summary = "导出订单操作日志记录列表")
//    @RequiresPermissions("user:orderLog:export")
//    @Log(title = "订单操作日志记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderLog orderLog)
    {
        List<OrderLog> list = orderLogService.selectOrderLogList(orderLog);
        ExcelUtil<OrderLog> util = new ExcelUtil<OrderLog>(OrderLog.class);
        util.exportExcel(response, list, "订单操作日志记录数据");
    }

    /**
     * 获取订单操作日志记录详细信息
     */
    @Operation(summary = "获取订单操作日志记录详细信息")
//    @RequiresPermissions("user:orderLog:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderLogService.getById(id));
    }

    /**
     * 新增订单操作日志记录
     */
    @Operation(summary = "新增订单操作日志记录")
//    @RequiresPermissions("user:orderLog:add")
//    @Log(title = "订单操作日志记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderLog orderLog)
    {
        return toAjax(orderLogService.save(orderLog));
    }

    /**
     * 修改订单操作日志记录
     */
    @Operation(summary = "修改订单操作日志记录")
//    @RequiresPermissions("user:orderLog:edit")
//    @Log(title = "订单操作日志记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderLog orderLog)
    {
        return toAjax(orderLogService.updateById(orderLog));
    }

    /**
     * 删除订单操作日志记录
     */
    @Operation(summary = "删除订单操作日志记录")
//    @RequiresPermissions("user:orderLog:remove")
//    @Log(title = "订单操作日志记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderLogService.removeBatchByIds(Arrays.asList(ids)));
    }
}
