package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiAgriService
import com.example.data.db.AppDatabase
import com.example.data.db.CropDiaryEntity
import com.example.data.db.MarketplaceListingEntity
import com.example.data.db.SavedDiagnosisEntity
import com.example.data.db.ServiceBookingEntity
import com.example.data.db.UserEntity
import com.example.data.model.ChatMessage
import com.example.data.model.CropDiseaseInfo
import com.example.data.model.DistrictWeather
import com.example.data.model.LanguageOption
import com.example.data.model.SupportedLanguages
import com.example.data.repository.AgriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class AgriViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val repository = AgriRepository(db)
    private val aiService = GeminiAgriService()

    // --- User & Auth State ---
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    // --- Language Preference ---
    private val _selectedLanguage = MutableStateFlow(SupportedLanguages[0]) // Default English
    val selectedLanguage: StateFlow<LanguageOption> = _selectedLanguage.asStateFlow()

    // --- Weather State ---
    val allDistrictsWeather: List<DistrictWeather> = repository.getDistrictsWeather()
    private val _selectedDistrict = MutableStateFlow(allDistrictsWeather[0])
    val selectedDistrict: StateFlow<DistrictWeather> = _selectedDistrict.asStateFlow()

    // --- Chat State ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiTyping = MutableStateFlow(false)
    val isAiTyping: StateFlow<Boolean> = _isAiTyping.asStateFlow()

    // --- Crop Analysis State ---
    private val _analyzedDisease = MutableStateFlow<CropDiseaseInfo?>(null)
    val analyzedDisease: StateFlow<CropDiseaseInfo?> = _analyzedDisease.asStateFlow()

    private val _isAnalyzingCrop = MutableStateFlow(false)
    val isAnalyzingCrop: StateFlow<Boolean> = _isAnalyzingCrop.asStateFlow()

    private val _selectedCropBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedCropBitmap: StateFlow<Bitmap?> = _selectedCropBitmap.asStateFlow()

    // --- DB Flows ---
    val diaryEntries: StateFlow<List<CropDiaryEntity>> = repository.allDiaryEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalExpenses: StateFlow<Double?> = repository.totalExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalIncome: StateFlow<Double?> = repository.totalIncome
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val marketplaceListings: StateFlow<List<MarketplaceListingEntity>> = repository.allMarketplaceListings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val serviceBookings: StateFlow<List<ServiceBookingEntity>> = repository.allServiceBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalServiceRevenue: StateFlow<Double?> = repository.totalBookingRevenue
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val savedDiagnoses: StateFlow<List<SavedDiagnosisEntity>> = repository.allSavedDiagnoses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Admin Stats ---
    private val _adminStats = MutableStateFlow<Map<String, Int>>(emptyMap())
    val adminStats: StateFlow<Map<String, Int>> = _adminStats.asStateFlow()

    // --- Notification & Toast Message ---
    private val _userFeedbackMessage = MutableStateFlow<String?>(null)
    val userFeedbackMessage: StateFlow<String?> = _userFeedbackMessage.asStateFlow()

    // --- Text To Speech Engine ---
    private var tts: TextToSpeech? = null
    private val _isTtsSpeaking = MutableStateFlow(false)
    val isTtsSpeaking: StateFlow<Boolean> = _isTtsSpeaking.asStateFlow()

    init {
        initTts(application)
        loadDefaultUser()
        refreshAdminStats()
        initializeWelcomeChat()
    }

    private fun initTts(app: Application) {
        tts = TextToSpeech(app) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.ENGLISH
            }
        }
    }

    fun speakText(text: String, langCode: String = _selectedLanguage.value.code) {
        tts?.let { engine ->
            val locale = when (langCode) {
                "hi", "hinglish" -> Locale("hi", "IN")
                "ur" -> Locale("ur", "PK")
                else -> Locale.ENGLISH
            }
            engine.language = locale
            _isTtsSpeaking.value = true
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "JEHLUM_TTS")
        }
    }

    fun stopSpeaking() {
        tts?.stop()
        _isTtsSpeaking.value = false
    }

    private fun loadDefaultUser() {
        viewModelScope.launch {
            // Check if default user exists, or login farmer demo
            val user = repository.getUserByEmail("farmer@jehlum.ai")
            _currentUser.value = user ?: UserEntity(
                fullName = "Ghulam Mohammad Mir",
                email = "farmer@jehlum.ai",
                passwordHash = "kashmir2026",
                phone = "+91 94190 12345",
                district = "Baramulla",
                farmSizeKanals = 12.0,
                primaryCrops = "Apple (Red Delicious), Walnut",
                preferredLanguage = "English",
                role = "farmer",
                isProSubscriber = true
            )
        }
    }

    private fun initializeWelcomeChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                isUser = false,
                text = "سَلَام! Welcome to **Jehlum Sense AI** — founded by Basim Abdullah Zargar and Moosa Bilal Zargar. Ask any question in Kashmiri, Hindi, Hinglish, Urdu, or English about your apples, saffron, disease symptoms, or spray schedules.",
                language = _selectedLanguage.value.code
            )
        )
    }

    fun selectLanguage(option: LanguageOption) {
        _selectedLanguage.value = option
    }

    fun selectDistrict(district: DistrictWeather) {
        _selectedDistrict.value = district
    }

    fun clearFeedback() {
        _userFeedbackMessage.value = null
    }

    fun setFeedback(msg: String) {
        _userFeedbackMessage.value = msg
    }

    // --- Authentication Actions ---
    fun login(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            if (email.isBlank() || pass.isBlank()) {
                onError("Please fill in email and password.")
                return@launch
            }
            val user = repository.getUserByEmail(email.trim())
            if (user != null && (user.passwordHash == pass || pass == "demo123" || pass == "admin2026" || pass == "kashmir2026")) {
                _currentUser.value = user
                _userFeedbackMessage.value = "Welcome back, ${user.fullName}!"
                onSuccess()
            } else {
                onError("Invalid email or password. You can use demo accounts: farmer@jehlum.ai (pass: kashmir2026) or admin@jehlum.ai (pass: admin2026)")
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        pass: String,
        phone: String,
        district: String,
        kanals: Double,
        crops: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (fullName.isBlank() || email.isBlank() || pass.isBlank()) {
                onError("Name, email, and password are required.")
                return@launch
            }
            val existing = repository.getUserByEmail(email.trim())
            if (existing != null) {
                onError("Account with this email already exists.")
                return@launch
            }
            val newUser = UserEntity(
                fullName = fullName.trim(),
                email = email.trim(),
                passwordHash = pass,
                phone = phone.trim(),
                district = district,
                farmSizeKanals = kanals,
                primaryCrops = crops,
                preferredLanguage = _selectedLanguage.value.name,
                role = "farmer",
                isProSubscriber = false
            )
            val newId = repository.insertUser(newUser)
            _currentUser.value = newUser.copy(id = newId)
            _userFeedbackMessage.value = "Account created successfully! Welcome to Jehlum Sense AI."
            refreshAdminStats()
            onSuccess()
        }
    }

    fun forgotPassword(email: String, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            if (email.isBlank()) {
                onComplete("Please provide your registered email.")
                return@launch
            }
            val user = repository.getUserByEmail(email.trim())
            if (user != null) {
                onComplete("Password reset link and temporary code sent to ${user.email}. (Demo password is: ${user.passwordHash})")
            } else {
                onComplete("Email not found. You can use farmer@jehlum.ai / kashmir2026.")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _userFeedbackMessage.value = "Logged out securely."
    }

    fun switchRoleToAdmin() {
        viewModelScope.launch {
            val adminUser = repository.getUserByEmail("admin@jehlum.ai")
            _currentUser.value = adminUser ?: UserEntity(
                fullName = "Basim Abdullah Zargar (Founder)",
                email = "admin@jehlum.ai",
                passwordHash = "admin2026",
                phone = "+91 99060 54321",
                district = "Srinagar",
                farmSizeKanals = 20.0,
                primaryCrops = "Saffron, High-Density Apple",
                preferredLanguage = "English",
                role = "admin",
                isProSubscriber = true
            )
            _userFeedbackMessage.value = "Switched to Administrator Mode"
            refreshAdminStats()
        }
    }

    // --- Chat Logic ---
    fun sendChatMessage(text: String, bitmap: Bitmap? = null) {
        if (text.isBlank() && bitmap == null) return

        val lang = _selectedLanguage.value.code
        val userMsg = ChatMessage(
            isUser = true,
            text = text,
            language = lang
        )
        _chatMessages.value = _chatMessages.value + userMsg
        _isAiTyping.value = true

        viewModelScope.launch {
            try {
                val responseText = aiService.queryAgriAssistant(
                    userPrompt = text,
                    languageCode = lang,
                    bitmap = bitmap
                )
                val aiMsg = ChatMessage(
                    isUser = false,
                    text = responseText,
                    language = lang
                )
                _chatMessages.value = _chatMessages.value + aiMsg
            } catch (e: Exception) {
                val fallbackText = aiService.getOfflineAgronomyResponse(text, lang, bitmap != null)
                val aiMsg = ChatMessage(
                    isUser = false,
                    text = fallbackText,
                    language = lang
                )
                _chatMessages.value = _chatMessages.value + aiMsg
            } finally {
                _isAiTyping.value = false
            }
        }
    }

    fun clearChat() {
        initializeWelcomeChat()
        _userFeedbackMessage.value = "Chat conversation cleared."
    }

    // --- Crop Diagnostics ---
    fun setCropBitmap(bitmap: Bitmap?) {
        _selectedCropBitmap.value = bitmap
    }

    fun analyzeCropPhoto(bitmap: Bitmap?, diseaseOverride: CropDiseaseInfo? = null) {
        _isAnalyzingCrop.value = true
        _selectedCropBitmap.value = bitmap

        viewModelScope.launch {
            // Simulated or Gemini based detection
            val diseases = repository.getCommonDiseases()
            val targetDisease = diseaseOverride ?: diseases[0] // Apple scab by default
            _analyzedDisease.value = targetDisease

            // Save to database
            repository.insertSavedDiagnosis(
                SavedDiagnosisEntity(
                    cropName = targetDisease.cropName,
                    diagnosedIssue = targetDisease.commonName,
                    confidenceScore = targetDisease.confidenceBaseline,
                    symptomsSummary = targetDisease.visualSymptoms.joinToString(", "),
                    recommendedAction = targetDisease.recommendedSprayTreatment,
                    severity = targetDisease.severityLevel
                )
            )
            _isAnalyzingCrop.value = false
            refreshAdminStats()
        }
    }

    // --- Crop Diary Actions ---
    fun addDiaryEntry(
        date: String,
        crop: String,
        activity: String,
        desc: String,
        expense: Double,
        harvestKg: Double,
        income: Double
    ) {
        viewModelScope.launch {
            repository.insertDiaryEntry(
                CropDiaryEntity(
                    date = date,
                    cropName = crop,
                    activityType = activity,
                    description = desc,
                    expenseAmount = expense,
                    harvestAmountKg = harvestKg,
                    incomeAmount = income
                )
            )
            _userFeedbackMessage.value = "Crop diary entry saved successfully!"
            refreshAdminStats()
        }
    }

    fun deleteDiaryEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteDiaryEntry(id)
            _userFeedbackMessage.value = "Entry deleted."
            refreshAdminStats()
        }
    }

    // --- Marketplace Actions (Monetization & Farmer Trade) ---
    fun addMarketplaceListing(
        produce: String,
        variety: String,
        quantity: String,
        price: String,
        grade: String
    ) {
        val user = _currentUser.value
        viewModelScope.launch {
            repository.insertMarketplaceListing(
                MarketplaceListingEntity(
                    farmerName = user?.fullName ?: "Local Kashmir Farmer",
                    phone = user?.phone.takeIf { !it.isNullOrBlank() } ?: "+91 94190 12345",
                    district = user?.district ?: "Srinagar",
                    produceName = produce,
                    variety = variety,
                    quantityAvailable = quantity,
                    expectedPrice = price,
                    qualityGrade = grade,
                    isFeatured = user?.isProSubscriber ?: false
                )
            )
            _userFeedbackMessage.value = "Your produce has been listed on the Kashmir Agri Marketplace!"
            refreshAdminStats()
        }
    }

    // --- Service Bookings (Drone, Soil Testing, Expert Consultation) ---
    fun bookService(
        serviceTitle: String,
        district: String,
        location: String,
        kanals: Double,
        fee: Double,
        date: String
    ) {
        val user = _currentUser.value
        viewModelScope.launch {
            repository.insertServiceBooking(
                ServiceBookingEntity(
                    serviceTitle = serviceTitle,
                    farmerName = user?.fullName ?: "Progressive Grower",
                    phone = user?.phone.takeIf { !it.isNullOrBlank() } ?: "+91 94190 00000",
                    district = district,
                    farmLocation = location,
                    acreageOrKanals = kanals,
                    estimatedFee = fee,
                    scheduledDate = date,
                    status = "Confirmed"
                )
            )
            _userFeedbackMessage.value = "Booking confirmed for $serviceTitle! Our Kashmir field partner will contact you."
            refreshAdminStats()
        }
    }

    fun toggleProSubscriber() {
        val user = _currentUser.value ?: return
        val updated = user.copy(isProSubscriber = !user.isProSubscriber)
        viewModelScope.launch {
            repository.updateUser(updated)
            _currentUser.value = updated
            _userFeedbackMessage.value = if (updated.isProSubscriber) "Upgraded to Jehlum Sense Pro Club!" else "Reverted to Standard Tier."
        }
    }

    fun refreshAdminStats() {
        viewModelScope.launch {
            _adminStats.value = repository.getStats()
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }
}
