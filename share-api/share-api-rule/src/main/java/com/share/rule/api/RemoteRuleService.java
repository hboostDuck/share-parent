package com.share.rule.api;



import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.constant.ServiceNameConstants;
import com.share.common.core.domain.R;
import com.share.rule.api.domain.FeeRule;
import com.share.rule.api.factory.RemoteRuleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteRuleService", value = ServiceNameConstants.RULE_SERVICE, fallbackFactory = RemoteRuleFallbackFactory.class)
public interface RemoteRuleService {


    @PostMapping(value = "/feeRule/getFeeRuleList")
    public R<List<FeeRule>> getFeeRuleList(@RequestBody List<Long> feeRuleIdList, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/feeRule/getFeeRule/{id}")
    public R<FeeRule> getFeeRule(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
