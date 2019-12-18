package com.hansen.fullvideo.bean;

import java.io.Serializable;

// BaseInfo用于记录整体信息
public class BaseInfo implements Serializable{

    private static final long serialVersionUID = 2074656067805712769L;

	private int type;   //相同的数据类型
    protected int day;			// 星期几上课 第几列
    protected int lessonfrom;	// 开始行数
    protected int lessonto;	// 结束行数
    protected String place;	//地点

	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLessonfrom() {
		return lessonfrom;
	}
	public void setLessonfrom(int lessonfrom) {
		this.lessonfrom = lessonfrom;
	}
	public int getLessonto() {
		return lessonto;
	}
	public void setLessonto(int lessonto) {
		this.lessonto = lessonto;
	}
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }


    //仅测试使用
    public BaseInfo() {
    }

    public BaseInfo( int weektype, int day, int lessonfrom, int lessonto,
                    String place){

        this.day = day;
        this.lessonfrom = lessonfrom;
        this.lessonto = lessonto;
        this.place=place;
    }

}