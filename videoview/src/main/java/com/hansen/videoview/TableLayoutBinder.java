package com.hansen.videoview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * @author HanN on 2019/12/16 9:44
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/12/16 9:44
 * @updateremark:
 * @version: 2.1.67
 */
public class TableLayoutBinder {
    private Context mContext;
    public ViewGroup mTableView;
    public TableLayout mTableLayout;

    /**
     * 上下文 布局固定
     *
     * @param context
     */
    public TableLayoutBinder(Context context) {
        this(context, R.layout.table_media_info);
    }

    /**
     * 上下文和布局
     * 使用当前布局的tableLayout
     *
     * @param context
     * @param layoutResourceId
     */
    public TableLayoutBinder(Context context, int layoutResourceId) {
        mContext = context;
        mTableView = (ViewGroup) LayoutInflater.from(mContext).inflate(layoutResourceId, null);
        mTableLayout = (TableLayout) mTableView.findViewById(R.id.table);
    }

    /**
     * 上下文和tablayout  这个是在使用其他布局里面的tableLayout
     *
     * @param context
     * @param tableLayout
     */
    public TableLayoutBinder(Context context, TableLayout tableLayout) {
        mContext = context;
        mTableView = tableLayout;
        mTableLayout = tableLayout;
    }

    /**
     * 添加第一种类型的view 一行
     * @param name
     * @param value
     * @return
     */
    public View appendRow1(String name, String value) {
        return appendRow(R.layout.table_media_info_row1,name,value);

    }

    /**
     * 在name 用的地址值的情况下 R.string elix
     *
     * @param nameId
     * @param value
     * @return
     */
    public View appendRow1(int nameId, String value) {
        return appendRow1(mContext.getString(nameId),value);

    }

    public View appendRow2(String name, String value) {
        return appendRow(R.layout.table_media_info_row2,name,value);

    }
    public View appendRow2(int nameId, String value) {
        return appendRow2(mContext.getString(nameId),value);

    }
    public View appendSection(String name) {
        return appendRow(R.layout.table_media_info_section, name, null);
    }

    public View appendSection(int nameId) {
        return appendSection(mContext.getString(nameId));
    }



    /**
     * 添加一行
     * @param layoutId
     * @param name
     * @param value
     * @return
     */
    public View appendRow(int layoutId, String name, String value) {
        ViewGroup rowtView = (ViewGroup) LayoutInflater.from(mContext).inflate(layoutId, mTableLayout, false);
        setNameValueText(rowtView, name, value);

        mTableLayout.addView(rowtView);
        return rowtView;

    }

    private void setNameValueText(ViewGroup rowtView, String name, String value) {
        ViewHolder viewHolder = obtainViewHolder(rowtView);
        viewHolder.setName(name);
        viewHolder.setValue(value);
    }

    public void setValueText(View rowView, String value) {
        ViewHolder viewHolder = obtainViewHolder(rowView);
        viewHolder.setValue(value);
    }

    /**
     * 获取viewholder
     * @param rowView
     * @return
     */
    public ViewHolder obtainViewHolder(View rowView) {
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.mNameTextView = (TextView) rowView.findViewById(R.id.name);
            viewHolder.mValueTextView = (TextView) rowView.findViewById(R.id.value);
            rowView.setTag(viewHolder);
        }
        return viewHolder;


    }

    private static class ViewHolder {
        public TextView mNameTextView;
        public TextView mValueTextView;

        public void setName(String name) {
            if (mNameTextView != null) {
                mNameTextView.setText(name);
            }
        }

        public void setValue(String value) {
            if (mValueTextView != null) {
                mValueTextView.setText(value);
            }
        }
    }

    public AlertDialog.Builder buildAlertDialogBuilder() {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(mContext);
        dlgBuilder.setView(buildLayout());
        return dlgBuilder;
    }
    public ViewGroup buildLayout() {
        return mTableView;
    }
}
