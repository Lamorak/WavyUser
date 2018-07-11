package cz.lamorak.wavyuser.user

import cz.lamorak.wavyuser.user.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("user/all")
    fun getUser(): Single<User>

    @DELETE("user/{userId}")
    fun deleteUser(@Path("userId") userId: String): Completable
}