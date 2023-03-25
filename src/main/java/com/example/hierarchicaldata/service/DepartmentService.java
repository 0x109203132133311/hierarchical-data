package com.example.hierarchicaldata.service;

import com.example.hierarchicaldata.LrItem;
import com.example.hierarchicaldata.entity.Department;

import java.util.List;

public interface DepartmentService {


    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int add(String name, Long parentId);
    int del(Long id);

    int updateBatch(List<Department> list);

    int updateBatchSelective(List<Department> list);

    /**
     * 获取子孙
     * @param id
     * @return
     */
    List<Department> getDescendant(Long id,Integer levelLimit, Boolean includeSelf);
    /**
     * 获取祖先
     * @param id
     * @return
     */
    List<Department> getAncestor(Long id,Integer levelLimit, Boolean includeSelf);

    List<LrItem> getDescendantForTree(Long id, Integer levelLimit, Boolean includeSelf);

}

