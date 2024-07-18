package com.channelblab.springrain.controller;

import com.channelblab.springrain.common.anotations.NoResponseHandle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-18 13:11
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/multilingual")
public class MultilingualController {


    @NoResponseHandle
    @GetMapping("/export")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        response.getWriter().write("");
    }
}
