package com.example.checkitems.data.repository

import android.content.Context
import com.example.checkitems.data.AppDatabase
import com.example.checkitems.domain.model.Item
import com.example.checkitems.domain.repository.ItemRepository
import com.example.checkitems.utils.ItemDbConverter
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(
    private val context: Context,
    private val appDatabase: AppDatabase,
    private val converter: ItemDbConverter
) : ItemRepository {
    override suspend fun insertItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllItems(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchItems(string: String): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

}