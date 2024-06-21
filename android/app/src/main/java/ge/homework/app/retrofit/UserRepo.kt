package ge.homework.app.retrofit

import ge.homework.app.models.response.RegisterUserReq
import ge.homework.app.models.response.UserRegisterResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

object Auth {
    var token = "85b111d6-703d-4789-93ec-493f940c3e8c"
}

interface UserRepo {
    @POST("/um/users")
    suspend fun registerUser(@Body body: RegisterUserReq): Response<UserRegisterResp>
    
    @POST("/um/users/{id}/verification")
    suspend fun verifyUser(@Path("id") id: Long, @Body otpInp: Map<String, Int>): Response<UserRegisterResp>
    
    @POST("/sm/sessions")
    suspend fun createSession(@Body body: MutableMap<String, String>): Response<UserRegisterResp>
}