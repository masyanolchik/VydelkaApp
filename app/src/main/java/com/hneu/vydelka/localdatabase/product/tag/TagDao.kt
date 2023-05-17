package com.hneu.vydelka.localdatabase.product.tag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTag(localTag: LocalTag): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTag(localTag: LocalTag)

    @Delete
    fun deleteTag(localTag: LocalTag)
    @Query("SELECT * FROM tags WHERE tagId=:tagId")
    fun getTag(tagId:Int): LocalTag

    @Query("SELECT * FROM tags")
    fun getTags(): List<LocalTag>
}