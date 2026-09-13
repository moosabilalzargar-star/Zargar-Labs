package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        CropDiaryEntity::class,
        MarketplaceListingEntity::class,
        ServiceBookingEntity::class,
        SavedDiagnosisEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cropDiaryDao(): CropDiaryDao
    abstract fun marketplaceDao(): MarketplaceDao
    abstract fun serviceBookingDao(): ServiceBookingDao
    abstract fun savedDiagnosisDao(): SavedDiagnosisDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jehlum_sense_ai_db"
                ).addCallback(DatabaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    prepopulateDatabase(database)
                }
            }
        }

        private suspend fun prepopulateDatabase(database: AppDatabase) {
            val userDao = database.userDao()
            val diaryDao = database.cropDiaryDao()
            val marketDao = database.marketplaceDao()
            val bookingDao = database.serviceBookingDao()
            val diagDao = database.savedDiagnosisDao()

            // Prepopulate default demo accounts:
            // 1. Farmer Demo Account
            userDao.insertUser(
                UserEntity(
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
            )
            // 2. Admin Account (Founders access)
            userDao.insertUser(
                UserEntity(
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
            )

            // Prepopulate sample Crop Diary entries
            diaryDao.insertEntry(
                CropDiaryEntity(
                    date = "2026-09-02",
                    cropName = "Apple",
                    activityType = "Harvest",
                    description = "Picked first batch of Red Delicious apple boxes. Packed 45 wooden boxes.",
                    expenseAmount = 4500.0,
                    harvestAmountKg = 900.0,
                    incomeAmount = 36000.0
                )
            )
            diaryDao.insertEntry(
                CropDiaryEntity(
                    date = "2026-08-18",
                    cropName = "Apple",
                    activityType = "Spray",
                    description = "Pre-harvest protective bio-fungicide spray against Alternaria leaf blotch.",
                    expenseAmount = 3200.0,
                    harvestAmountKg = 0.0,
                    incomeAmount = 0.0
                )
            )
            diaryDao.insertEntry(
                CropDiaryEntity(
                    date = "2026-07-25",
                    cropName = "Saffron",
                    activityType = "Weeding",
                    description = "Summer hoeing and bed preparation at Pampore karewa fields.",
                    expenseAmount = 2800.0,
                    harvestAmountKg = 0.0,
                    incomeAmount = 0.0
                )
            )

            // Prepopulate sample Marketplace Listings (Revenue & Farmer Trade)
            marketDao.insertListing(
                MarketplaceListingEntity(
                    farmerName = "Tariq Ahmad Bhat",
                    phone = "+91 97970 11223",
                    district = "Shopian",
                    produceName = "Apple",
                    variety = "Royal Delicious (Orchard Fresh)",
                    quantityAvailable = "250 Boxes (4,500 Kg)",
                    expectedPrice = "₹1,150 / box",
                    qualityGrade = "Grade A (Export Quality)",
                    isFeatured = true
                )
            )
            marketDao.insertListing(
                MarketplaceListingEntity(
                    farmerName = "Bashir Ahmad Wani",
                    phone = "+91 94191 88990",
                    district = "Pulwama (Pampore)",
                    produceName = "Pure Saffron (Zafran)",
                    variety = "Mongra Saffron (GI Tagged)",
                    quantityAvailable = "350 Grams",
                    expectedPrice = "₹280 / gram",
                    qualityGrade = "GI Certified Grade 1",
                    isFeatured = true
                )
            )
            marketDao.insertListing(
                MarketplaceListingEntity(
                    farmerName = "Abid Hussain Lone",
                    phone = "+91 96220 33445",
                    district = "Kupwara",
                    produceName = "Walnuts (Doon)",
                    variety = "Kaghzi Thin Shell Snow White",
                    quantityAvailable = "1,200 Kg",
                    expectedPrice = "₹390 / Kg",
                    qualityGrade = "Grade A Sun-Dried",
                    isFeatured = false
                )
            )

            // Prepopulate sample Service Bookings (Monetization features)
            bookingDao.insertBooking(
                ServiceBookingEntity(
                    serviceTitle = "Drone Spray Service",
                    farmerName = "Ghulam Mohammad Mir",
                    phone = "+91 94190 12345",
                    district = "Baramulla",
                    farmLocation = "Apple Orchard, Sopore Rd",
                    acreageOrKanals = 10.0,
                    estimatedFee = 1500.0,
                    scheduledDate = "2026-09-18",
                    status = "Confirmed"
                )
            )

            // Prepopulate sample Saved Diagnosis
            diagDao.insertDiagnosis(
                SavedDiagnosisEntity(
                    cropName = "Apple (Malus domestica)",
                    diagnosedIssue = "Apple Scab (Venturia inaequalis)",
                    confidenceScore = 92,
                    symptomsSummary = "Olive-green to brown velvety lesions on upper leaf surfaces and fruit spots.",
                    recommendedAction = "Apply systemic fungicide (e.g., Difenoconazole or Hexaconazole) strictly following SKUAST spray schedule.",
                    severity = "Moderate"
                )
            )
        }
    }
}
