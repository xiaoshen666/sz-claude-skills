package com.supcon.nebule.aiproctsap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 蓝卓app健康检测接口
 */
@RestController
public class AiproctsapHealthController {

    @GetMapping("/aiproctsap/health")
    public String health() {
        return "success";
    }
}