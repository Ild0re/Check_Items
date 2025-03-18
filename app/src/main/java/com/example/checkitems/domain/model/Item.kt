package com.example.checkitems.domain.model

data class Item(
    val id: Int,
    val name: String,
    val time: Long,
    val tags: MutableList<String>,
    val amount: Int
)