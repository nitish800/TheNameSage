package com.nitish.thenamegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.nitish.thenamegame.intent.GoogleAuthUiClient
import com.nitish.thenamegame.intent.PredictViewModel
import com.nitish.thenamegame.intent.SignInViewModel
import com.nitish.thenamegame.ui.theme.TheNameGameTheme
import com.nitish.thenamegame.view.HomePage
import com.nitish.thenamegame.view.NameDetails
import com.nitish.thenamegame.view.SignupPage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val preViewModel: PredictViewModel by viewModels()
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.navigationBarColor = ContextCompat.getColor(this@MainActivity, R.color.black)
        FirebaseApp.initializeApp(this);
        setContent {
            TheNameGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "LOGIN") {
                        composable("LOGIN") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSingedInUser() != null) {
                                    navController.navigate("HOME")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    navController.navigate("HOME")
                                    viewModel.resetState()
                                }
                            }

                            SignupPage(state = state, onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            })


                        }



                        composable("HOME") {
                            HomePage(predictViewModel = preViewModel, navController = navController)
                        }

                        composable("PROFILE") {
                            NameDetails(
                                predictViewModel = preViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

