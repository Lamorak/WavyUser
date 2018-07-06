package cz.lamorak.wavyuser

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (BindingModule::class), (RetrofitModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}