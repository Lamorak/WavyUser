package cz.lamorak.wavyuser.user.viewmodel

import cz.lamorak.architecture.ViewModelTest
import cz.lamorak.wavyuser.user.interactor.DeleteUserInteractor
import cz.lamorak.wavyuser.user.interactor.GetUserInteractor
import cz.lamorak.wavyuser.user.model.User
import cz.lamorak.wavyuser.user.viewmodel.UserViewAction.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewIntent.*
import cz.lamorak.wavyuser.user.viewmodel.UserViewState.*
import cz.lamorak.wavyuser.util.toSingle
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock

class UserViewModelImplTest : ViewModelTest<UserViewIntent, UserViewState, UserViewAction>() {

    @Mock
    lateinit var getUserInteractor: GetUserInteractor
    @Mock
    lateinit var deleteUserInteractor: DeleteUserInteractor

    @Test
    fun loadData() {
        val user = User()
        whenever(getUserInteractor.getUser()).thenReturn(user.toSingle())
        initViewModel(NoData)
        val (states, actions) = testIntent(LoadUser)
        states.assertValues(
                NoData,
                Loading,
                UserData(user, true)
        )
        actions.assertValues(None, None)
    }

    @Test
    fun loadDataError() {
        whenever(getUserInteractor.getUser()).thenReturn(Single.error(Throwable()))
        initViewModel(NoData)
        val (states, actions) = testIntent(LoadUser)
        states.assertValues(
                NoData,
                Loading,
                NoData
        )
        actions.assertValues(None, ShowLoadError)
    }

    @Test
    fun deleteUser() {
        val user = User(id = "userId")
        whenever(deleteUserInteractor.delete(user.id)).thenReturn(Completable.complete())
        val initialState = UserData(user, true)
        initViewModel(initialState)
        val (states, actions) = testIntent(DeleteUser)
        states.assertValues(
                initialState,
                UserData(user, false),
                UserData(user, false)
        )
        actions.assertValues(None, ShowUserDeleted)
    }

    @Test
    fun deleteUserError() {
        whenever(deleteUserInteractor.delete(any())).thenReturn(Completable.error(Throwable()))
        val initialState = UserData(User(), true)
        initViewModel(initialState)
        val (states, actions) = testIntent(DeleteUser)
        states.assertValues(
                initialState,
                UserData(initialState.user, false),
                initialState
        )
        actions.assertValues(None, ShowDeleteError)
    }

    override fun initViewModel(initialState: UserViewState) {
        viewModel = UserViewModelImpl(initialState, getUserInteractor, deleteUserInteractor)
    }
}