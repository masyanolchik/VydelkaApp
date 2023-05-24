package com.hneu.vydelka.datasource.favorite

import com.hneu.core.datasource.favorite.LocalFavoriteDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.user.UserDao
import com.hneu.vydelka.localdatabase.user.UserProductFavoriteCrossRef
import com.hneu.vydelka.localdatabase.user.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomFavoriteDataSource @Inject constructor(
    private val userDao: UserDao,
) : LocalFavoriteDataSource {
    override fun addToFavorites(userId: Int, product: Product): Flow<Result<Product>> {
        return try {
            userDao.addProductFavoriteCrossRef(
                UserProductFavoriteCrossRef(
                    userId = userId,
                    productId = product.id
                )
            )
            flowOf(Result.Success(product))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getFavorites(userId: Int): Flow<Result<List<Product>>> {
        return try {
            flowOf(Result.Success(userDao.getUserWithFieldsById(userId).toDomain().favoriteProducts))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun removeFromFavorites(userId: Int, product: Product): Flow<Result<Product>> {
        return try {
            userDao.deleteProductFavoriteCrossRef(
                UserProductFavoriteCrossRef(
                    userId = userId,
                    productId = product.id
                )
            )
            flowOf(Result.Success(product))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }
}