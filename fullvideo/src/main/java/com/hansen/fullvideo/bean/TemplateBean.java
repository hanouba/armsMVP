package com.hansen.fullvideo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author HanN on 2019/12/18 15:14
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/18 15:14
 * @updateremark:
 * @version: 2.1.67
 */
@Entity
public class TemplateBean {
    @Id(autoincrement = true)//设置自增长
    private Long id;
    private  String tempName;
    private int tempType;//预案编号

    public TemplateBean(String tempName, int tempType) {
        this.tempName = tempName;
        this.tempType = tempType;
    }

    @Generated(hash = 153456139)
    public TemplateBean(Long id, String tempName, int tempType) {
        this.id = id;
        this.tempName = tempName;
        this.tempType = tempType;
    }

    @Generated(hash = 741639705)
    public TemplateBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTempName() {
        return this.tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public int getTempType() {
        return this.tempType;
    }

    public void setTempType(int tempType) {
        this.tempType = tempType;
    }


}
