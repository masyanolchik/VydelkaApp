package com.hneu.vydelka.datasource.promo

import com.hneu.core.datasource.promo.LocalPromoDataSource
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.promo.Promo
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import com.hneu.vydelka.localdatabase.product.tag.toLocal
import com.hneu.vydelka.localdatabase.promo.PromoDao
import com.hneu.vydelka.localdatabase.promo.fromDomain
import com.hneu.vydelka.localdatabase.promo.getTagsCrossRef
import com.hneu.vydelka.localdatabase.promo.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomPromoDataSource @Inject constructor(
    private val tagDao: TagDao,
    private val promoDao: PromoDao,
) : LocalPromoDataSource {
    override fun savePromos(promos: List<Promo>): Flow<Result<List<Promo>>> {
        try {
            promoDao.nukeTable()
            val localPromos = promos.map {promo ->
                Pair(promo.fromDomain(), promo.getTagsCrossRef(promo.id))
            }
            val tagSet = mutableSetOf<Tag>()
            promos.forEach { tagSet.addAll(it.tags) }
            tagSet.forEach{ tagDao.addTag(it.toLocal()) }
            localPromos.forEach {
                promoDao.addPromo(it.first)
                it.second.forEach { promoTagsCrossRef ->
                    promoDao.addPromoTagsCrossRef(promoTagsCrossRef)
                }
            }
            return getPromos()

        } catch(e: Exception) {
           return flowOf(Result.Error(e))
        }
    }

    override fun getPromos(): Flow<Result<List<Promo>>> {
        return try{
            val localPromosWithTags = promoDao.getPromosWithTags()
            flowOf(Result.Success(localPromosWithTags.map { it.toDomain() }))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }
}