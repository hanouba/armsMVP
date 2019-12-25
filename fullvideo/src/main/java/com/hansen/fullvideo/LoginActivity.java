package com.hansen.fullvideo;

import android.Manifest;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.TemplateBean;
import com.hansen.fullvideo.dao.DBHelper;
import com.hansen.fullvideo.utils.Utils;

import java.util.List;
import java.util.TreeMap;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity {
    private DBHelper mDBHelper;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        constraintLayout = findViewById(R.id.constra);
        constraintLayout.setBackgroundResource(R.mipmap.splash);
        Utils.init(this);
        mDBHelper = DBHelper.getInstance(LoginActivity.this);
        checkPermiss();


    }

    @AfterPermissionGranted(1)
    private void checkPermiss() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {


            List<BigScreenBean> bigScreenBeans = mDBHelper.searchAll();
            if (bigScreenBeans.size() != 0) {
            }else {
                createLocalData();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            openGreen();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    1, perms);
        }
    }

    /**
     * 创建预案数据
     */
    private void createLocalData() {
        BigScreenBean bigScreenBean1 = new BigScreenBean(0,0,1,2,"cctv-1",0,"预案1");
        BigScreenBean bigScreenBean2 = new BigScreenBean(0,1,1,2,"cctv-1",0,"预案1");

        BigScreenBean bigScreenBean3 = new BigScreenBean(1,0,3,4,"cctv-2",1,"预案2");
        BigScreenBean bigScreenBean4 = new BigScreenBean(1,1,3,4,"cctv-2",1,"预案2");

        BigScreenBean bigScreenBean5 = new BigScreenBean(2,0,5,6,"cctv-3",2,"预案3");
        BigScreenBean bigScreenBean6 = new BigScreenBean(2,1,5,6,"cctv-3",2,"预案3");

        BigScreenBean bigScreenBean7 = new BigScreenBean(3,6,1,4,"cctv-4",2,"预案3");
        BigScreenBean bigScreenBean8 = new BigScreenBean(3,7,1,4,"cctv-4",2,"预案3");
        BigScreenBean bigScreenBean9 = new BigScreenBean(3,8,1,4,"cctv-4",2,"预案3");
        BigScreenBean bigScreenBean10 = new BigScreenBean(3,9,1,4,"cctv-4",2,"预案3");

        BigScreenBean bigScreenBean11 = new BigScreenBean(4,2,1,2,"cctv-5",3,"预案4");
        BigScreenBean bigScreenBean12 = new BigScreenBean(4,3,1,2,"cctv-5",3,"预案4");

        BigScreenBean bigScreenBean13 = new BigScreenBean(5,2,5,6,"cctv-6",3,"预案4");
        BigScreenBean bigScreenBean14 = new BigScreenBean(5,3,5,6,"cctv-6",3,"预案4");


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

        for (int i = 1; i <= 100; i++) {
            TemplateBean templateBean = new TemplateBean("预案"+i,i-1);
            mDBHelper.insertOrReplace(templateBean);
        }



    }

    public void openGreen() {
        Intent intent = new Intent(this,GreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
