package com.hneu.vydelka.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hneu.vydelka.localdatabase.order.LocalOrder
import com.hneu.vydelka.localdatabase.order.OrderDao
import com.hneu.vydelka.localdatabase.order.cart.CartDao
import com.hneu.vydelka.localdatabase.order.cart.CartOrderedProductsCrossRef
import com.hneu.vydelka.localdatabase.order.cart.LocalCart
import com.hneu.vydelka.localdatabase.order.orderedproduct.LocalOrderedProduct
import com.hneu.vydelka.localdatabase.order.orderedproduct.OrderedProductDao
import com.hneu.vydelka.localdatabase.product.LocalProduct
import com.hneu.vydelka.localdatabase.product.LocalProductWithAdditionalFields
import com.hneu.vydelka.localdatabase.product.ProductAdditionalImagesCrossRef
import com.hneu.vydelka.localdatabase.product.ProductAttributesCrossRef
import com.hneu.vydelka.localdatabase.product.ProductDao
import com.hneu.vydelka.localdatabase.product.ProductTagsCrossRef
import com.hneu.vydelka.localdatabase.product.additionalimage.AdditionalImageDao
import com.hneu.vydelka.localdatabase.product.additionalimage.LocalAdditionalImage
import com.hneu.vydelka.localdatabase.product.attribute.AttributeDao
import com.hneu.vydelka.localdatabase.product.attribute.LocalAttribute
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupAttributesCrossRef
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupDao
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroup
import com.hneu.vydelka.localdatabase.product.attributegroup.LocalAttributeGroupWithAllowedValues
import com.hneu.vydelka.localdatabase.product.category.CategoryAttributeGroupsCrossRef
import com.hneu.vydelka.localdatabase.product.category.CategoryDao
import com.hneu.vydelka.localdatabase.product.category.LocalCategory
import com.hneu.vydelka.localdatabase.product.category.LocalCategoryWithLocalAttributeGroups
import com.hneu.vydelka.localdatabase.product.tag.LocalTag
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import com.hneu.vydelka.localdatabase.promo.LocalPromo
import com.hneu.vydelka.localdatabase.promo.PromoDao
import com.hneu.vydelka.localdatabase.promo.PromoTagsCrossRef
import com.hneu.vydelka.localdatabase.user.LocalUser
import com.hneu.vydelka.localdatabase.user.UserDao
import com.hneu.vydelka.localdatabase.user.UserOrderHistoryCrossRef
import com.hneu.vydelka.localdatabase.user.UserProductFavoriteCrossRef
import com.hneu.vydelka.localdatabase.user.UserProductHistoryCrossRef

@Database(
    entities = [
        LocalUser::class,
        UserOrderHistoryCrossRef::class,
        UserProductFavoriteCrossRef::class,
        UserProductHistoryCrossRef::class,
        LocalOrder::class,
        LocalCart::class,
        CartOrderedProductsCrossRef::class,
        LocalOrderedProduct::class,
        LocalAdditionalImage::class,
        LocalAttribute::class,
        LocalAttributeGroup::class,
        AttributeGroupAttributesCrossRef::class,
        CategoryAttributeGroupsCrossRef::class,
        LocalCategory::class,
        LocalTag::class,
        LocalPromo::class,
        PromoTagsCrossRef::class,
        LocalProduct::class,
        ProductAdditionalImagesCrossRef::class,
        ProductAttributesCrossRef::class,
        ProductTagsCrossRef::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun cartDao(): CartDao
    abstract fun orderedProductDao(): OrderedProductDao
    abstract fun additionalImageDao(): AdditionalImageDao
    abstract fun attributeDao(): AttributeDao

    abstract fun attributeGroupDao(): AttributeGroupDao

    abstract fun categoryDao(): CategoryDao

    abstract fun productDao(): ProductDao

    abstract fun tagDao(): TagDao
    abstract fun promoDao(): PromoDao

    companion object {
        const val DB_NAME="localDataSource"
    }
}