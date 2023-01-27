package com.example.searchbin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchbin.data.entities.CachedBinInfoDTO
import java.util.*

@Dao
abstract class CachedBinInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(cachedBinInfo: CachedBinInfoDTO)

    fun insertWithTimeStamp(cachedBinInfo: CachedBinInfoDTO) {
        insert(cachedBinInfo.apply {
            createdAt = System.currentTimeMillis()
        })
    }

    @Query("SELECT * FROM cached_bin_info WHERE bin = :binNumber")
    abstract fun isCached(binNumber: Long): List<CachedBinInfoDTO>

    @Query("SELECT * FROM cached_bin_info WHERE bin = :binNumber")
    abstract fun getBinData(binNumber: Long): CachedBinInfoDTO

    @Query("SELECT * FROM cached_bin_info")
    abstract fun getAllBinData(): List<CachedBinInfoDTO>

}