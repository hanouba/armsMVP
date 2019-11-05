package me.jessyan.mvparms.demo.mvp.model.entity;

/**
 * Create by HanN on 2019/11/1
 * 注释: 项目检查的特殊
 */
public class ProjectBean {
    /**
     * projectIp  : 192.168.3.145
     * projectPort  : 8550
     * isMatch  : 1
     */

    private String projectIp;
    private int projectPort;
    private int isMatch;

    public String getProjectIp() {
        return projectIp;
    }

    public void setProjectIp(String projectIp) {
        this.projectIp = projectIp;
    }

    public int getProjectPort() {
        return projectPort;
    }

    public void setProjectPort(int projectPort) {
        this.projectPort = projectPort;
    }

    public int getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(int isMatch) {
        this.isMatch = isMatch;
    }
}
