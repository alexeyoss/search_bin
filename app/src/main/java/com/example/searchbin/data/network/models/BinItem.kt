package com.example.searchbin.data.network.models

import com.example.searchbin.presentation.adapters.BinItemFingerprint

/** Common interface to operate the collection of DTO objects for recyclerview view types [BinItemFingerprint]*/
interface BinItem {
    fun toList(): List<BinItem>
}