package com.berghachi.nimblewaystest.di.modules

import com.berghachi.nimblewaystest.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}