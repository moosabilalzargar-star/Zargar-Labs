package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object AiChat : Screen("ai_chat", "AI Assistant")
    object CropAnalysis : Screen("crop_analysis", "Crop Analysis")
    object VoiceAssistant : Screen("voice_assistant", "Voice Assistant")
    object Weather : Screen("weather", "Weather & Spray")
    object Marketplace : Screen("marketplace", "Mandi & Services")
    object Schemes : Screen("schemes", "Gov Schemes")
    object CropDiary : Screen("crop_diary", "Crop Diary")
    object KnowledgeBase : Screen("knowledge_base", "Knowledge Base")
    object Profile : Screen("profile", "User Profile")
    object Admin : Screen("admin", "Admin Dashboard")
    object About : Screen("about", "About & Founders")
    object Contact : Screen("contact", "Contact & Help")
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Sign Up")
    object ForgotPassword : Screen("forgot_password", "Forgot Password")
}
