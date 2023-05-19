package com.hneu.core.datasource.product.testing

import com.hneu.core.datasource.category.testing.FakeRemoteCategoryDataSource
import com.hneu.core.datasource.product.RemoteProductDataSource
import com.hneu.core.domain.product.Product
import com.hneu.core.domain.request.Result
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal

class FakeRemoteProductDataSource : RemoteProductDataSource {
    override fun getProducts() = flowOf(Result.Success(FAKE_LIST))

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
                attributes = setOf(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0].attributes[0]),
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
                attributes = setOf(FakeRemoteCategoryDataSource.FAKE_LIST[2].attributeGroups[0].attributes[1]),
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
                attributes = setOf(FakeRemoteCategoryDataSource.FAKE_LIST[4].attributeGroups[0].attributes[0]),
            ),
        )
    }
}