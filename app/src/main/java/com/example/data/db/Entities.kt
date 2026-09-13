package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val email: String,
    val passwordHash: String,
    val phone: String = "",
    val district: String = "Srinagar",
    val farmSizeKanals: Double = 8.0,
    val primaryCrops: String = "Apple, Saffron",
    val preferredLanguage: String = "English", // "English", "Hindi", "Urdu", "Kashmiri", "Hinglish"
    val role: String = "farmer", // "farmer" or "admin"
    val isProSubscriber: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "crop_diary")
data class CropDiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val cropName: String, // Apple, Saffron, Walnut, Cherry, Rice, Almond, etc.
    val activityType: String, // Spray, Fertilizer, Pruning, Irrigation, Weeding, Harvest, Sale
    val description: String,
    val expenseAmount: Double = 0.0,
    val harvestAmountKg: Double = 0.0,
    val incomeAmount: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "marketplace_listings")
data class MarketplaceListingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val farmerName: String,
    val phone: String,
    val district: String,
    val produceName: String,
    val variety: String,
    val quantityAvailable: String,
    val expectedPrice: String,
    val qualityGrade: String = "Grade A (Export / Premium)",
    val isFeatured: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "service_bookings")
data class ServiceBookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val serviceTitle: String, // "Drone Spray Service", "SKUAST Certified Soil Testing", "Agronomist 1-on-1 Consult"
    val farmerName: String,
    val phone: String,
    val district: String,
    val farmLocation: String,
    val acreageOrKanals: Double,
    val estimatedFee: Double,
    val scheduledDate: String,
    val status: String = "Confirmed", // "Pending", "Confirmed", "Completed"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_diagnoses")
data class SavedDiagnosisEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cropName: String,
    val diagnosedIssue: String,
    val confidenceScore: Int,
    val symptomsSummary: String,
    val recommendedAction: String,
    val severity: String, // "Low", "Moderate", "Critical - Consult SKUAST"
    val timestamp: Long = System.currentTimeMillis()
)
