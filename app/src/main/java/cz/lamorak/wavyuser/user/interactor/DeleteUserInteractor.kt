package cz.lamorak.wavyuser.user.interactor

import io.reactivex.Completable

interface DeleteUserInteractor {

    fun delete(userId: String): Completable
}