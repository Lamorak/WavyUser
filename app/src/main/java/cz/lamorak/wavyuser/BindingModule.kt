package cz.lamorak.wavyuser

import cz.lamorak.wavyuser.user.UserModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ContributesAndroidInjector(modules = [(UserModule::class)])
    abstract fun bindMainActivity(): MainActivity
}