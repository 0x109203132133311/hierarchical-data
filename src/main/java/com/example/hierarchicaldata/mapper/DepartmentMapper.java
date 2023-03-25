package com.example.hierarchicaldata.mapper;

import com.example.hierarchicaldata.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int updateBatch(List<Department> list);

    int updateBatchSelective(List<Department> list);

    int updateAddBackData(@Param("lft") Integer id);

    int updateDelBackData(@Param("lft") Integer lft);

    Integer getLastInsertLft(@Param("id") Long id);

    List<Department> selectDescendant(@Param("id") Long id,
                                      @Param("levelLimit") Integer levelLimit,
                                      @Param("includeSelf") Boolean includeSelf
    );

    List<Department> getAncestor(@Param("id") Long id,
                                 @Param("levelLimit") Integer levelLimit,
                                 @Param("includeSelf") Boolean includeSelf
    );
}