package com.example.checkitems.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Long,
    val tags: String,
    val amount: Int
)