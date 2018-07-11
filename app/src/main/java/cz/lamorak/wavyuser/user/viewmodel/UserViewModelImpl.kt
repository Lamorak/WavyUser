package cz.lamorak.wavyuser.user.viewmodel

import cz.lamorak.wavyuser.user.interactor.DeleteUserInteractor
import cz.lamorak.wavyuser.user.interactor.GetUserInteractor
import cz.lamorak.wavyuser.user.viewmodel.UserViewAction.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewIntent.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewState.*
import cz.lamorak.wavyuser.util.either
import io.reactivex.Observable

class UserViewModelImpl(initialState: UserViewState,
                        private val getUserInteractor: GetUserInteractor,
                        private val deleteUserInteractor: DeleteUserInteractor) : UserViewModel(initialState) {

    override fun resolveState(intent: UserViewIntent, state: UserViewState) = when (intent) {
        LoadUser -> Loading
        is UserLoaded -> UserData(intent.user, deleteEnabled = true)
        is UserLoadError -> NoData
        DeleteUser,
        UserDeleted -> UserData(state.user, deleteEnabled = false)
        is UserDeleteError -> UserData(state.user, deleteEnabled = true)
    }

    override fun resolveAction(intent: UserViewIntent, state: UserViewState) = when (intent) {
        is UserLoadError -> ShowLoadError
        UserDeleted -> ShowUserDeleted
        is UserDeleteError -> ShowDeleteError
        else -> None
    }

    override fun resolveModel(intent: UserViewIntent, state: UserViewState): Observable<UserViewIntent> = when (intent) {
        LoadUser -> getUserInteractor.getUser()
                .either({ UserLoaded(it) }, { UserLoadError(it) }).toObservable()

        DeleteUser -> deleteUserInteractor.delete(state.user.id)
                .either(UserDeleted, { UserDeleteError(it) }).toObservable()

        else -> Observable.empty<UserViewIntent>()
    }
}