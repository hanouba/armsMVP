package com.ivsom.ops.di.component;

import com.ivsom.ops.di.module.HomeMoudle;
import com.ivsom.ops.mvp.contract.HomeContract;
import com.ivsom.ops.mvp.ui.fragment.HomeFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author HanN on 2019/11/11 17:27
 * @email: 1356548475@qq.com
 * @project armmvp
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/11 17:27
 * @updateremark:
 * @version: 2.1.67
 */
@ActivityScope
@Component(modules = HomeMoudle.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);
        HomeComponent.Builder appComponent(AppComponent appComponent);
        HomeComponent build();
    }
}
