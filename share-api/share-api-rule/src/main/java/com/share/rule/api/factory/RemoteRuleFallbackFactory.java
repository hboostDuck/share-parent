package com.share.rule.api.factory;

import cn.hutool.system.UserInfo;
import com.share.common.core.domain.R;
import com.share.rule.api.RemoteRuleService;
import com.share.rule.api.domain.FeeRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoteRuleFallbackFactory implements RemoteRuleService {
    private static final Logger log = LoggerFactory.getLogger(RemoteRuleFallbackFactory.class);


    @Override
    public R<List<FeeRule>> getFeeRuleList(List<Long> feeRuleIdList, String source) {
        return null;
    }

    @Override
    public R<FeeRule> getFeeRule(Long id, String source) {
        return null;
    }
}
