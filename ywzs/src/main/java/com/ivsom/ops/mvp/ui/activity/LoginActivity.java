package com.ivsom.ops.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.ivsom.ops.R;
import com.ivsom.ops.app.AppCounts;
import com.ivsom.ops.di.component.DaggerLoginComponent;
import com.ivsom.ops.mvp.contract.LoginContract;
import com.ivsom.ops.mvp.model.entity.LoginBean;
import com.ivsom.ops.mvp.presenter.LoginPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.rl_login_title)
    RelativeLayout rlLoginTitle;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_pass_word)
    EditText etPassWord;
    @BindView(R.id.bt_login)
    AppCompatButton btLogin;
    @BindView(R.id.tv_forgete_password)
    TextView tvForgetePassword;
    @BindView(R.id.tv_forget_project_name)
    TextView tvForgetProjectName;
    @BindView(R.id.tv_new_create_project)
    TextView tvNewCreateProject;
    @BindView(R.id.ll_login_info)
    LinearLayout llLoginInfo;
    @BindView(R.id.tv_terms)
    TextView tvTerms;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void logingReuslt(LoginBean loginBean) {
        String sid = loginBean.getSid();
        String uid = loginBean.getUid();
        SPUtils.getInstance().put(AppCounts.SESSION_ID,sid);
        SPUtils.getInstance().put(AppCounts.USER_ID,uid);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return null;
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }



    @OnClick({R.id.bt_login, R.id.tv_forgete_password, R.id.tv_forget_project_name, R.id.tv_new_create_project, R.id.tv_terms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                 mPresenter.getPorject("whtv","whtv","123456");
                break;
            case R.id.tv_forgete_password:
                break;
            case R.id.tv_forget_project_name:
                break;
            case R.id.tv_new_create_project:
                break;
            case R.id.tv_terms:
                break;
                default:
        }
    }
}
