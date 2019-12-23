package com.hansen.fullvideo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author HanN on 2019/12/19 11:42
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description: 大屏信息
 * @updateuser:
 * @updatedata: 2019/12/19 11:42
 * @updateremark:
 * @version: 2.1.67
 */
@Entity
public class BigScreenBean {
    @Id(autoincrement = true)//设置自增长
    private Long id;
    private int type;   //相同的数据类型
    protected int column;			// 星期几上课 第几列  从0 --9
    protected int lessonfrom;	// 开始行数  1--6
    protected int lessonto;	// 结束行数
    private String videoname; //视频名称
    private int tempType;//预案编号
 	// id


    public BigScreenBean(int type, int column, int lessonfrom, int lessonto, String videoname, int tempType) {
        this.type = type;
        this.column = column;
        this.lessonfrom = lessonfrom;
        this.lessonto = lessonto;
        this.videoname = videoname;
        this.tempType = tempType;
    }


    @Generated(hash = 1779582477)
    public BigScreenBean(Long id, int type, int column, int lessonfrom, int lessonto, String videoname,
            int tempType) {
        this.id = id;
        this.type = type;
        this.column = column;
        this.lessonfrom = lessonfrom;
        this.lessonto = lessonto;
        this.videoname = videoname;
        this.tempType = tempType;
    }


    @Generated(hash = 275093853)
    public BigScreenBean() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public int getType() {
        return this.type;
    }


    public void setType(int type) {
        this.type = type;
    }


    public int getColumn() {
        return this.column;
    }


    public void setColumn(int column) {
        this.column = column;
    }


    public int getLessonfrom() {
        return this.lessonfrom;
    }


    public void setLessonfrom(int lessonfrom) {
        this.lessonfrom = lessonfrom;
    }


    public int getLessonto() {
        return this.lessonto;
    }


    public void setLessonto(int lessonto) {
        this.lessonto = lessonto;
    }


    public String getVideoname() {
        return this.videoname;
    }


    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }


    public int getTempType() {
        return this.tempType;
    }


    public void setTempType(int tempType) {
        this.tempType = tempType;
    }

 
}
