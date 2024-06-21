package ge.homework.app.models.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.homework.app.models.response.RegisterUserReq
import ge.homework.app.models.response.UserType
import ge.homework.app.retrofit.Auth
import ge.homework.app.retrofit.UserRepo
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    val otp = MutableLiveData<String>()
    private var userId: Long? = 3

    fun registerUser(
        username: String,
        password: String,
        fullName: String
    ) {
        viewModelScope.launch {
            val resp = userRepo.registerUser(
                RegisterUserReq(
                    username, password, fullName
                )
            )

            if (resp.isSuccessful) {
                otp.postValue(resp.body()?.otp?.toString())
                userId = resp.body()?.id
            }
        }
    }

    fun verifyUser(otpInp: String) {
        userId?.let {
            viewModelScope.launch {
                userRepo.verifyUser(it, mapOf(Pair("code", otpInp.toInt())))
            }
        }
    }

    val type = MutableLiveData(UserType.NONE)
    
    fun createSession(username: String, password: String) {
        viewModelScope.launch {
            val resp = userRepo.createSession(
                mutableMapOf(
                    Pair("username", username),
                    Pair("password", password),
                )
            )
            
            if (resp.isSuccessful) {
                resp.headers()["X-Session-Token"]?.let {
                    Auth.token =  it    
                    type.value = resp.body()?.userType
                }
            }
        }
    }

    fun resetState() {
        type.value = UserType.NONE
    }
}