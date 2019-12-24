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
import com.hansen.socket.SocketActivity;

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


    @Override
    protected void onStop() {
        super.onStop();

    }

    public void openGreen(View view) {
        Intent intent = new Intent(this,GreenActivity.class);
        startActivity(intent);
    }

    public void openOld(View view) {
        Intent intent = new Intent(this, SocketActivity.class);
        startActivity(intent);
    }
}
