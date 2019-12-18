package com.hansen.fullvideo.bean;

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
public class TemplateBean {
    private  String textName;
    private  int  imageId;

    public TemplateBean(String textName, int imageId) {
        this.textName = textName;
        this.imageId = imageId;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
