package com.hneu.core.domain.order

import com.hneu.core.domain.user.User
import com.hneu.core.domain.product.Product

class Cart(
    val id: Int,
    val optionalUserId: Int?,
    val orderedProducts: MutableList<OrderedProduct>
) {
    fun addProductToCart(product: Product): Boolean {
        val productsInCart = orderedProducts.map{ it.product }
        return if(productsInCart.contains(product)) {
            false
        } else {
            orderedProducts.add(OrderedProduct(orderedProducts.size, product, 1))
        }
    }

    fun changeProductQuantityInCart(product: Product, desiredQuantity: Int) : Boolean {
        var productQuantityChanged = false
        orderedProducts.find { it.product.id == product.id }?.let {
            if(desiredQuantity != 0) {
                orderedProducts[orderedProducts.indexOf(it)] = OrderedProduct(0, product, desiredQuantity)
                productQuantityChanged = true
            }
        }
        return productQuantityChanged;
    }

    fun deleteProductFromCart(product: Product): Boolean {
        return orderedProducts.remove(orderedProducts.find{ it.product.id == product.id })
    }
}
