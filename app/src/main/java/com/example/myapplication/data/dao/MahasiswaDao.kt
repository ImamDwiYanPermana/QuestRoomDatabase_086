package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(
        mahasiswa: Mahasiswa
    )
    @Update
    suspend fun  updateMahasiswa(
        mahasiswa: Mahasiswa
    )
    @Delete
    suspend fun  deleteMahasiswa(
        mahasiswa: Mahasiswa
    )
    @Query
        ("SELECT * FROM mahasiswa ORDER BY nama ASC")
    fun getAllMahasiswa(): Flow<Mahasiswa>
    @Query
        ("SELECT * FROM mahasiswa ORDER BY nim ASC")
    fun getMahasiswa(nim: String): Flow<Mahasiswa>

}