package com.hneu.vydelka.localdatabase.promo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PromoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPromo(localPromo: LocalPromo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPromoTagsCrossRef(promoTagsCrossRef: PromoTagsCrossRef): Long

    @Update
    fun updatePromo(localPromo: LocalPromo)

    @Delete
    fun deletePromo(localPromo: LocalPromo)

    @Query("SELECT * from promos WHERE promoId=:promoId")
    fun getPromoById(promoId: Int): LocalPromo

    @Transaction
    @Query("SELECT * from promos WHERE promoId=:promoId")
    fun getPromoWithTags(promoId: Int): LocalPromoWithTags

    @Transaction
    @Query("SELECT * FROM promos")
    fun getPromosWithTags(): List<LocalPromoWithTags>

    @Query("DELETE FROM promos")
    fun nukeTable()
}