package com.berghachi.nimblewaystest.di.component

import com.berghachi.nimblewaystest.base.BaseApplication
import com.berghachi.nimblewaystest.di.modules.ActivityBuildersModule
import com.berghachi.nimblewaystest.di.modules.DataModule
import com.berghachi.nimblewaystest.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class
        , ActivityBuildersModule::class
        , ViewModelModule::class
        , DataModule::class
        ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(baseApplication: BaseApplication): Builder

        fun build(): AppComponent
    }

}