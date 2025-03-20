package com.example.checkitems.domain.repository

import com.example.checkitems.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun insertItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun getAllItems(): Flow<List<Item>>

    suspend fun searchItems(string: String): Flow<List<Item>>
}