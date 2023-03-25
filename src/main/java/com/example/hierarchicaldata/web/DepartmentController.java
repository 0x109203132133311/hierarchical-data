package com.example.hierarchicaldata.web;

import com.example.hierarchicaldata.LrItem;
import com.example.hierarchicaldata.entity.Department;
import com.example.hierarchicaldata.service.DepartmentService;
import com.example.hierarchicaldata.vo.AddDepartmentVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangsm
 * @date 2023/03/24 09:42
 **/

@RestController
@RequestMapping("department")
public class DepartmentController {
    private DepartmentService departmentService;

    @PostMapping
    public Integer add(@RequestBody AddDepartmentVo departmentVo) {
        return departmentService.add(departmentVo.getName(), departmentVo.getParentId());
    }

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @DeleteMapping("{id}")
    Integer del(@PathVariable Long id) {
        return departmentService.del(id);
    }

    @GetMapping("descendant/{id}")
    List<Department> getDescendants(@PathVariable Long id,
                                    Integer levelLimit,
                                    Boolean includeSelf
    ) {
        return departmentService.getDescendant(id, levelLimit,includeSelf);
    }

    @GetMapping("descendant/tree/{id}")
    List<LrItem> getDescendantsForTree(@PathVariable Long id,
                                       Integer levelLimit,
                                       Boolean includeSelf
    ) {
        return departmentService.getDescendantForTree(id, levelLimit,includeSelf);
    }

    @GetMapping("ancestor/{id}")
    List<Department> getAncestor(@PathVariable Long id,
                                 Integer levelLimit,
                                 Boolean includeSelf
                                 ) {
        return departmentService.getAncestor(id, levelLimit,includeSelf);
    }


}
