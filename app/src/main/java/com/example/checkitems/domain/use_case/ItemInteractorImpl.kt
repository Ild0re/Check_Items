package com.example.checkitems.domain.use_case

import com.example.checkitems.domain.model.Item
import com.example.checkitems.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemInteractorImpl(private val repository: ItemRepository) : ItemInteractor {
    override suspend fun insertItem(item: Item) {
        return repository.insertItem(item)
    }

    override suspend fun updateItem(item: Item) {
        return repository.updateItem(item)
    }

    override suspend fun deleteItem(item: Item) {
        return repository.deleteItem(item)
    }

    override suspend fun getAllItems(): Flow<List<Item>> {
        return repository.getAllItems()
    }

    override suspend fun searchItems(string: String): Flow<List<Item>> {
        return repository.searchItems(string)
    }
}