package com.hansen.fullvideo;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.dao.DBHelper;
import com.hansen.fullvideo.utils.Utils;

import java.util.List;

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
            List<BigScreenBean> bigScreenBeans = mDBHelper.searchAll();
            if (bigScreenBeans.size() != 0) {

            }else {
                createLocalData();
            }



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
        BigScreenBean bigScreenBean1 = new BigScreenBean(0,0,1,2,"cctv-1",1);
        BigScreenBean bigScreenBean2 = new BigScreenBean(0,1,1,2,"cctv-1",1);

        BigScreenBean bigScreenBean3 = new BigScreenBean(1,0,3,4,"cctv-2",1);
        BigScreenBean bigScreenBean4 = new BigScreenBean(1,1,3,4,"cctv-2",1);

        BigScreenBean bigScreenBean5 = new BigScreenBean(2,0,5,6,"cctv-3",2);
        BigScreenBean bigScreenBean6 = new BigScreenBean(2,1,5,6,"cctv-3",2);

        BigScreenBean bigScreenBean7 = new BigScreenBean(3,6,1,4,"cctv-4",2);
        BigScreenBean bigScreenBean8 = new BigScreenBean(3,7,1,4,"cctv-4",2);
        BigScreenBean bigScreenBean9 = new BigScreenBean(3,8,1,4,"cctv-4",2);
        BigScreenBean bigScreenBean10 = new BigScreenBean(3,9,1,4,"cctv-4",2);

        BigScreenBean bigScreenBean11 = new BigScreenBean(4,2,1,2,"cctv-5",3);
        BigScreenBean bigScreenBean12 = new BigScreenBean(4,3,1,2,"cctv-5",3);

        BigScreenBean bigScreenBean13 = new BigScreenBean(5,2,5,6,"cctv-6",3);
        BigScreenBean bigScreenBean14 = new BigScreenBean(5,3,5,6,"cctv-6",3);


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
        mDBHelper.insertOrReplace(bigScreenBean11);
        mDBHelper.insertOrReplace(bigScreenBean12);
        mDBHelper.insertOrReplace(bigScreenBean13);
        mDBHelper.insertOrReplace(bigScreenBean14);



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
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
