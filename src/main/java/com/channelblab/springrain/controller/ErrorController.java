package com.channelblab.springrain.controller;

import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping
    public void error() {
        throw new BusinessException(Response.COMMON_ERROR_CODE, "common_error");
    }


}
