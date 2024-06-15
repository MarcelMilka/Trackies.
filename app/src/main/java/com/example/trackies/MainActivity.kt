package com.example.trackies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.authentication.ui.login.LogIn
import com.example.trackies.authentication.ui.login.RecoverThePassword
import com.example.trackies.authentication.ui.login.RecoverThePasswordInformation
import com.example.trackies.authentication.ui.register.Authenticate
import com.example.trackies.authentication.ui.register.Register
import com.example.trackies.authentication.ui.welcomeScreen.WelcomeScreen
import com.example.trackies.homeScreen.presentation.HomeScreen

class MainActivity : ComponentActivity() {

    private val firebaseAuthenticator by lazy {
        FirebaseAuthentication()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//          check if a user is already signed in
            val usersID = firebaseAuthenticator.getSignedInUser()

//          navigation
            val navigationController = rememberNavController()

            NavHost( navController = navigationController, startDestination = when (usersID) { null -> {"SignedOut"} else -> {"SignedIn"} } ) {

//              Welcome screen
                navigation( route = "SignedOut", startDestination = "WelcomeScreen" ) {

                    composable( route = "WelcomeScreen" ) {

                        WelcomeScreen { navigationController.navigate( it ) }
                    }

//                  Sign up
                    navigation( route = "SignUp", startDestination = "Register" ) {

                        composable( route = "Register" ) {

                            Register {

                                navigationController.navigate( it ) { popUpTo("WelcomeScreen") { inclusive = false } }
                            }
                        }
                        composable( route = "Authenticate" ) {

                            Authenticate { navigationController.navigate("SignIn") { popUpTo("WelcomeScreen") { inclusive = false } } }
                        }
                    }

//                  Sign in
                    navigation( route = "SignIn", startDestination = "Login" ) {

                        composable( route = "Login" ) { LogIn { navigationController.navigate(it) } }
                        composable( route = "RecoverThePassword" ) {
                            RecoverThePassword {
                                navigationController.navigate(it) { popUpTo("SignIn") { inclusive = false } }
                            }
                        }
                        composable( route = "RecoverThePassword-Information" ) {
                            RecoverThePasswordInformation {
                                navigationController.navigate(it) { popUpTo("SignIn") { inclusive = false } }
                            }
                        }
                    }
                }

//              Home screen
                navigation( route = "SignedIn", startDestination = "HomeScreen" ) {

                    composable( route = "HomeScreen" ) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}