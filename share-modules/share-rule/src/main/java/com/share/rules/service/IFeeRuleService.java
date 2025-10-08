package com.share.rules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.rule.api.domain.FeeRule;
import com.share.rule.api.domain.FeeRuleRequestForm;
import com.share.rule.api.domain.FeeRuleResponseVo;


import java.util.List;

public interface IFeeRuleService extends IService<FeeRule> {


    public List<FeeRule> selectFeeRuleList(FeeRule feeRule);

    List<FeeRule> getALLFeeRuleList();

    FeeRuleResponseVo calculateOrderFee(FeeRuleRequestForm calculateOrderFeeForm);

}
