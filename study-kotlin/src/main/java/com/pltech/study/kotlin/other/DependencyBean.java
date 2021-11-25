package com.pltech.study.kotlin.other;

import java.util.List;

/**
 * 单个依赖库在依赖树中的Bean对象描述
 */
public class DependencyBean {
    //如: androidx.appcompat:appcompat:1.0.0 -> 1.3.0
    private String libGroup;    //当前库所在的组名称：androidx.appcompat
    private String libName;     //当前库名：appcompat
    private String libDesc;     //当前库的完整描述：androidx.appcompat:appcompat:1.0.0
    private String currVersion;    //当前使用的版本：1.0.0
    private String targetVersion;  //当前库的目标版本：1.3.0
    private int aarDepth; //当前库在依赖树中的深度

    private List<String> referenceList;

    public int getAarDepth() {
        return aarDepth;
    }

    public void setAarDepth(int aarDepth) {
        this.aarDepth = aarDepth;
    }

    public String getLibGroup() {
        return libGroup;
    }

    public void setLibGroup(String libGroup) {
        this.libGroup = libGroup;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getLibDesc() {
        return libDesc;
    }

    public void setLibDesc(String libDesc) {
        this.libDesc = libDesc;
    }

    public String getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(String currVersion) {
        this.currVersion = currVersion;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public List<String> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<String> referenceList) {
        this.referenceList = referenceList;
    }

    @Override
    public String toString() {
        return "{" + libDesc + ", aarDepth=" + aarDepth + "}";
    }
}
