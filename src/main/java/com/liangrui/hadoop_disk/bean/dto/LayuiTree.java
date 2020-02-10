package com.liangrui.hadoop_disk.bean.dto;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class LayuiTree {
    @JSONField(ordinal = 1)
    private String name;
    @JSONField(ordinal = 2)
    private String id;
    @JSONField(ordinal = 3)
    private String spread;
    @JSONField(ordinal = 4)
    private List<LayuiTree> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LayuiTree{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", spread='" + spread + '\'' +
                ", children=" + children +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public List<LayuiTree> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiTree> children) {
        this.children = children;
    }
}
