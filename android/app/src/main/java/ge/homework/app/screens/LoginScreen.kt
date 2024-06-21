package ge.homework.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ge.homework.app.models.response.UserType
import ge.homework.app.models.vm.UserViewModel


//@Preview
@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val type by userViewModel.type.observeAsState()
    
    type?.let { 
        if (type == UserType.ADMIN) {
            userViewModel.resetState()
            navController.navigate("admin/home")
        } else if (type == UserType.DEFAULT) {
            userViewModel.resetState()
            navController.navigate("normal/home")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        OutlinedTextField(
            label = {Text(text = "username")},
            value = username, 
            onValueChange = { username = it },
        )
        OutlinedTextField(
            label = {Text(text = "password")},
            value = password, 
            onValueChange = { password = it },
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { 
                userViewModel.createSession(username, password) 
            }
        ) {
            Text(text = "Sign in")
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text(text = "Register")
        }
    }
}

@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
) {

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var fullName by remember {
        mutableStateOf("")
    }
    var otpInp by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text(text = "username")},
            value = username, 
            onValueChange = { username = it },
        )

        OutlinedTextField(
            label = { Text(text = "password")},
            value = password, 
            onValueChange = { password = it },
        )

        OutlinedTextField(
            label = { Text(text = "full name")},
            value = fullName, 
            onValueChange = { fullName = it },
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                userViewModel.registerUser(
                    username, password, fullName
                )
            }) {
                Text(text = "Register")
            }

            TextButton(onClick = { navController.navigate("login") }) {
                Text(text = "Sign in")
            }
        }

        val otp by userViewModel.otp.observeAsState()

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "otp: ${otp ?: "- - - -"}")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = otpInp, onValueChange = { otpInp = it })

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { userViewModel.verifyUser(otpInp) }) {
            Text(text = "verify")
        }
    }

}