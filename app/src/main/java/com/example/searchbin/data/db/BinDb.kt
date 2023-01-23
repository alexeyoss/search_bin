package com.example.searchbin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.viewbinding.BuildConfig
import com.example.searchbin.data.RequestHistory
import com.example.searchbin.presentation.utils.ConsHolder

/**
 * The [RoomDatabase] we use in this app.
 */

@Database(
    entities = [RequestHistory::class],
    version = ConsHolder.BIN_DB_VERSION,
    exportSchema = false
)
abstract class BinDb : RoomDatabase() {
    abstract fun binDao(): BinDao

    companion object {
        @Volatile
        private var instance: BinDb? = null

        fun getInstance(context: Context): BinDb {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): BinDb {
            val databaseBuilder = Room.databaseBuilder(context, BinDb::class.java, "wash_me.db")
//            BinDb.addMigrartions(databaseBuilder, context) TODO
            if (BuildConfig.DEBUG) {
                databaseBuilder.fallbackToDestructiveMigration()
            }
            return databaseBuilder.build()
        }
    }
}