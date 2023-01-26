package com.example.searchbin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.viewbinding.BuildConfig
import com.example.searchbin.data.db.converters.NetworkBinInfoDTOConverter
import com.example.searchbin.data.db.dao.CachedBinInfoDao
import com.example.searchbin.data.db.BinDbConstHolder.DB_NAME
import com.example.searchbin.data.db.BinDbConstHolder.DB_VERSION
import com.example.searchbin.data.entities.CachedBinInfoDTO

@Database(
    entities = [CachedBinInfoDTO::class], version = DB_VERSION, exportSchema = false
)
@TypeConverters(
    NetworkBinInfoDTOConverter::class
)
abstract class BinDb : RoomDatabase() {
    abstract fun binDao(): CachedBinInfoDao

    companion object {
        @Volatile
        private var instance: BinDb? = null

        fun getInstance(context: Context): BinDb {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): BinDb {
            val databaseBuilder = Room.databaseBuilder(context, BinDb::class.java, DB_NAME)
//            BinDb.addMigrations(databaseBuilder, context) TODO
            if (BuildConfig.DEBUG) {
                databaseBuilder.fallbackToDestructiveMigration()
            }
            return databaseBuilder.build()
        }
    }
}