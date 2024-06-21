package ge.homework.app.models.response



data class UserRegisterResp(
    val  id: Long,
    val  username: String,
    val  fullName: String,
    val  userType: UserType,
    val  otp: Int?,
    val  verified: Boolean,
)

enum class UserType {
    DEFAULT, ADMIN, NONE
}

data class RegisterUserReq(
    val username: String,
    val password: String,
    val fullName: String,
)