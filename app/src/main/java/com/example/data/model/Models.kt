package com.example.data.model

data class LanguageOption(
    val code: String,
    val name: String,
    val nativeLabel: String,
    val speechLocaleTag: String
)

val SupportedLanguages = listOf(
    LanguageOption("en", "English", "English", "en-IN"),
    LanguageOption("hi", "Hindi", "हिन्दी", "hi-IN"),
    LanguageOption("ur", "Urdu", "اردو", "ur-PK"),
    LanguageOption("ks", "Kashmiri", "كٲشُر", "ks-IN"),
    LanguageOption("hinglish", "Hinglish", "Hinglish (Hindi+English)", "hi-IN")
)

data class DistrictWeather(
    val districtName: String,
    val temperatureC: Int,
    val condition: String,
    val humidityPercent: Int,
    val rainProbability: Int,
    val windSpeedKmh: Int,
    val frostRiskLevel: String, // "Low", "Moderate", "High Alert"
    val sprayAdvisory: String,
    val sprayStatus: String // "EXCELLENT", "CAUTION", "AVOID"
)

data class GovScheme(
    val id: String,
    val title: String,
    val acronym: String,
    val department: String,
    val subsidyBenefit: String,
    val briefDescription: String,
    val eligibility: List<String>,
    val documentRequirements: List<String>,
    val helpline: String,
    val officialPortal: String
)

data class KnowledgeArticle(
    val id: String,
    val crop: String,
    val title: String,
    val category: String, // "Cultivation", "Pest Management", "Fertigation", "Harvest"
    val season: String,
    val summary: String,
    val keyRecommendations: List<String>
)

data class CropDiseaseInfo(
    val id: String,
    val cropName: String,
    val commonName: String,
    val scientificName: String,
    val severityLevel: String, // "Mild", "Moderate", "Severe - Quarantine / Alert"
    val visualSymptoms: List<String>,
    val culturalPrevention: List<String>,
    val recommendedSprayTreatment: String,
    val skuastReferralAdvice: String,
    val confidenceBaseline: Int
)

data class MandiRate(
    val mandiName: String,
    val commodity: String,
    val variety: String,
    val modalPrice: String,
    val priceRange: String,
    val trend: String // "UP", "DOWN", "STABLE"
)

data class AgriService(
    val id: String,
    val title: String,
    val subtitle: String,
    val pricePerUnit: Double,
    val unitLabel: String,
    val badge: String,
    val description: String,
    val highlights: List<String>
)

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val isUser: Boolean,
    val text: String,
    val language: String = "en",
    val timestamp: Long = System.currentTimeMillis(),
    val imageUri: String? = null
)
