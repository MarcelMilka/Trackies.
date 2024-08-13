package com.example.trackies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.*
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.authentication.ui.login.LogIn
import com.example.trackies.authentication.ui.login.RecoverThePassword
import com.example.trackies.authentication.ui.login.RecoverThePasswordInformation
import com.example.trackies.authentication.ui.register.Authenticate
import com.example.trackies.authentication.ui.register.CouldNotRegister
import com.example.trackies.authentication.ui.register.Register
import com.example.trackies.authentication.ui.welcomeScreen.WelcomeScreen
import com.example.trackies.confirmDeleting.ConfirmDeleting
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.HomeScreen
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.showAllTrackies.presentation.ShowAllTrackies
import com.example.trackies.switchToPremium.TrackiesPremium

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

            var startDestination = when (uniqueIdentifier) {

                null -> "SignedOut"

                else -> "SignedIn"
            }

            Log.d("start destination", startDestination)

            NavHost(
                navController = navigationController,
                startDestination = startDestination
            ) {

//              Welcome screen
                navigation(route = "SignedOut", startDestination = "WelcomeScreen") {

                    composable(route = "WelcomeScreen") { WelcomeScreen { navigationController.navigate(it) } }

//                  Sign up
                    navigation(route = "SignUp", startDestination = "Register") {

                        composable(route = "Register") {

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
                    navigation(route = "SignIn", startDestination = "Login") {

                        composable(route = "Login") {

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

                        composable(route = "RecoverThePassword") {
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

                        composable(route = "RecoverThePassword-Information") {
                            RecoverThePasswordInformation {
                                navigationController.navigate(it) { popUpTo("SignIn") { inclusive = false } }
                            }
                        }
                    }
                }

//              Home screen
                navigation( route = "SignedIn", startDestination = "HomeScreen" ) {

                    val sharedViewModel by lazy { SharedViewModel(uniqueIdentifier!!) }

                    composable(
                        route = "HomeScreen",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        HomeScreen(

                            uiState = sharedViewModel.uiState.collectAsState().value,

                            graphToDisplay = sharedViewModel.graphToDisplay.collectAsState().value,

                            onAddNewTrackie = {

                                navigationController.navigate("AddNewTrackie")
                            },

                            onCheck = { trackieViewState ->


                                sharedViewModel.checkTrackieAsIngestedForToday(trackieViewState = trackieViewState)
                            },

                            onShowAllTrackies = {

                                navigationController.navigate( route = "ShowAllTrackies" )
                            },

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
                            },

                            onChangeGraph = { sharedViewModel.changeGraphToDisplay(it) },

                            onDisplayDetails = {trackieViewState ->

                                sharedViewModel.setTrackieToDisplay( trackieToDisplay = trackieViewState )
                                navigationController.navigate("DetailedTrackie")
                            }
                        )
                    }

                    composable( route = "AddNewTrackie",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        AddNewTrackie(
                            uiState = sharedViewModel.uiState.collectAsState().value,
                            onReturn = { navigationController.navigateUp() },
                            onNavigateToTrackiesPremium = { navigationController.navigate("TrackiesPremium") },
                            onAdd = {trackie ->

                                val newTrackie = TrackieViewState (

                                    name = trackie.name,
                                    totalDose = trackie.totalDose,
                                    measuringUnit = trackie.measuringUnit,
                                    repeatOn = trackie.repeatOn,
                                    ingestionTime = trackie.ingestionTime
                                )

                                sharedViewModel.addNewTrackie(newTrackie)
                            }
                        )
                    }

                    composable( route = "ShowAllTrackies",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        ShowAllTrackies(

                            uiState = sharedViewModel.uiState.collectAsState().value,
                            onReturn = { navigationController.navigateUp() },
                            fetchAllUsersTrackies = { sharedViewModel.fetchAllTrackies() }
                        )
                    }

                    composable( route = "DetailedTrackie",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        DetailedTrackie(
                            trackieToDisplay = sharedViewModel.trackieToDisplay.collectAsState().value,
                            onReturn = { navigationController.navigateUp() },
                            onDelete = { navigationController.navigate("ConfirmDeleting") }
                        )
                    }

                    dialog( route = "ConfirmDeleting" ) {

                        ConfirmDeleting(

                            onConfirm = { navigationController.navigate("HomeScreen") { popUpTo("HomeScreen") {inclusive = true} } },
                            onDecline = { navigationController.navigateUp() }
                        )
                    }


                    dialog( route = "TrackiesPremium" ) {

                        TrackiesPremium { navigationController.navigateUp() }
                    }
                }
            }
        }
    }
}