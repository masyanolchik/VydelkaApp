package com.hneu.core.datasource.category.testing

import com.hneu.core.datasource.category.RemoteCategoryDataSource
import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteCategoryDataSource: RemoteCategoryDataSource {
    override fun getCategories(): Flow<Result<List<Category>>> = flowOf(Result.Success(FAKE_LIST))

    companion object {
        val FAKE_ATTRIBUTES_FOR_VACUUM_CLEANERS = listOf(
            Attribute(
                id = 0,
                name = "Samsung",
            ),
            Attribute(
                id = 1,
                name = "LG",
            ),
            Attribute(
                id = 2,
                name = "BOSCH",
            ),
        )
        val FAKE_ATTRIBUTE_GROUPS_VACUUM_CLEANERS = listOf(
            AttributeGroup(
                id = 0,
                name = "Виробник",
                attributes = FAKE_ATTRIBUTES_FOR_VACUUM_CLEANERS,
            )
        )

        val FAKE_ATTRIBUTES_MOBILE_PHONES = listOf(
            Attribute(
                id = 3,
                name = "Samsung",
            ),
            Attribute(
                id = 4,
                name = "Apple",
            ),
            Attribute(
                id = 5,
                name = "Google",
            ),
        )
        val FAKE_ATTRIBUTE_GROUPS_MOBILE_PHONES = listOf(
            AttributeGroup(
                id = 1,
                name = "Виробник",
                attributes = FAKE_ATTRIBUTES_MOBILE_PHONES,
            )
        )
        val FAKE_LIST = listOf(
            Category(
                id = 0,
                name = "Побутова техніка",
                attributeGroups = mutableListOf(),
            ),
            Category(
                id = 1,
                name = "Техніка для дому",
                attributeGroups = mutableListOf(),
            ),
            Category(
                id = 2,
                name = "Пилососи",
                attributeGroups = mutableListOf(),
            ),
            Category(
                id = 3,
                name = "Мобільний зв'язок",
                attributeGroups = mutableListOf(),
            ),
            Category(
                id = 4,
                name = "Мобільні телефони",
                attributeGroups = mutableListOf(),
            ),
        ).also {
            it[1].parentCategory = it[0]
            it[2].parentCategory = it[1]
            (it[2].attributeGroups as MutableList).addAll(FAKE_ATTRIBUTE_GROUPS_VACUUM_CLEANERS)
            it[4].parentCategory = it[3]
            (it[4].attributeGroups as MutableList).addAll(FAKE_ATTRIBUTE_GROUPS_MOBILE_PHONES)
        }
    }
}