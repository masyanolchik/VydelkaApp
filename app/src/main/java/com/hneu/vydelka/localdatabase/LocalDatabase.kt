package com.hneu.vydelka.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hneu.vydelka.localdatabase.product.tag.LocalTag
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import com.hneu.vydelka.localdatabase.promo.LocalPromo
import com.hneu.vydelka.localdatabase.promo.PromoDao
import com.hneu.vydelka.localdatabase.promo.PromoTagsCrossRef

@Database(
    entities = [
        LocalTag::class,
        LocalPromo::class,
        PromoTagsCrossRef::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao
    abstract fun promoDao(): PromoDao

    companion object {
        const val DB_NAME="localDataSource"
    }
}