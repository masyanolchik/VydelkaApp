package com.hneu.vydelka.datasource.product

import com.hneu.core.datasource.product.LocalProductDataSource
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.product.Tag
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.localdatabase.product.ProductAdditionalImagesCrossRef
import com.hneu.vydelka.localdatabase.product.ProductAttributesCrossRef
import com.hneu.vydelka.localdatabase.product.ProductDao
import com.hneu.vydelka.localdatabase.product.ProductTagsCrossRef
import com.hneu.vydelka.localdatabase.product.additionalimage.AdditionalImageDao
import com.hneu.vydelka.localdatabase.product.additionalimage.fromDomainToLocalAdditionalImage
import com.hneu.vydelka.localdatabase.product.attribute.AttributeDao
import com.hneu.vydelka.localdatabase.product.attribute.fromDomain
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupAttributesCrossRef
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupDao
import com.hneu.vydelka.localdatabase.product.attributegroup.fromDomain
import com.hneu.vydelka.localdatabase.product.attributegroup.toDomain
import com.hneu.vydelka.localdatabase.product.category.CategoryAttributeGroupsCrossRef
import com.hneu.vydelka.localdatabase.product.category.CategoryDao
import com.hneu.vydelka.localdatabase.product.category.LocalCategoryWithLocalAttributeGroups
import com.hneu.vydelka.localdatabase.product.category.fromDomain
import com.hneu.vydelka.localdatabase.product.category.toDomain
import com.hneu.vydelka.localdatabase.product.fromDomain
import com.hneu.vydelka.localdatabase.product.tag.TagDao
import com.hneu.vydelka.localdatabase.product.tag.toLocal
import com.hneu.vydelka.localdatabase.product.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomProductDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val attributeGroupDao: AttributeGroupDao,
    private val attributeDao: AttributeDao,
    private val additionalImageDao: AdditionalImageDao,
    private val tagDao: TagDao,
): LocalProductDataSource {
    override fun saveProduct(product: Product): Flow<Result<Product>> {
        return try {
            saveProductInternal(product)
            getProductById(product.id)
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun saveProducts(products: List<Product>): Flow<Result<List<Product>>> {
        return try {
            products.forEach {
                saveProductInternal(it)
            }
            val productsList = mutableListOf<Product>()
            products.forEach {
                val productLocal = productDao.getProduct(it.id)
                val attributeGroups = categoryDao.getAttributeGroupsForCategory(productLocal.localProduct.categoryId).map {
                    attributeGroupDao.getAttributeGroupWithAllowedValues(it.attributeGroupId).toDomain()
                }
                productsList.add(productDao.getProduct(it.id).toDomain(attributeGroups))
            }
            flowOf(Result.Success(productsList))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getProductById(productId: Int): Flow<Result<Product>> {
        return try {
            val productLocal = productDao.getProduct(productId)
            val attributeGroups = categoryDao.getAttributeGroupsForCategory(productLocal.localProduct.categoryId).map {
                attributeGroupDao.getAttributeGroupWithAllowedValues(it.attributeGroupId).toDomain()
            }
            flowOf(Result.Success(productDao.getProduct(productId).toDomain(attributeGroups)))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getProducts(): Flow<Result<List<Product>>> {
        return try {
            flowOf(Result.Success(productDao.getAllProducts().map { it.toDomain() }))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getProductsByCategoryId(categoryId: Int): Flow<Result<List<Product>>> {
        return try {
            val m = attributeDao.getAttributes()
            val k = attributeGroupDao.getAttributes()
            val attributeGroups = categoryDao.getAttributeGroupsForCategory(categoryId).map {
                attributeGroupDao.getAttributeGroupWithAllowedValues(it.attributeGroupId).toDomain()
            }
            flowOf(Result.Success(productDao.getProductByCategoryId(categoryId).map { it.toDomain(attributeGroups = attributeGroups) }))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    override fun getProductsByTags(tags: List<Tag>): Flow<Result<List<Product>>> {
        return try {
            flowOf(
                Result.Success(productDao
                .getAllProducts().map { it.toDomain() }
                .filter { product ->
                    product
                        .getSearchTags()
                        .containsAll(tags.map { it.name })
                })
            )
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    private fun saveProductInternal(product: Product) {
        try {
            productDao.insertProduct(product.fromDomain())
            product.images.forEach {
                additionalImageDao.addAdditionalImage(it.fromDomainToLocalAdditionalImage()).toInt()
                productDao.insertProductAdditionalImagesCrossRef(
                    ProductAdditionalImagesCrossRef(
                        product.id,
                        additionalImageDao.addAdditionalImage(it.fromDomainToLocalAdditionalImage()).toInt()
                    )
                )
            }
            product.attributes.values.forEach { attr ->
                attributeDao.addAttribute(attr.fromDomain())
                productDao.insertProductAttributesCrossRef(
                    ProductAttributesCrossRef(
                        productId = product.id,
                        attributeId = attr.id,
                    )
                )
            }
            product.additionalTags.forEach { tag ->
                tagDao.addTag(tag.toLocal())
                productDao.insertProductTagsCrossRef(
                    ProductTagsCrossRef(
                        productId = product.id,
                        tagId = tag.id
                    )
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }



    private fun LocalCategoryWithLocalAttributeGroups.toDomainWithDao(categoryDao: CategoryDao): Category {
        return if(localCategory.parentCategoryId != null) {
            val parentLocalCategoryWithLocalAttributeGroups = categoryDao.getCategoryByIdWithAttributes(localCategory.parentCategoryId)
            this.toDomain(parentLocalCategoryWithLocalAttributeGroups.toDomainWithDao(categoryDao))
        } else {
            this.toDomain(null)
        }
    }
}