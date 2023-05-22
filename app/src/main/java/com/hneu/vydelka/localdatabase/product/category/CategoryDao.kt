package com.hneu.vydelka.localdatabase.product.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(localCategory: LocalCategory): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAttribute(localCategory: LocalCategory)

    @Delete
    fun deleteAttribute(localCategory: LocalCategory)

    @Query("SELECT * from categories WHERE categoryId=:categoryId")
    fun getCategoryById(categoryId: Int): LocalCategory

    @Transaction
    @Query("SELECT * from categories WHERE categoryId=:categoryId")
    fun getCategoryByIdWithAttributes(categoryId: Int): LocalCategoryWithLocalAttributeGroups

    @Query("SELECT * from categories")
    fun getCategories(): List<LocalCategory>

    @Transaction
    @Query("SELECT * from categories")
    fun getCategoriesWithAttributes(): List<LocalCategoryWithLocalAttributeGroups>
}