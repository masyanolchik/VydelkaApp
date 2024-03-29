package com.hneu.core.domain.product

import java.math.BigDecimal

data class Product(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val status: String,
    val warrantyLengthMonth: Int,
    val returnExchangeLength: Int,
    val category: Category,
    val description: String,
    val titleImageSrc: String,
    val images: List<String>,
    val attributes: Map<AttributeGroup, Attribute>,
    val additionalTags: Set<Tag> = emptySet()
) {
    fun getSearchTags() : List<String> {
        return listOf(name, price.toString(), category.name) + additionalTags.map{it.name}.toList()
    }

    fun matchesFilterCriteria(filter: FilterModel) : Boolean {
        if(price in filter.priceRangeStart..filter.priceRangeEnd) {
            var matchesFilter = true
            when {
                attributes.keys.containsAll(filter.selectedAttributes.keys) -> {
                    filter.selectedAttributes.keys.forEach {
                        matchesFilter = filter.selectedAttributes[it]?.contains(attributes[it]) ?: false
                        if(!matchesFilter) {
                            return matchesFilter
                        }
                    }
                }
                else -> { matchesFilter = filter.selectedAttributes.keys.isEmpty() }
            }
            return matchesFilter
        } else {
            return false
        }
    }
}
