package com.example.searchbin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import java.util.*

@Dao
abstract class CachedBinInfoDao {

    private val calendar by lazy {
        Calendar.getInstance()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(cachedBinInfo: CachedBinInfoDTO)

    fun insertWithTimeStamp(cachedBinInfo: CachedBinInfoDTO) {
        insert(cachedBinInfo.apply {
            createdAt = calendar.time.toString()
        })
    }

    @Query("SELECT * FROM cached_bin_info WHERE bin = :binNumber")
    abstract fun isCached(binNumber: Long): List<CachedBinInfoDTO>

    @Query("SELECT * FROM cached_bin_info WHERE bin = :binNumber")
    abstract fun getBinData(binNumber: Long): CachedBinInfoDTO

    @Query("SELECT * FROM cached_bin_info")
    abstract fun getAllBinData(): List<CachedBinInfoDTO>

}