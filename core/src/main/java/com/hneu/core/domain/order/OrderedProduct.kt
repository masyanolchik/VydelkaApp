package com.hneu.core.domain.order

import com.hneu.core.domain.product.Product

data class OrderedProduct(
    val id: Int,
    val product: Product,
    val quantity: Int,
)
