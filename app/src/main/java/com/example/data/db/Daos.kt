package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?

    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}

@Dao
interface CropDiaryDao {
    @Query("SELECT * FROM crop_diary ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<CropDiaryEntity>>

    @Query("SELECT * FROM crop_diary WHERE cropName = :cropName ORDER BY timestamp DESC")
    fun getEntriesByCrop(cropName: String): Flow<List<CropDiaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: CropDiaryEntity): Long

    @Query("DELETE FROM crop_diary WHERE id = :id")
    suspend fun deleteEntry(id: Long)

    @Query("SELECT SUM(expenseAmount) FROM crop_diary")
    fun getTotalExpenses(): Flow<Double?>

    @Query("SELECT SUM(incomeAmount) FROM crop_diary")
    fun getTotalIncome(): Flow<Double?>

    @Query("SELECT COUNT(*) FROM crop_diary")
    suspend fun getEntryCount(): Int
}

@Dao
interface MarketplaceDao {
    @Query("SELECT * FROM marketplace_listings ORDER BY isFeatured DESC, timestamp DESC")
    fun getAllListings(): Flow<List<MarketplaceListingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: MarketplaceListingEntity): Long

    @Query("DELETE FROM marketplace_listings WHERE id = :id")
    suspend fun deleteListing(id: Long)

    @Query("SELECT COUNT(*) FROM marketplace_listings")
    suspend fun getListingCount(): Int
}

@Dao
interface ServiceBookingDao {
    @Query("SELECT * FROM service_bookings ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<ServiceBookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: ServiceBookingEntity): Long

    @Query("SELECT COUNT(*) FROM service_bookings")
    suspend fun getBookingCount(): Int

    @Query("SELECT SUM(estimatedFee) FROM service_bookings")
    fun getTotalBookingRevenue(): Flow<Double?>
}

@Dao
interface SavedDiagnosisDao {
    @Query("SELECT * FROM saved_diagnoses ORDER BY timestamp DESC")
    fun getAllDiagnoses(): Flow<List<SavedDiagnosisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosis(diagnosis: SavedDiagnosisEntity): Long

    @Query("SELECT COUNT(*) FROM saved_diagnoses")
    suspend fun getDiagnosisCount(): Int
}
