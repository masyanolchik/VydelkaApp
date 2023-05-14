package com.hneu.core.datasource.category.testing

import com.hneu.core.datasource.category.RemoteCategoryDataSource
import com.hneu.core.domain.product.Attribute
import com.hneu.core.domain.product.AttributeGroup
import com.hneu.core.domain.product.Category
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteCategoryDataSource: RemoteCategoryDataSource {
    override fun getCategories(): Flow<Result> = flowOf(Result.Success(FAKE_LIST))

    companion object {
        val FAKE_ATTRIBUTES_FOR_VACUUM_CLEANERS = listOf(
            Attribute(
                id = 0,
                name = "Samsung",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
            Attribute(
                id = 1,
                name = "LG",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
            Attribute(
                id = 2,
                name = "BOSCH",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
        )
        val FAKE_ATTRIBUTE_GROUPS_VACUUM_CLEANERS = listOf(
            AttributeGroup(
                id = 0,
                name = "Виробник",
                attributes = FAKE_ATTRIBUTES_FOR_VACUUM_CLEANERS,
                relatedCategory = Category("",attributeGroup = emptyList())
            ).also { attrGrp ->
                FAKE_ATTRIBUTES_FOR_VACUUM_CLEANERS.forEach { attr ->
                    attr.attributeGroup = attrGrp
                }
            }
        )

        val FAKE_ATTRIBUTES_MOBILE_PHONES = listOf(
            Attribute(
                id = 3,
                name = "Samsung",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
            Attribute(
                id = 4,
                name = "Apple",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
            Attribute(
                id = 5,
                name = "Google",
                attributeGroup = AttributeGroup(-1,"",emptyList(),Category("",attributeGroup = emptyList()))
            ),
        )
        val FAKE_ATTRIBUTE_GROUPS_MOBILE_PHONES = listOf(
            AttributeGroup(
                id = 1,
                name = "Виробник",
                attributes = FAKE_ATTRIBUTES_MOBILE_PHONES,
                relatedCategory = Category("",attributeGroup = emptyList())
            ).also { attrGrp ->
                FAKE_ATTRIBUTES_MOBILE_PHONES.forEach { attr ->
                    attr.attributeGroup = attrGrp
                }
            }
        )
        val FAKE_LIST = listOf(
            Category(
                id = 0,
                name = "Побутова техніка",
                attributeGroup = mutableListOf(),
            ),
            Category(
                id = 1,
                name = "Техніка для дому",
                attributeGroup = mutableListOf(),
            ),
            Category(
                id = 2,
                name = "Пилососи",
                attributeGroup = mutableListOf(),
            ),
            Category(
                id = 3,
                name = "Мобільний зв'язок",
                attributeGroup = mutableListOf(),
            ),
            Category(
                id = 4,
                name = "Мобільні телефони",
                attributeGroup = mutableListOf(),
            ),
        ).also {
            it[1].parentCategory = it[0]
            it[2].parentCategory = it[1]
            (it[2].attributeGroup as MutableList).addAll(FAKE_ATTRIBUTE_GROUPS_VACUUM_CLEANERS.also { attributeGroups ->
                attributeGroups[0].relatedCategory = it[2]
            })
            it[4].parentCategory = it[3]
            (it[4].attributeGroup as MutableList).addAll(FAKE_ATTRIBUTE_GROUPS_MOBILE_PHONES.also { attributeGroups ->
                attributeGroups[0].relatedCategory = it[4]
            })
        }
    }
}