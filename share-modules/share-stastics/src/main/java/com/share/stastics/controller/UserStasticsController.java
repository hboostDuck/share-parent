package com.share.stastics.controller;

import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.order.api.RemoteUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "数据统计")
@RestController
@RequestMapping("/sta")
public class UserStasticsController extends BaseController {

    @Autowired
    private RemoteUserService userService;

    //统计2024年每月注册人数
    @GetMapping("/userCount")
    public AjaxResult userCount() {
        R<Map<String, Object>> result = userService.getUserCount(SecurityConstants.INNER);
        Map<String, Object> map = result.getData();
        return success(map);
    }

}
