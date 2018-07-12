package cz.lamorak.wavyuser.user

import cz.lamorak.architecture.ViewModel
import cz.lamorak.architecture.ViewModelFactory
import cz.lamorak.wavyuser.user.interactor.DeleteUserInteractor
import cz.lamorak.wavyuser.user.interactor.DeleteUserInteractorImpl
import cz.lamorak.wavyuser.user.interactor.GetUserInteractor
import cz.lamorak.wavyuser.user.interactor.GetUserInteractorImpl
import cz.lamorak.wavyuser.user.viewmodel.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UserModule {

    @Provides
    fun userViewModelFactory(viewModel: ViewModel<UserViewIntent, UserViewState, UserViewAction>): ViewModelFactory<ViewModel<UserViewIntent, UserViewState, UserViewAction>> = ViewModelFactory(viewModel)

    @Provides
    fun userViewModel(initialState: UserViewState,
                      getUserInteractor: GetUserInteractor,
                      deleteUserInteractor: DeleteUserInteractor): ViewModel<UserViewIntent, UserViewState, UserViewAction> = UserViewModelImpl(initialState, getUserInteractor, deleteUserInteractor)

    @Provides
    fun initialState(): UserViewState = UserViewState.NoData

    @Provides
    fun getUserInteractor(api: UserApi): GetUserInteractor = GetUserInteractorImpl(api)

    @Provides
    fun deleteUserInteractor(api: UserApi): DeleteUserInteractor = DeleteUserInteractorImpl(api)

    @Provides
    fun userApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}