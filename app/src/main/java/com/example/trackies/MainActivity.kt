package com.example.trackies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.authentication.ui.login.LogIn
import com.example.trackies.authentication.ui.login.RecoverThePassword
import com.example.trackies.authentication.ui.login.RecoverThePasswordInformation
import com.example.trackies.authentication.ui.register.Authenticate
import com.example.trackies.authentication.ui.register.CouldNotRegister
import com.example.trackies.authentication.ui.register.Register
import com.example.trackies.authentication.ui.welcomeScreen.WelcomeScreen
import com.example.trackies.homeScreen.presentation.HomeScreen

class MainActivity : ComponentActivity() {

    private val firebaseAuthenticator by lazy { FirebaseAuthentication() }

    private var uniqueIdentifier: String? = null
    private var signUpError: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//          check if a user is already signed in
            uniqueIdentifier = firebaseAuthenticator.getSignedInUser()

            val navigationController = rememberNavController()

            NavHost( navController = navigationController, startDestination = when (uniqueIdentifier) { null -> {"SignedOut"} else -> {"SignedIn"} } ) {

//              Welcome screen
                navigation( route = "SignedOut", startDestination = "WelcomeScreen" ) {

                    composable( route = "WelcomeScreen" ) {

                        WelcomeScreen { navigationController.navigate( it ) }
                    }

//                  Sign up
                    navigation( route = "SignUp", startDestination = "Register" ) {

                        composable( route = "Register" ) {

                            Register { credentials ->

                                firebaseAuthenticator.signUpWithEmailAndPassword(
                                    email = credentials.email,
                                    password = credentials.password,
                                    signUpError = {

                                        signUpError = it
                                        navigationController.navigate( "CouldNotRegister" ) { popUpTo("WelcomeScreen") { inclusive = false } }
                                    },
                                    authenticationResult = { waitingForAuthentication ->

                                        if (waitingForAuthentication) {
                                            firebaseAuthenticator.signOut()
                                            navigationController.navigate( "Authenticate" ) { popUpTo("WelcomeScreen") { inclusive = false } }
                                        }
                                        else { navigationController.navigate( "CouldNotRegister" ) { popUpTo("WelcomeScreen") { inclusive = false } } }
                                    }
                                )
                            }
                        }

                        composable( route = "Authenticate" ) {

                            Authenticate { navigationController.navigate("SignIn") { popUpTo("WelcomeScreen") { inclusive = false } } }
                        }

                        composable( route = "CouldNotRegister" ) {

                            CouldNotRegister (
                                errorCause = signUpError!!,
                                navigate = { navigationController.navigate("WelcomeScreen") { popUpTo("WelcomeScreen") { inclusive = false } } }
                            )
                        }
                    }

//                  Sign in
                    navigation( route = "SignIn", startDestination = "Login" ) {

                        composable( route = "Login" ) {

                            LogIn(
                                onContinue = { credentials ->
                                    firebaseAuthenticator.signInWithEmailAndPassword(

                                        email = credentials.email,
                                        password = credentials.password,
                                        signInError = {},
                                        authenticatedSuccessfully = { uid ->

                                            uniqueIdentifier = uid
                                            navigationController.navigate("SignedIn") {

                                                popUpTo("SignedOut") { inclusive = true }
                                            }
                                        }
                                    )
                                },
                                recoverThePassword = { navigationController.navigate("RecoverThePassword") }
                            )
                        }

                        composable( route = "RecoverThePassword" ) {
                            RecoverThePassword { email ->

                                firebaseAuthenticator.recoverThePassword(
                                    email = email,
                                    successfullySentEmail = {
                                        navigationController.navigate("RecoverThePassword-Information") { popUpTo("SignIn") { inclusive = false } }
                                    },
                                    failedToSendEmail = {
                                        Log.d("customFailedToSendEmail", it)
                                    }
                                )
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

                        HomeScreen(

                            uniqueIdentifier = uniqueIdentifier!!,
                            onSignOut = {

                                navigationController.navigate( route = "SignedOut" ) {

                                    popUpTo( route = "SignedIn" ) { inclusive = true }
                                }

                                firebaseAuthenticator.signOut()
                            },

                            onDelete = {

                                firebaseAuthenticator.deleteAccount(

                                    onComplete = {

                                        navigationController.navigate( route = "SignedOut" ) {

                                            popUpTo( route = "SignedIn" ) { inclusive = true }
                                        }
                                    },
                                    onFailure = {exception ->
                                        Log.d("halla", exception)
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}