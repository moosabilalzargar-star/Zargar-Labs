package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.JehlumBottomBar
import com.example.ui.components.JehlumTopBar
import com.example.ui.navigation.Screen
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.AiChatScreen
import com.example.ui.screens.CropAnalysisScreen
import com.example.ui.screens.CropDiaryScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KnowledgeBaseScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MarketplaceMonetizationScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SchemesScreen
import com.example.ui.screens.VoiceAssistantScreen
import com.example.ui.screens.WeatherScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AgriViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                JehlumSenseApp()
            }
        }
    }
}

@Composable
fun JehlumSenseApp(viewModel: AgriViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val feedbackMessage by viewModel.userFeedbackMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearFeedback()
        }
    }

    // Handle back button when inside sub-screens
    BackHandler(enabled = currentScreen != Screen.Home) {
        currentScreen = Screen.Home
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JehlumTopBar(
                currentScreen = currentScreen,
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { viewModel.selectLanguage(it) },
                onProfileClick = { currentScreen = Screen.Profile },
                userRole = currentUser?.role,
                onAdminClick = { currentScreen = Screen.Admin }
            )
        },
        bottomBar = {
            // Show bottom bar on primary tabs
            val showBottomBar = currentScreen in listOf(
                Screen.Home,
                Screen.AiChat,
                Screen.CropAnalysis,
                Screen.Marketplace,
                Screen.CropDiary,
                Screen.Weather,
                Screen.VoiceAssistant,
                Screen.Schemes,
                Screen.KnowledgeBase
            )
            if (showBottomBar) {
                JehlumBottomBar(
                    currentRoute = currentScreen.route,
                    onNavigate = { screen -> currentScreen = screen }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    viewModel = viewModel,
                    onNavigate = { screen -> currentScreen = screen }
                )

                Screen.AiChat -> AiChatScreen(
                    viewModel = viewModel
                )

                Screen.CropAnalysis -> CropAnalysisScreen(
                    viewModel = viewModel,
                    onNavigateToServices = { currentScreen = Screen.Marketplace }
                )

                Screen.VoiceAssistant -> VoiceAssistantScreen(
                    viewModel = viewModel
                )

                Screen.Weather -> WeatherScreen(
                    viewModel = viewModel
                )

                Screen.Marketplace -> MarketplaceMonetizationScreen(
                    viewModel = viewModel
                )

                Screen.CropDiary -> CropDiaryScreen(
                    viewModel = viewModel
                )

                Screen.Schemes -> SchemesScreen(
                    viewModel = viewModel
                )

                Screen.KnowledgeBase -> KnowledgeBaseScreen(
                    viewModel = viewModel
                )

                Screen.Profile -> ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToLogin = { currentScreen = Screen.Login },
                    onNavigateToAdmin = { currentScreen = Screen.Admin }
                )

                Screen.Admin -> AdminScreen(
                    viewModel = viewModel
                )

                Screen.About, Screen.Contact -> AboutScreen(
                    viewModel = viewModel
                )

                Screen.Login -> LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = { currentScreen = Screen.Home },
                    onNavigateToRegister = { currentScreen = Screen.Register },
                    onNavigateToForgot = { currentScreen = Screen.ForgotPassword }
                )

                Screen.Register -> RegisterScreen(
                    viewModel = viewModel,
                    onRegisterSuccess = { currentScreen = Screen.Home },
                    onNavigateToLogin = { currentScreen = Screen.Login }
                )

                Screen.ForgotPassword -> ForgotPasswordScreen(
                    viewModel = viewModel,
                    onBackToLogin = { currentScreen = Screen.Login }
                )
            }
        }
    }
}
