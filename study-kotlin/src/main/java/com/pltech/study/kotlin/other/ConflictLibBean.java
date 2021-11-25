package com.pltech.study.kotlin.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 有冲突的依赖库bean文件
 * 存储依赖库名称、冲突库所在的依赖路径
 */
public class ConflictLibBean {
    //依赖库描述，举例：androidx.annotation:annotation
    private String libDesc;
    //有冲突的库到项目根目录的依赖路径，整个项目中该依赖库的所有冲突位置，故用List
    private List<String> depPaths = new ArrayList<>();
    //本依赖库的目标版本，即整个项目期望使用的最新版本号
    private String targetVer;

    public String getLibDesc() {
        return libDesc;
    }

    public void setLibDesc(String libDesc) {
        this.libDesc = libDesc;
    }

    public List<String> getDepPaths() {
        return depPaths;
    }

    public void setDepPaths(List<String> depPaths) {
        this.depPaths = depPaths;
    }

    public String getTargetVer() {
        return targetVer;
    }

    public void setTargetVer(String targetVer) {
        this.targetVer = targetVer;
    }
}
