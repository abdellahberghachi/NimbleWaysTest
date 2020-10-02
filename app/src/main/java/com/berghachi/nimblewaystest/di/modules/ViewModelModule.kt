package com.berghachi.nimblewaystest.di.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berghachi.nimblewaystest.di.viewmodel.FactoryViewModel
import com.berghachi.nimblewaystest.di.viewmodel.ViewModelKey
import com.berghachi.nimblewaystest.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
     abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
     abstract fun providesMainViewModel(mainViewModel: MainViewModel): ViewModel

}