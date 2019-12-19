package com.hansen.fullvideo;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.CourseInfo;
import com.hansen.fullvideo.bean.TemplateBean;
import com.hansen.fullvideo.dao.DBHelper;
import com.hansen.fullvideo.thread.ThreadPollProxy;
import com.hansen.fullvideo.thread.ThreadPoolManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity{


    private DBHelper mDBHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
        mDBHelper = DBHelper.getInstance(MainActivity.this);
        setContentView(R.layout.activity_main);
        checkPermiss();


    }
    @AfterPermissionGranted(1)
    private void checkPermiss() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            Toast.makeText(this, "获取了权限", Toast.LENGTH_SHORT).show();

            createLocalData();


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    1, perms);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 创建预案数据
     */
    private void createLocalData() {
        BigScreenBean bigScreenBean1 = new BigScreenBean(0,0,1,2,001,"cctv-1","");
        BigScreenBean bigScreenBean2 = new BigScreenBean(0,1,1,2,002,"cctv-1","");

        BigScreenBean bigScreenBean3 = new BigScreenBean(1,0,3,4,003,"cctv-2","");
        BigScreenBean bigScreenBean4 = new BigScreenBean(1,1,3,4,004,"cctv-2","");

        BigScreenBean bigScreenBean5 = new BigScreenBean(2,0,5,6,005,"cctv-3","");
        BigScreenBean bigScreenBean6 = new BigScreenBean(2,1,5,6,006,"cctv-3","");

        BigScreenBean bigScreenBean7 = new BigScreenBean(3,6,1,4,007,"cctv-4","");
        BigScreenBean bigScreenBean8 = new BigScreenBean(3,7,1,4,108,"cctv-4","");
        BigScreenBean bigScreenBean9 = new BigScreenBean(3,8,1,4,109,"cctv-4","");
        BigScreenBean bigScreenBean10 = new BigScreenBean(3,9,1,4,100,"cctv-4","");


        mDBHelper.insertOrReplace(bigScreenBean1);
        mDBHelper.insertOrReplace(bigScreenBean2);
        mDBHelper.insertOrReplace(bigScreenBean3);
        mDBHelper.insertOrReplace(bigScreenBean4);
        mDBHelper.insertOrReplace(bigScreenBean5);
        mDBHelper.insertOrReplace(bigScreenBean6);
        mDBHelper.insertOrReplace(bigScreenBean7);
        mDBHelper.insertOrReplace(bigScreenBean8);
        mDBHelper.insertOrReplace(bigScreenBean9);
        mDBHelper.insertOrReplace(bigScreenBean10);



    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void openGreen(View view) {
        Intent intent = new Intent(this,GreenActivity.class);
        startActivity(intent);
    }

    public void openOld(View view) {
        Intent intent = new Intent(this,OldActivity.class);
        startActivity(intent);
    }
}
