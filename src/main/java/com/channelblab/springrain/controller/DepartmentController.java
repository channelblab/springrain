package com.channelblab.springrain.controller;

import com.channelblab.springrain.model.Department;
import com.channelblab.springrain.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Validated
@Tag(name = "框架-部门接口")
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @Operation(summary = "新增或修改部门")
    @PostMapping("/addOrUpdate")
    public void addOrUpdate(@RequestBody Department department) {
        departmentService.addOrUpdate(department);
    }

    @Operation(summary = "部门树")
    @GetMapping("/tree")
    public List<Department> tree() {
        return departmentService.tree();
    }

    @Operation(summary = "部门树名称搜索")
    @GetMapping("/search")
    public List<Department> search(@RequestParam() @NotBlank String key) {
        return departmentService.search(key);
    }

    @Operation(summary = "部门人员树")
    @GetMapping("/deptUserTree")
    public List<Department> deptUserTree() {
        return departmentService.deptUserTree();
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/delete/{departmentId}")
    public void delete(@PathVariable @NotBlank String departmentId) {
        departmentService.delete(departmentId);
    }


}
