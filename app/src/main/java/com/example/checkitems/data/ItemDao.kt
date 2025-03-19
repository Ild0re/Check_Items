package com.example.checkitems.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(entity = ItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Update(entity = ItemEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: ItemEntity)

    @Delete(entity = ItemEntity::class)
    suspend fun deleteItem(item: ItemEntity)

    @Query("SELECT * FROM item")
    suspend fun getAllItems() Flow<List<ItemEntity>>
}