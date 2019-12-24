package com.hansen.fullvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


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
public class TemplateAdapter extends ArrayAdapter<String>  {

    private int resourceId;
    private int selected = -1;
    private int firstCreate = 0;

    public TemplateAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String temName = getItem(position); //获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView tempImage = (ImageView) view.findViewById(R.id.temp_image);
        TextView tempName = (TextView) view.findViewById(R.id.temp_name);
        tempName.setText(temName);


                if (selected == position) {
                    tempImage.setImageResource(R.mipmap.icon_temp_selected);  //选中项背景
                    tempName.setTextColor(getContext().getResources().getColor(R.color.color_388BFD));
                } else {

                    tempImage.setImageResource(R.mipmap.icon_temp_unselected);  //其它项背景
                    tempName.setTextColor(getContext().getResources().getColor(R.color.color_969696));


                }

        return view;
    }

    public void setSelected(int selectPostions) {
        if (selected != selectPostions) {
            selected = selectPostions;
            notifyDataSetChanged();
        }
    }


}
