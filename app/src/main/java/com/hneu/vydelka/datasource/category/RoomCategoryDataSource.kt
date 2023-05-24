package com.hneu.vydelka.datasource.category

import com.hneu.core.datasource.category.LocalCategoryDataSource
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import com.hneu.vydelka.localdatabase.product.attribute.AttributeDao
import com.hneu.vydelka.localdatabase.product.attribute.fromDomain
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupAttributesCrossRef
import com.hneu.vydelka.localdatabase.product.attributegroup.AttributeGroupDao
import com.hneu.vydelka.localdatabase.product.attributegroup.fromDomain
import com.hneu.vydelka.localdatabase.product.category.CategoryAttributeGroupsCrossRef
import com.hneu.vydelka.localdatabase.product.category.CategoryDao
import com.hneu.vydelka.localdatabase.product.category.LocalCategoryWithLocalAttributeGroups
import com.hneu.vydelka.localdatabase.product.category.fromDomain
import com.hneu.vydelka.localdatabase.product.category.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomCategoryDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
    private val attributeGroupDao: AttributeGroupDao,
    private val attributeDao: AttributeDao,
): LocalCategoryDataSource{
    override fun saveCategories(categories: List<Category>): Flow<Result<List<Category>>> {
        return try {
            categories.forEach {
                if(it.parentCategory != null) {
                    saveCategory(it)
                } else {
                    categoryDao.addCategory(it.fromDomain())
                }
            }
            getCategories()
        } catch (e: Exception) {
            return flowOf(Result.Error(e))
        }
    }

    override fun getCategories(): Flow<Result<List<Category>>> {
        return try {
            val localCategoriesWithFields = categoryDao.getCategoriesWithAttributes()
            val withParentCat = localCategoriesWithFields.map { it.toDomainWithDao(categoryDao) }
            flowOf(Result.Success(withParentCat))
        } catch (e: Exception) {
            flowOf(Result.Error(e))
        }
    }

    private fun saveCategory(category: Category?) {
        try {
            if(category?.parentCategory != null) {
                saveCategory(category.parentCategory)
            }
            if(category != null) {
                val localCategory = category.fromDomain()
                categoryDao.addCategory(localCategory)
                category.attributeGroups.forEach { attrGroup ->
                    val localGroup = attrGroup.fromDomain()
                    attributeGroupDao.addAttributeGroup(localGroup)
                    attrGroup.attributes.forEach { attr ->
                        val localAttribute = attr.fromDomain()
                        attributeDao.addAttribute(localAttribute)
                        attributeGroupDao.addAttributeGroupAttributesCrossRef(
                            AttributeGroupAttributesCrossRef(
                                attributeGroupId = localGroup.attributeGroupId,
                                attributeId = localAttribute.attributeId
                            )
                        )
                    }
                    categoryDao.addCategoryAttributeGroupsCrossRef(
                        CategoryAttributeGroupsCrossRef(
                            categoryId = localCategory.categoryId,
                            attributeGroupId = attrGroup.id
                        )
                    )
            }
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