package com.hneu.vydelka.ui.feed.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.theme.VydelkaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class ProductCardTest {
    @get:Rule
    internal val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var imageNode: SemanticsMatcher
    private lateinit var titleNode: SemanticsMatcher
    private lateinit var availabilityNode: SemanticsMatcher
    private lateinit var priceNode: SemanticsMatcher
    private lateinit var addToCartButtonNode: SemanticsMatcher
    private lateinit var addToFavoritesButtonNode: SemanticsMatcher
    private lateinit var deleteFromCartButton: SemanticsMatcher
    private lateinit var decreaseProductAmountButton: SemanticsMatcher
    private lateinit var increaseProductAmountButton: SemanticsMatcher

    @Before
    fun setUp() {
        imageNode = hasContentDescriptionExactly(TEST_IMAGE_CONTENT_DESCRIPTION)
        titleNode = hasText(TEST_TITLE)
        availabilityNode = hasText(TEST_AVAILABILITY)
        priceNode = hasText(TEST_PRICE)
        addToCartButtonNode =
            hasContentDescription(
                composeTestRule.activity.getString(R.string.add_to_cart_button_label)
            )
        addToFavoritesButtonNode =
            hasContentDescription(
                composeTestRule.activity.getString(R.string.add_to_favorites_button_label)
            )
        deleteFromCartButton =
            hasContentDescription(
                composeTestRule.activity.getString(R.string.bottom_sheet_remove_product_from_cart)
            )
        decreaseProductAmountButton =
            hasContentDescription(
                composeTestRule.activity.getString(R.string.bottom_sheet_reduce_product_content_description)
            ) and hasClickAction()
        increaseProductAmountButton =
            hasContentDescription(
                composeTestRule.activity.getString(R.string.bottom_sheet_add_product_content_description)
            ) and hasClickAction()
    }

    @Test
    fun productCard_cardAppearance() {
        composeTestRule.setContent {
            VydelkaTheme {
                ProductCard(
                    title = TEST_TITLE,
                    availability = TEST_AVAILABILITY,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                )
            }
        }

        composeTestRule.onNode(imageNode)
            .assertIsDisplayed()
        composeTestRule.onNode(titleNode)
            .assertIsDisplayed()
        composeTestRule.onNode(priceNode)
            .assertIsDisplayed()
        composeTestRule.onNode(addToCartButtonNode)
            .assertIsDisplayed()
            .onParent()
            .assertHasClickAction()
        composeTestRule.onNode(addToFavoritesButtonNode)
            .assertIsDisplayed()
            .onParent()
            .assertHasClickAction()
        composeTestRule.onNode(imageNode, useUnmergedTree = true)
            .onParent()
            .assertHasClickAction()
    }

    @Test
    fun productCard_cardInteraction_onCardClicked() {
        var counter = 0
        val onCardClickedListener: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                ProductCard(
                    title = TEST_TITLE,
                    availability = TEST_AVAILABILITY,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = onCardClickedListener
                )
            }
        }

        composeTestRule.onNode(imageNode, useUnmergedTree = true)
            .onParent()
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun productCard_cardInteraction_onFavoriteButtonClicked() {
        var counter = 0
        val onFavoriteButtonClickedListener: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                ProductCard(
                    title = TEST_TITLE,
                    availability = TEST_AVAILABILITY,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onFavoriteButtonClicked = onFavoriteButtonClickedListener
                )
            }
        }

        composeTestRule.onNode(addToFavoritesButtonNode, useUnmergedTree = true)
            .onParent()
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun productCard_cardInteraction_onCartButtonClicked() {
        var counter = 0
        val onCartButtonClickedListener: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                ProductCard(
                    title = TEST_TITLE,
                    availability = TEST_AVAILABILITY,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCartButtonClicked = onCartButtonClickedListener
                )
            }
        }

        composeTestRule.onNode(addToCartButtonNode, useUnmergedTree = true)
            .onParent()
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun cartProductCard_cardAppearance() {
        composeTestRule.setContent {
            VydelkaTheme {
                CartProductCard(
                    title = TEST_TITLE,
                    availability = TEST_AVAILABILITY,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                )
            }
        }

        composeTestRule.onNode(imageNode)
            .assertIsDisplayed()
        composeTestRule.onNode(titleNode)
            .assertIsDisplayed()
        composeTestRule.onNode(priceNode)
            .assertIsDisplayed()
        composeTestRule.onNode(availabilityNode)
            .assertIsDisplayed()
        composeTestRule.onNode(deleteFromCartButton)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNode(decreaseProductAmountButton)
            .assertIsDisplayed()
        composeTestRule.onNode(increaseProductAmountButton)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("0")
            .assertIsDisplayed()
    }

    companion object {
        const val TEST_TITLE = "Test title"
        const val TEST_AVAILABILITY = "available"
        const val TEST_PRICE = "9232"
        const val TEST_IMAGE_SRC = "broken link"
        const val TEST_IMAGE_CONTENT_DESCRIPTION = "image content description"
    }
}