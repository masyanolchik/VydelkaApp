package com.hneu.vydelka.datasource.user

import com.hneu.core.datasource.order.LocalOrderDataSource
import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.datasource.profile.LocalProfileDataSource
import com.hneu.core.datasource.user.LocalUserDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import com.hneu.core.domain.user.User
import com.hneu.vydelka.localdatabase.user.UserDao
import com.hneu.vydelka.localdatabase.user.UserOrderHistoryCrossRef
import com.hneu.vydelka.localdatabase.user.UserProductFavoriteCrossRef
import com.hneu.vydelka.localdatabase.user.UserProductHistoryCrossRef
import com.hneu.vydelka.localdatabase.user.fromDomain
import com.hneu.vydelka.localdatabase.user.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomUserDataSource @Inject constructor(
    private val userDao: UserDao,
    private val productDataSource: LocalProductDataSource,
    private val orderDataSource: LocalOrderDataSource,
) : LocalUserDataSource, LocalProfileDataSource {
    override fun getByUsername(username: String): Flow<Result<User>> {
        return try {
            flowOf(Result.Success(userDao.getUserByUsername(username).toDomain()))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun saveUser(user: User): Flow<Result<User>> {
        return try {
            val localUser = user.fromDomain()
            userDao.addUser(localUser)
            user.favoriteProducts.forEach { product ->
                productDataSource.saveProduct(product)
                userDao.addProductFavoriteCrossRef(UserProductFavoriteCrossRef(user.id, product.id))
            }
            user.orderHistory.forEach { order ->
                orderDataSource.saveOrder(order, user)
                userDao.addUserOrderHistoryCrossRef(UserOrderHistoryCrossRef(
                    userId = localUser.userId,
                    orderId = order.id,
                ))
            }
            user.productHistory.forEach { product ->
                productDataSource.saveProduct(product)
                userDao.addProductHistoryCrossRef(UserProductHistoryCrossRef(
                    userId = localUser.userId,
                    productId = product.id,
                ))
            }
            flowOf(Result.Success(user))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getByUsernameAndPassword(username: String, password: String): Flow<Result<User>> {
        return try {
            if(userDao.isUserExists(username)) {
                val localUser = userDao.getUserByUsername(username)
                if(localUser.localUser.password == password) {
                    flowOf(Result.Success(localUser.toDomain()))
                } else {
                    flowOf(Result.Error(object: Exception(){
                        override val message: String
                            get() = "Password incorrect"
                    }))
                }
            } else {
                flowOf(Result.Error(object: Exception(){
                    override val message: String?
                        get() = "Username not found"
                }))
            }
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }
}