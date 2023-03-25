package com.example.hierarchicaldata.service.impl;

import com.example.hierarchicaldata.LrItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hierarchicaldata.entity.Department;
import com.example.hierarchicaldata.mapper.DepartmentMapper;
import com.example.hierarchicaldata.service.DepartmentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Department record) {
        return departmentMapper.insert(record);
    }

    @Override
    public int insertSelective(Department record) {
        return departmentMapper.insertSelective(record);
    }

    @Override
    public Department selectByPrimaryKey(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {
        return departmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(String name, Long parentId) {


        Integer nodeLevel;
        Integer nodeLft;
        Integer nodeRgt;
        if (parentId == 0) {
            nodeLft = 1;
            nodeRgt = nodeLft + 1;
            nodeLevel = 1;
        } else {

            Department department = departmentMapper.selectByPrimaryKey(parentId);
            if (department == null) {
                return 0;
            }
            Integer lastLft = departmentMapper.getLastInsertLft(parentId);
            if (lastLft == null) {
                nodeLft = department.getLft() + 1;
                nodeRgt = nodeLft + 1;
            } else {
                nodeLft = lastLft + 2;
                nodeRgt = nodeLft + 1;
            }
            departmentMapper.updateAddBackData(nodeLft);
            nodeLevel = department.getLevel() + 1;
        }


        Department newDepartment = new Department();
        newDepartment.setParentId(parentId);
        newDepartment.setLft(nodeLft);
        newDepartment.setRgt(nodeRgt);
        newDepartment.setName(name);
        newDepartment.setLevel(nodeLevel);
        int result = insertSelective(newDepartment);
        if (result == 0) {
            throw new RuntimeException("");
        }
        return result;

    }

    @Override
    public int updateBatch(List<Department> list) {
        return departmentMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<Department> list) {
        return departmentMapper.updateBatchSelective(list);
    }

    @Override
    public int del(Long id) {
        Department department = selectByPrimaryKey(id);
        if (department != null) {
            departmentMapper.updateDelBackData(department.getLft());
            return deleteByPrimaryKey(id);
        }
        return 0;
    }

    @Override
    public List<Department> getDescendant(Long id, Integer levelLimit, Boolean includeSelf) {
        return departmentMapper.selectDescendant(id, levelLimit,includeSelf);
    }

    @Override
    public List<Department> getAncestor(Long id, Integer levelLimit, Boolean includeSelf) {
        return departmentMapper.getAncestor(id, levelLimit,includeSelf);
    }

    @Override
    public List<LrItem> getDescendantForTree(Long id, Integer levelLimit, Boolean includeSelf) {
       List<LrItem> items= getDescendant(id, levelLimit,includeSelf)
                .stream()
                .map(d -> new LrItem(d.getId(), d.getName(), d.getLft(), d.getRgt())
                ).collect(Collectors.toList());
       LrItem.init(items);
       return LrItem.recursive(items);
    }
}

