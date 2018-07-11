package cz.lamorak.wavyuser.user.interactor

import cz.lamorak.wavyuser.user.UserApi
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class DeleteUserInteractorImpl(private val api: UserApi) : DeleteUserInteractor {

    override fun delete(userId: String): Completable = api.deleteUser(userId)
            .subscribeOn(Schedulers.io())
}