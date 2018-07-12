package cz.lamorak.wavyuser

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.lamorak.architecture.ViewActivity
import cz.lamorak.architecture.ViewModel
import cz.lamorak.architecture.ViewModelFactory
import cz.lamorak.wavyuser.user.viewmodel.UserViewAction
import cz.lamorak.wavyuser.user.viewmodel.UserViewAction.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewIntent
import cz.lamorak.wavyuser.user.viewmodel.UserViewIntent.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewModel
import cz.lamorak.wavyuser.user.viewmodel.UserViewState
import cz.lamorak.wavyuser.user.viewmodel.UserViewState.*
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : ViewActivity<UserViewIntent, UserViewState, UserViewAction>() {

    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<ViewModel<UserViewIntent, UserViewState, UserViewAction>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    override fun defaultIntent(): UserViewIntent? = LoadUser

    override fun subscribeIntents() = CompositeDisposable(
    )

    override fun display(state: UserViewState) {
    }

    override fun handle(action: UserViewAction) = when (action) {
    }
}
