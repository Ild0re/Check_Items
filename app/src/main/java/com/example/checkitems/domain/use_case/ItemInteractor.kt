package com.example.checkitems.domain.use_case

import com.example.checkitems.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemInteractor {

    suspend fun insertItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun getAllItems(): Flow<List<Item>>

    suspend fun searchItems(string: String): Flow<List<Item>>
}