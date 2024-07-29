package com.channelblab.springrain.controller;

import com.channelblab.springrain.model.Department;
import com.channelblab.springrain.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Tag(name = "框架-部门接口")
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "部门树")
    @GetMapping("/tree")
    public List<Department> tree() {
        return departmentService.tree();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/delete/{departmentId}")
    public void delete(@PathVariable @NotBlank String departmentId) {
        departmentService.delete(departmentId);
    }


}
