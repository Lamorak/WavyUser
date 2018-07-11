package cz.lamorak.wavyuser.user.interactor

import cz.lamorak.wavyuser.user.UserApi
import cz.lamorak.wavyuser.user.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetUserInteractorImpl(private val api: UserApi): GetUserInteractor {

    override fun getUser(): Single<User> = api.getUser()
            .subscribeOn(Schedulers.io())
}