package cz.lamorak.wavyuser

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import com.jakewharton.rxbinding2.view.clicks
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
import cz.lamorak.wavyuser.util.loadUri
import cz.lamorak.wavyuser.util.setVisible
import cz.lamorak.wavyuser.util.showToast
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_no_data.*
import kotlinx.android.synthetic.main.layout_user_data.*
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
            user_reload.clicks().asIntent { LoadUser },
            user_delete.clicks().asIntent { DeleteUser }
    )

    override fun display(state: UserViewState) {
        user_no_data.setVisible(state === NoData)
        loading_indicator.setVisible(state === Loading)
        user_data.setVisible(state is UserData)
        user_delete.setVisible(state.deleteEnabled)
        if (state is UserData) with(state.user) {
            user_avatar.loadUri(profilePicture, circleCropTransform())
            user_full_name.text = "$firstName $lastName"
            user_phone.text = phoneNumber
            user_email.text = email
        }
    }

    override fun handle(action: UserViewAction) = when (action) {
        ShowLoadError -> showToast(R.string.user_load_error)
        ShowDeleteError -> showToast(R.string.user_delete_error)
        ShowUserDeleted -> showToast(R.string.user_deleted)
        else -> {
        }
    }
}
