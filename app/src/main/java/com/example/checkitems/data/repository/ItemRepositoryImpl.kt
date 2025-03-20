package com.example.checkitems.data.repository

import android.content.Context
import com.example.checkitems.data.AppDatabase
import com.example.checkitems.domain.model.Item
import com.example.checkitems.domain.repository.ItemRepository
import com.example.checkitems.utils.ItemDbConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemRepositoryImpl(
    private val context: Context,
    private val appDatabase: AppDatabase,
    private val converter: ItemDbConverter
) : ItemRepository {
    override suspend fun insertItem(item: Item) {
        val itemEntity = converter.mapToEntity(item)
        appDatabase.itemDao().insertItem(itemEntity)
    }

    override suspend fun updateItem(item: Item) {
        val updatedItem = converter.mapToEntity(item)
        appDatabase.itemDao().updateItem(updatedItem)
    }

    override suspend fun deleteItem(item: Item) {
        val deletedItem = converter.mapToEntity(item)
        appDatabase.itemDao().deleteItem(deletedItem)
    }

    override suspend fun getAllItems(): Flow<List<Item>> = flow {
        val itemEntities = appDatabase.itemDao().getAllItems()
        val items = converter.convertFromEntities(itemEntities)
        emit(items)
    }

    override suspend fun searchItems(string: String): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

}