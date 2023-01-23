package com.example.searchbin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "request_history",

    )
data class RequestHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    )