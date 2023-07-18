package com.example.searchbin.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.searchbin.data.db.BinDbConstHolder.DB_CACHED_BIN_INFO_TABLE_NAME
import com.example.searchbin.data.network.models.BinItem
import com.example.searchbin.data.network.models.NetworkBinInfoDTO
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = DB_CACHED_BIN_INFO_TABLE_NAME,
    indices = [
        Index("bin", unique = true),
    ]
)
@Immutable
@Parcelize
data class CachedBinInfoDTO(
    @PrimaryKey @ColumnInfo(name = "bin") var bin: Long,
    @ColumnInfo(name = "response") var response: NetworkBinInfoDTO,
    @ColumnInfo(name = "created_at") var createdAt: String? = null
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}