package cz.lamorak.wavyuser.user.model

data class User(
        val id: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val phoneNumber: String = "",
        val email: String = "",
        val profilePicture: String = ""
)