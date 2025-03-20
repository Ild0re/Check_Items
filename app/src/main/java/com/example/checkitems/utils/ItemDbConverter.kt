package com.example.checkitems.utils

import com.example.checkitems.data.ItemEntity
import com.example.checkitems.domain.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemDbConverter {

    fun mapToEntity(item: Item): ItemEntity {
        return ItemEntity(
            item.id,
            item.name,
            item.time,
            listToString(item.tags),
            item.amount
        )
    }

    fun mapToItem(itemEntity: ItemEntity): Item {
        return Item(
            itemEntity.id,
            itemEntity.name,
            itemEntity.time,
            stringToList(itemEntity.tags),
            itemEntity.amount
        )
    }

    fun convertFromEntities(list: List<ItemEntity>): List<Item> {
        return list.map { item -> mapToItem(item) }
    }

    private fun listToString(list: MutableList<String>): String {
        return Gson().toJson(list)
    }

    private fun stringToList(string: String): MutableList<String> {
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(string, type)
    }
}