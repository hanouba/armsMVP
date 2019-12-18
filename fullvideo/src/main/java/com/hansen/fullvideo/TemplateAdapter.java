package com.hansen.fullvideo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hansen.fullvideo.bean.TemplateBean;

import java.util.List;

/**
 * @author HanN on 2019/12/18 15:13
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/18 15:13
 * @updateremark:
 * @version: 2.1.67
 */
public class TemplateAdapter extends ArrayAdapter<TemplateBean> {
    private int resourceId;

    public TemplateAdapter(Context context, int textViewResourceId, List<TemplateBean> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TemplateBean templateBean = getItem(position); //获取当前项的Fruit实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView tempImage = (ImageView) view.findViewById(R.id.temp_image);
        TextView tempName =(TextView) view.findViewById(R.id.temp_name);
        tempImage.setImageResource(templateBean.getImageId());
        tempName.setText(templateBean.getTextName());
        return view;
    }
}
