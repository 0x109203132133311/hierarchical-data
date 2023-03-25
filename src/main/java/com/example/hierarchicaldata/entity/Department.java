package com.example.hierarchicaldata.entity;

/**
 * department
 */
public class Department {
    /**
     * id
     */
    private Long id;

    private Long parentId;

    /**
     * name
     */
    private String name;

    /**
     * lft
     */
    private Integer lft;

    /**
     * rgt
     */
    private Integer rgt;

    /**
     * level
     */
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}