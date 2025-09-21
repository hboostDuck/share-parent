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
import com.share.order.domain.OrderBill;
import com.share.order.service.IOrderBillService;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.utils.poi.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.share.common.core.web.page.TableDataInfo;

/**
 * 订单账单Controller
 *
 * @author duck
 * @date 2025-09-21
 */
@Tag(name = "订单账单接口管理")
@RestController
@RequestMapping("/orderBill")
public class OrderBillController extends BaseController
{
    @Autowired
    private IOrderBillService orderBillService;

    /**
     * 查询订单账单列表
     */
    @Operation(summary = "查询订单账单列表")
//    @RequiresPermissions("user:orderBill:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderBill orderBill)
    {
        startPage();
        List<OrderBill> list = orderBillService.selectOrderBillList(orderBill);
        return getDataTable(list);
    }

    /**
     * 导出订单账单列表
     */
    @Operation(summary = "导出订单账单列表")
//    @RequiresPermissions("user:orderBill:export")
//    @Log(title = "订单账单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderBill orderBill)
    {
        List<OrderBill> list = orderBillService.selectOrderBillList(orderBill);
        ExcelUtil<OrderBill> util = new ExcelUtil<OrderBill>(OrderBill.class);
        util.exportExcel(response, list, "订单账单数据");
    }

    /**
     * 获取订单账单详细信息
     */
    @Operation(summary = "获取订单账单详细信息")
//    @RequiresPermissions("user:orderBill:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderBillService.getById(id));
    }

    /**
     * 新增订单账单
     */
    @Operation(summary = "新增订单账单")
//    @RequiresPermissions("user:orderBill:add")
//    @Log(title = "订单账单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderBill orderBill)
    {
        return toAjax(orderBillService.save(orderBill));
    }

    /**
     * 修改订单账单
     */
    @Operation(summary = "修改订单账单")
//    @RequiresPermissions("user:orderBill:edit")
//    @Log(title = "订单账单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderBill orderBill)
    {
        return toAjax(orderBillService.updateById(orderBill));
    }

    /**
     * 删除订单账单
     */
    @Operation(summary = "删除订单账单")
//    @RequiresPermissions("user:orderBill:remove")
//    @Log(title = "订单账单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderBillService.removeBatchByIds(Arrays.asList(ids)));
    }
}
