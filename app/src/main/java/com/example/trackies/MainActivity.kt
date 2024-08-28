package com.example.trackies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.authentication.ui.login.LogIn
import com.example.trackies.authentication.ui.login.RecoverThePassword
import com.example.trackies.authentication.ui.login.RecoverThePasswordInformation
import com.example.trackies.authentication.ui.register.Authenticate
import com.example.trackies.authentication.ui.register.CouldNotRegister
import com.example.trackies.authentication.ui.register.Register
import com.example.trackies.authentication.ui.welcomeScreen.WelcomeScreen
import com.example.trackies.confirmDeleting.ConfirmTrackieDeletion
import com.example.trackies.detailedTrackie.presentation.DetailedTrackieViewModel
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.HomeScreen
import com.example.trackies.homeScreen.presentation.HomeScreenViewModel
import com.example.trackies.settings.Settings
import com.example.trackies.showAllTrackies.presentation.ShowAllTrackies
import com.example.trackies.switchToPremium.TrackiesPremium
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

                    composable(
                        route = "WelcomeScreen",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {

                        WelcomeScreen { navigationController.navigate(it) }
                    }

//                  Sign up
                    navigation(route = "SignUp", startDestination = "Register") {

                        composable(
                            route = "Register",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                        ) {

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

                        composable(
                            route = "Authenticate",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {

                            Authenticate { navigationController.navigate("SignIn") { popUpTo("WelcomeScreen") { inclusive = false } } }
                        }

                        composable(
                            route = "CouldNotRegister",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {

                            CouldNotRegister (
                                errorCause = signUpError!!,
                                navigate = { navigationController.navigate("WelcomeScreen") { popUpTo("WelcomeScreen") { inclusive = false } } }
                            )
                        }
                    }

//                  Sign in
                    navigation(route = "SignIn", startDestination = "Login") {

                        composable(
                            route = "Login",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {

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

                        composable(
                            route = "RecoverThePassword",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {

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

                        composable(
                            route = "RecoverThePassword-Information",
                            enterTransition = {EnterTransition.None },
                            exitTransition = { ExitTransition.None }
                        ) {

                            RecoverThePasswordInformation {
                                navigationController.navigate(it) { popUpTo("SignIn") { inclusive = false } }
                            }
                        }
                    }
                }

//              Home screen
                navigation( route = "SignedIn", startDestination = "HomeScreen" ) {

                    composable(
                        route = "HomeScreen",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        val homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navigationController)
                        val detailedTrackieViewModel = it.sharedViewModel<DetailedTrackieViewModel>(navigationController)

                        HomeScreen(

                            heightOfHomeScreenLazyColumn = homeScreenViewModel.heightOfHomeScreenLazyColumn,

                            uiState = homeScreenViewModel.uiState.collectAsState().value,

                            typeOfHomeScreenGraphToDisplay = homeScreenViewModel.typeOfHomeScreenGraphToDisplay.collectAsState().value,

                            onOpenSettings = { navigationController.navigate("Settings") },

                            onAddNewTrackie = {

                                navigationController.navigate("AddNewTrackie")
                            },

                            onMarkTrackieAsIngestedForToday = { trackieViewState ->

                                homeScreenViewModel.markTrackieAsIngestedForToday(trackieViewState = trackieViewState)
                            },

                            onShowAllTrackies = {

                                navigationController.navigate( route = "ShowAllTrackies" )
                            },

                            onChangeGraph = { homeScreenViewModel.changeGraphToDisplay(it) },

                            onDisplayDetailedTrackie = { trackieViewState ->

                                detailedTrackieViewModel.setTrackieToDisplayDetails( trackieViewState = trackieViewState )
                                detailedTrackieViewModel.calculateWeeklyRegularity( trackieViewState = trackieViewState)
                                navigationController.navigate( route = "DetailedTrackie" )
                            }
                        )
                    }

                    composable(
                        route = "AddNewTrackie",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        val homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navigationController)

                        AddNewTrackie(

                            uiState = homeScreenViewModel.uiState.collectAsState().value,
                            onReturn = { navigationController.navigateUp() },
                            onNavigateToTrackiesPremium = { navigationController.navigate("TrackiesPremium") },
                            onAdd = { trackie ->

                                val newTrackie = TrackieViewState (

                                    name = trackie.name,
                                    totalDose = trackie.totalDose,
                                    measuringUnit = trackie.measuringUnit,
                                    repeatOn = trackie.repeatOn,
                                    ingestionTime = trackie.ingestionTime
                                )

                                homeScreenViewModel.addNewTrackie(newTrackie)
                            }
                        )
                    }

                    composable(
                        route = "ShowAllTrackies",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        val homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navigationController)
                        val detailedTrackieViewModel = it.sharedViewModel<DetailedTrackieViewModel>(navigationController)

                        ShowAllTrackies(

                            uiState = homeScreenViewModel.uiState.collectAsState().value,
                            fetchAllUsersTrackies = { homeScreenViewModel.fetchAllTrackies() },
                            onReturn = { navigationController.navigateUp() },
                            onMarkTrackieAsIngested = {trackieViewState ->

                                homeScreenViewModel.markTrackieAsIngestedForToday(trackieViewState = trackieViewState)
                            },
                            onDisplayDetails = { trackieViewState ->

                                detailedTrackieViewModel.setTrackieToDisplayDetails( trackieViewState = trackieViewState )
                                detailedTrackieViewModel.calculateWeeklyRegularity( trackieViewState = trackieViewState)
                                navigationController.navigate("DetailedTrackie")
                            }
                        )
                    }

                    composable(
                        route = "DetailedTrackie",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        val detailedTrackieViewModel = it.sharedViewModel<DetailedTrackieViewModel>(navigationController)

                        DetailedTrackie(

                            uiState = detailedTrackieViewModel.uiState.collectAsState().value,
                            displayDetailsOf = detailedTrackieViewModel.trackieToDisplay.collectAsState().value,
                            onReturn = { navigationController.navigateUp() },
                            onDelete = { navigationController.navigate("ConfirmTrackieDeletion") }
                        )
                    }

                    composable(
                        route = "Settings",
                        enterTransition = {EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ) {

                        Settings(

                            onReturnHomeScreen = { navigationController.navigateUp() },
                            onChangeEmail = {},
                            onChangePassword = {},
                            onDeleteAccount = {},
                            onChangeLanguage = {},
                            onLogout = {

                                navigationController.navigate( route = "SignedOut" ) {

                                    popUpTo( route = "SignedIn" ) { inclusive = true }
                                }

                                uniqueIdentifier = null

                                firebaseAuthenticator.signOut()
                            }
                        )
                    }

                    dialog(route = "ConfirmTrackieDeletion") {

                        val homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navigationController)
                        val detailedTrackieViewModel = it.sharedViewModel<DetailedTrackieViewModel>(navigationController)

                        ConfirmTrackieDeletion(

                            nameOfTheTrackieToDelete = detailedTrackieViewModel.trackieToDisplay.collectAsState().value,
                            onConfirm = {

                                navigationController.navigate("HomeScreen") { popUpTo("HomeScreen") {inclusive = true} }
                                homeScreenViewModel.deleteTrackie(it)
                            },
                            onDecline = { navigationController.navigateUp() }
                        )
                    }

                    dialog(route = "TrackiesPremium") { TrackiesPremium { navigationController.navigateUp() } }
                }
            }
        }
    }
}

@Composable
//  the reified keyword allows the function to access the type of T at runtime
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {

//  getting information where the user already is e.g "HomeScreen", "Settings" etc.
    val navGraphRoute = destination.parent?.route ?: return viewModel()

//  whenever the function (this) gets called again,
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(viewModelStoreOwner = parentEntry)
}