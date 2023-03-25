package com.example.hierarchicaldata;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huangsm
 * @date 2023/03/25 09:50
 **/

public class LrItem {
    @NonNull
    private Long id;
    @NonNull
    private String depName;
    @NonNull
    private Integer left;
    @NonNull
    private Integer right;
    private Integer level;
    private Long parentId;
    /**
     * 是否是叶子
     */
    private Boolean isLeaf;
    private List<LrItem> childItem;

    public LrItem(@NonNull Long id, @NonNull String depName, @NonNull Integer left, @NonNull Integer right) {
        this.id = id;
        this.depName = depName;
        this.left = left;
        this.right = right;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public List<LrItem> getChildItem() {
        return childItem;
    }

    public void setChildItem(List<LrItem> childItem) {
        this.childItem = childItem;
    }

    public static void init(List<LrItem> deps) {
        // 如果数据库排序过了之后 这里就不用排序了
        deps.sort(Comparator.comparing(LrItem::getLeft));
        // 为计算层级 缓存节点右侧的值
        List<Integer> rights = new ArrayList<>();
        Map<Integer, Long> mp = new HashMap<>();
        // 初始化节点的层级，叶子节点 以及 父节点ID 对应的数据
        deps.forEach(item -> {
            if (rights.size() > 0) {
                    // 一旦发现本次节点右侧的值比前一次的大，说明出现层级上移了 需要移除前一个底层及的值
                    // 这里大部分情况下只存在移除前面一个值情况
                while (rights.size() > 0 && rights.get(rights.size() - 1) < item.getRight()) {
                    rights.remove(rights.size() - 1);//从rights末尾去除
                }
            }
            Integer _level = rights.size() + 1;
            item.setLevel(_level);
            mp.put(_level, item.getId());
            item.setParentId(mp.containsKey(_level - 1) ? mp.get(_level - 1) : 0); //计算出上级部门编号
            item.setLeaf(item.getLeft() == item.getRight() - 1);//判断是否叶子部门
            rights.add(item.getRight());
        });
        System.out.println(rights);
        System.out.println(mp);
    }


    /**
     * @param deps 所有数据
     */
    public static List<LrItem> recursive(List<LrItem> deps) {
        init(deps);
        //获取父节点
        List<LrItem> collect = deps.stream()
                .filter(m -> m.getParentId() == 0)
                .map(m ->
                        {
                            m.setChildItem(getChildrens(m, deps));
                            return m;
                        }
                ).collect(Collectors.toList());
        return collect;

    }

    /**
     * 递归查询子节点
     *
     * @param root 根节点
     * @param all  所有节点
     * @return 根节点信息
     */
    private static List<LrItem> getChildrens(LrItem root, List<LrItem> all) {
        List<LrItem> children = all.stream()
                .filter(m -> Objects.equals(m.getParentId(), root.getId()))
                .map(m -> {
                            m.setChildItem(getChildrens(m, all));
                            return m;
                        }
                ).collect(Collectors.toList());
        return children;
    }
}
