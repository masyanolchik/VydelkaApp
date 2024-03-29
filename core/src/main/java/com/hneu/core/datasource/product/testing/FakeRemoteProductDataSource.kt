package com.hneu.core.datasource.product.testing

import com.hneu.core.datasource.category.testing.FakeRemoteCategoryDataSource
import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.datasource.promo.testing.FakeRemotePromoDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal

class FakeRemoteProductDataSource : RemoteProductDataSource {
    override fun getProducts() = flowOf(Result.Success(FAKE_LIST))

    override fun getSortedProducts(sortOrderOrdinal: Int): Flow<Result<List<Product>>> {
        return flowOf(Result.Success(
            when(sortOrderOrdinal) {
                RemoteProductDataSource.LOW_PRICE_SORT -> FAKE_LIST.sortedBy { it.price }
                RemoteProductDataSource.HIGH_PRICE_SORT -> FAKE_LIST.sortedByDescending { it.price }
                else -> emptyList()
            }
          )
        )
    }

    override fun getTopProducts() = flowOf(Result.Success(listOf(FAKE_LIST[0],FAKE_LIST[2])))

    override fun searchByQuery(searchQuery: String): Flow<Result<List<Product>>> {
        val setOfProducts = mutableSetOf<Product>()
        val sb = StringBuilder(searchQuery)
        val searchQueries = sb.split(" ")
        val queryWithProduct = FAKE_LIST.map { Pair(it, buildString { it.getSearchTags().forEach(::append) }) }
        searchQueries.forEach {substrToFind ->
            queryWithProduct.forEach {
                if(it.second.contains(substrToFind, ignoreCase = true)) {
                    setOfProducts.add(it.first)
                }
            }
        }
        return flowOf(Result.Success(setOfProducts.toList()))
    }

    companion object {
        val FAKE_LIST = listOf(
            Product(
                id = 0,
                name = "пилосос смасмуг",
                price = BigDecimal(3549),
                status = "В наявності",
                warrantyLengthMonth = 60,
                returnExchangeLength = 14,
                category = FakeRemoteCategoryDataSource.FAKE_LIST[2],
                description="Да-да, чоткій пилосос",
                titleImageSrc = "https://images.samsung.com/is/image/samsung/p6pim/ua/vc07m2110sb-uk/gallery/ua-vc2100m-canister-with-anti-tangle-turbine-vc07m2110sb-uk-423746708?\$1300_1038_PNG\$",
                images = listOf(
                    "https://images.samsung.com/is/image/samsung/p6pim/ua/vc07m2110sb-uk/gallery/ua-vc2100m-canister-with-anti-tangle-turbine-vc07m2110sb-uk-423746708?\$1300_1038_PNG\$",
                    "https://images.samsung.com/is/image/samsung/p6pim/ua/vc07m2110sb-uk/gallery/ua-vc2100m-canister-with-anti-tangle-turbine-vc07m2110sb-uk-423746683?\$684_547_PNG\$",
                    "https://images.samsung.com/is/image/samsung/p6pim/ua/vc07m2110sb-uk/gallery/ua-vc2100m-canister-with-anti-tangle-turbine-vc07m2110sb-uk-423746684?\$684_547_PNG\$",
                    "https://images.samsung.com/is/image/samsung/p6pim/ua/vc07m2110sb-uk/gallery/ua-vc2100m-canister-with-anti-tangle-turbine-vc07m2110sb-uk-423746685?\$684_547_PNG\$",
                ),
                attributes = mutableMapOf(
                    Pair(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0], FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0].attributes[0]),
                    Pair(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[1],  FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[1].attributes[0]),
                ),
                additionalTags = FakeRemotePromoDataSource.FAKE_LIST[0].tags.toSet()
            ),
            Product(
                id = 1,
                name = "пилосос глод стар",
                price = BigDecimal(949),
                status = "Під замовлення",
                warrantyLengthMonth = 60,
                returnExchangeLength = 14,
                category = FakeRemoteCategoryDataSource.FAKE_LIST[2],
                description="ну такоє, но покатіт",
                titleImageSrc = "https://www.dragonelectronics.mu/assets/img/sa/VC5420NHT/VC5420NHT_01.webp",
                images = listOf(
                    "https://www.dragonelectronics.mu/assets/img/sa/VC5420NHT/VC5420NHT_01.webp",
                    "https://www.dragonelectronics.mu/assets/img/sa/VC5420NHT/VC5420NHT_02.webp",
                    "https://www.dragonelectronics.mu/assets/img/sa/VC5420NHT/VC5420NHT_03.webp",
                    "https://www.dragonelectronics.mu/assets/img/sa/VC5420NHT/VC5420NHT_04.webp",
                ),
                attributes = mutableMapOf(
                    Pair(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0], FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0].attributes[1]),
                    Pair(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[1],  FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[1].attributes[1]),
                    ),
            ),
            Product(
                id = 2,
                name = "самартофон",
                price = BigDecimal(2949),
                status = "В наявності",
                warrantyLengthMonth = 24,
                returnExchangeLength = 14,
                category = FakeRemoteCategoryDataSource.FAKE_LIST[4],
                description="ну такоє, но покатіт",
                titleImageSrc = "https://content.rozetka.com.ua/goods/images/big/263855363.jpg",
                images = listOf(
                    "https://content.rozetka.com.ua/goods/images/big/263855363.jpg",
                    "https://content.rozetka.com.ua/goods/images/big/263855364.jpg",
                    "https://content.rozetka.com.ua/goods/images/big/263855365.jpg",
                    "https://content2.rozetka.com.ua/goods/images/big/263855366.jpg",
                ),
                attributes = mutableMapOf(Pair(FakeRemoteCategoryDataSource.FAKE_LIST[4].attributeGroups[0], FakeRemoteCategoryDataSource.FAKE_LIST[4].attributeGroups[0].attributes[0])),
            ),
        )
    }
}