package ge.homework.app.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        
        composable("register") {
            RegisterScreen(navController)
        }
        
        composable("normal/home") {
            HomeScreen(navController = navController)
        }
        
        composable("admin/home") {
            AdminHomeScreen(navController = navController)
        }
    }
}