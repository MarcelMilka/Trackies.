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

                            Register { navigationController.navigate( it ) }
                        } // -> SignedOut / Authenticate
                        composable( route = "Authenticate" ) {  } // -> SignIn
                    }

//                  Sign in
                    navigation( route = "SignIn", startDestination = "Login" ) {

                        composable( route = "Login" ) {  }
                    }
                }
            }
        }
    }
}