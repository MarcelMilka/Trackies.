package com.example.trackies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//          navigation
            val navigationController = rememberNavController()

            NavHost( navController = navigationController, startDestination = "SignedOut" ) {

//              Welcome screen
                navigation( route = "SignedOut", startDestination = "WelcomeScreen" ) {

                    composable( route = "WelcomeScreen" ) {

                        WelcomeScreen { navigationController.navigate( it ) }
                    } // -> SignUp / SignIn

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
            }
        }
    }
}