package com.hansen.fullvideo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author HanN on 2019/12/19 17:02
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/19 17:02
 * @updateremark:
 * @version: 2.1.67
 */
@Entity
public class SingleViewLocation {
    @Id(autoincrement = true)//设置自增长
    private Long id;
    private float left;
    private float right;
    private float top;
    private float bottom;
    @Index(unique = true)//设置唯一性
    private String name;
    @Generated(hash = 97697264)
    public SingleViewLocation(Long id, float left, float right, float top,
            float bottom, String name) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.name = name;
    }
    @Generated(hash = 2045563476)
    public SingleViewLocation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public float getLeft() {
        return this.left;
    }
    public void setLeft(float left) {
        this.left = left;
    }
    public float getRight() {
        return this.right;
    }
    public void setRight(float right) {
        this.right = right;
    }
    public float getTop() {
        return this.top;
    }
    public void setTop(float top) {
        this.top = top;
    }
    public float getBottom() {
        return this.bottom;
    }
    public void setBottom(float bottom) {
        this.bottom = bottom;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
