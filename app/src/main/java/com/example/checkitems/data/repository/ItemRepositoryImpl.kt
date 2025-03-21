package com.example.checkitems.data.repository

import com.example.checkitems.data.AppDatabase
import com.example.checkitems.data.ItemEntity
import com.example.checkitems.domain.model.Item
import com.example.checkitems.domain.repository.ItemRepository
import com.example.checkitems.utils.ItemDbConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: ItemDbConverter
) : ItemRepository {
    override suspend fun insertItem(item: Item) {
        appDatabase.itemDao().insertItem(getEntities(item))
    }

    override suspend fun updateItem(item: Item) {
        appDatabase.itemDao().updateItem(getEntities(item))
    }

    override suspend fun deleteItem(item: Item) {
        appDatabase.itemDao().deleteItem(getEntities(item))
    }

    override suspend fun getAllItems(): Flow<List<Item>> = flow {
        val itemEntities = appDatabase.itemDao().getAllItems()
        val items = converter.convertFromEntities(itemEntities)
        emit(items)
    }

    override suspend fun searchItems(string: String): Flow<List<Item>> = flow {
        val itemEntities = appDatabase.itemDao().searchItem(string)
        val items = converter.convertFromEntities(itemEntities)
        emit(items)
    }

    private fun getEntities(item: Item): ItemEntity {
        val newItem = converter.mapToEntity(item)
        return newItem
    }
}