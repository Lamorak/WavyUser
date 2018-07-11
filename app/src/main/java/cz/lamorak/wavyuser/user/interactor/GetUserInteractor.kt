package cz.lamorak.wavyuser.user.interactor

import cz.lamorak.wavyuser.user.model.User
import io.reactivex.Single

interface GetUserInteractor {

    fun getUser(): Single<User>
}