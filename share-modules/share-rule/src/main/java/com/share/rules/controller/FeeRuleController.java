package com.share.rules.controller;

import com.alibaba.fastjson2.JSON;
import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.common.security.annotation.InnerAuth;

import com.share.rule.api.domain.FeeRule;
import com.share.rule.api.domain.FeeRuleRequestForm;
import com.share.rule.api.domain.FeeRuleResponseVo;
import com.share.rules.service.IFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Tag(name = "费用规则接口管理")
@RestController
@RequestMapping("/feeRule")
@SuppressWarnings({"unchecked", "rawtypes"})
public class FeeRuleController extends BaseController
{
    @Autowired
    private IFeeRuleService feeRuleService;


    @Operation(summary = "批量获取费用规则信息")
    @InnerAuth
    @PostMapping(value = "/getFeeRuleList")
    public R<List<FeeRule>> getFeeRuleList(@RequestBody List<Long> feeRuleIdList)
    {
        return R.ok(feeRuleService.listByIds(feeRuleIdList));
    }

    @Operation(summary = "获取费用规则详细信息")
    @InnerAuth
    @GetMapping(value = "/getFeeRule/{id}")
    public R<FeeRule> getFeeRule(@PathVariable("id") Long id)
    {
        return R.ok(feeRuleService.getById(id));
    }

    /**
     * 查询费用规则列表
     */
    @Operation(summary = "查询费用规则列表")
    @GetMapping("/list")
    public TableDataInfo list(FeeRule feeRule)
    {
        startPage();
        List<FeeRule> list = feeRuleService.selectFeeRuleList(feeRule);
        return getDataTable(list);
    }

    /**
     * 获取费用规则详细信息
     */
    @Operation(summary = "获取费用规则详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(feeRuleService.getById(id));
    }

    /**
     * 新增费用规则
     */
    @Operation(summary = "新增费用规则")
    @PostMapping
    public AjaxResult add(@RequestBody FeeRule feeRule)
    {
        return toAjax(feeRuleService.save(feeRule));
    }

    /**
     * 修改费用规则
     */
    @Operation(summary = "修改费用规则")
    @PutMapping
    public AjaxResult edit(@RequestBody FeeRule feeRule)
    {
        //return toAjax(feeRuleService.updateById(feeRule));
        System.out.println(JSON.toJSONString(feeRule));
        return toAjax(1);
    }

    /**
     * 删除费用规则
     */
    @Operation(summary = "删除费用规则")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(feeRuleService.removeBatchByIds(Arrays.asList(ids)));
    }

    @Operation(summary = "获取全部费用规则")
    @GetMapping("/getALLFeeRuleList")
    public AjaxResult getALLFeeRuleList()
    {
        return success(feeRuleService.getALLFeeRuleList());
    }

    @Operation(summary = "计算订单费用")
    @InnerAuth
    @PostMapping("/calculateOrderFee")
    public R<FeeRuleResponseVo> calculateOrderFee(@RequestBody FeeRuleRequestForm calculateOrderFeeForm) {
        return R.ok(feeRuleService.calculateOrderFee(calculateOrderFeeForm));
    }

}
