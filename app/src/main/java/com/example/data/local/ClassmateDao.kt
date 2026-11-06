package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassmateDao {
    @Query("SELECT * FROM classmates ORDER BY fullName ASC")
    fun getAllClassmates(): Flow<List<ClassmateEntity>>

    @Query("""
        SELECT * FROM classmates 
        WHERE fullName LIKE '%' || :query || '%' 
           OR nickname LIKE '%' || :query || '%'
           OR nativeVillage LIKE '%' || :query || '%'
           OR currentCity LIKE '%' || :query || '%'
           OR jobTitle LIKE '%' || :query || '%'
           OR organization LIKE '%' || :query || '%'
           OR occupationCategory LIKE '%' || :query || '%'
        ORDER BY fullName ASC
    """)
    fun searchClassmates(query: String): Flow<List<ClassmateEntity>>

    @Query("SELECT * FROM classmates WHERE id = :id LIMIT 1")
    fun getClassmateById(id: Long): Flow<ClassmateEntity?>

    @Query("SELECT COUNT(*) FROM classmates")
    fun getClassmateCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM classmates")
    suspend fun getClassmateCountSync(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClassmate(classmate: ClassmateEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(classmates: List<ClassmateEntity>)

    @Update
    suspend fun updateClassmate(classmate: ClassmateEntity)

    @Delete
    suspend fun deleteClassmate(classmate: ClassmateEntity)

    @Query("DELETE FROM classmates")
    suspend fun deleteAll()
}
