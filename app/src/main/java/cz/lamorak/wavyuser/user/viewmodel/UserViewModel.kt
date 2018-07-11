package cz.lamorak.wavyuser.user.viewmodel

import cz.lamorak.architecture.ViewModel
import cz.lamorak.wavyuser.user.model.User

abstract class UserViewModel(initialState: UserViewState): ViewModel<UserViewIntent, UserViewState, UserViewAction>(initialState)

sealed class UserViewIntent : ViewModel.ViewIntent() {
    object LoadUser: UserViewIntent()
    data class UserLoaded(val user: User): UserViewIntent()
    data class UserLoadError(val throwable: Throwable): UserViewIntent()
    object DeleteUser: UserViewIntent()
    object UserDeleted: UserViewIntent()
    data class UserDeleteError(val throwable: Throwable): UserViewIntent()
}

sealed class UserViewState(open val user: User,
                           open val deleteEnabled: Boolean) : ViewModel.ViewState() {
    object NoData: UserViewState(User(), deleteEnabled = false)
    object Loading: UserViewState(User(), deleteEnabled = false)
    class UserData(user: User, deleteEnabled: Boolean): UserViewState(user, deleteEnabled)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserViewState) return false

        if (user != other.user) return false
        if (deleteEnabled != other.deleteEnabled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user.hashCode()
        result = 31 * result + deleteEnabled.hashCode()
        return result
    }
}

sealed class UserViewAction : ViewModel.ViewAction() {
    object None: UserViewAction()
    object ShowLoadError: UserViewAction()
    object ShowDeleteError: UserViewAction()
    object ShowUserDeleted: UserViewAction()
}