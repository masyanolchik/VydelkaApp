package com.hneu.vydelka.ui.feed.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.hneu.vydelka.R
import com.hneu.vydelka.ui.theme.VydelkaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.google.common.truth.Truth.assertThat

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class MediumBoxProductCardTest {
    @get:Rule
    internal val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var imageNode: SemanticsMatcher
    private lateinit var titleNode: SemanticsMatcher
    private lateinit var priceNode: SemanticsMatcher
    private lateinit var addToCartButtonNotPressedNode: SemanticsMatcher
    private lateinit var addToCartButtonPressedNode: SemanticsMatcher

    @Before
    fun setUp() {
        imageNode = hasContentDescriptionExactly(TEST_IMAGE_CONTENT_DESCRIPTION)
        titleNode = hasText(TEST_TITLE)
        priceNode = hasText(TEST_PRICE)
        addToCartButtonNotPressedNode =
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.add_to_cart_button_label)
            )
        addToCartButtonPressedNode =
            hasClickAction() and hasText(
                composeTestRule.activity.getString(R.string.add_to_cart_button_label_added)
            )
    }

    @Test
    fun mediumBoxProductCard_cardAppearance_notAddedToCart() {
        composeTestRule.setContent {
            VydelkaTheme {
                MediumBoxProductCard(
                    title = TEST_TITLE,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = { },
                    onCartAddedClicked = { },
                )
            }
        }

        composeTestRule.onNode(imageNode)
            .assertIsDisplayed()
        composeTestRule.onNode(titleNode)
            .assertIsDisplayed()
        composeTestRule.onNode(priceNode)
            .assertIsDisplayed()
        composeTestRule.onNode(addToCartButtonNotPressedNode)
            .assertIsDisplayed()
        composeTestRule.onNode(imageNode, useUnmergedTree = true)
            .onParent()
            .assertHasClickAction()
    }

    @Test
    fun mediumBoxProductCard_cardAppearance_addedToCart() {
        composeTestRule.setContent {
            VydelkaTheme {
                MediumBoxProductCard(
                    title = TEST_TITLE,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = { },
                    onCartAddedClicked = { },
                    isProductAddedToCart = true
                )
            }
        }

        composeTestRule.onNode(imageNode)
            .assertIsDisplayed()
        composeTestRule.onNode(titleNode)
            .assertIsDisplayed()
        composeTestRule.onNode(priceNode)
            .assertIsDisplayed()
        composeTestRule.onNode(addToCartButtonPressedNode)
            .assertIsDisplayed()
        composeTestRule.onNode(imageNode, useUnmergedTree = true)
            .onParent()
            .assertHasClickAction()
    }

    @Test
    fun mediumBoxProductCard_cardInteraction_notPressedButtonClick() {
        var counter = 0
        val onCardButtonClicked: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                MediumBoxProductCard(
                    title = TEST_TITLE,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = { },
                    onCartAddedClicked = onCardButtonClicked,
                )
            }
        }

        composeTestRule.onNode(addToCartButtonNotPressedNode)
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun mediumBoxProductCard_cardInteraction_pressedButtonClick() {
        var counter = 0
        val onCardButtonClicked: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                MediumBoxProductCard(
                    title = TEST_TITLE,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = { },
                    onCartAddedClicked = onCardButtonClicked,
                    isProductAddedToCart = true,
                )
            }
        }

        composeTestRule.onNode(addToCartButtonPressedNode)
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun mediumBoxProductCard_cardInteraction_cardClick() {
        var counter = 0
        val onCardClicked: () -> Unit = {
            counter = counter.inc()
        }
        composeTestRule.setContent {
            VydelkaTheme {
                MediumBoxProductCard(
                    title = TEST_TITLE,
                    price = TEST_PRICE,
                    imageSrc = TEST_IMAGE_SRC,
                    contentDescription = TEST_IMAGE_CONTENT_DESCRIPTION,
                    onCardClicked = onCardClicked,
                    onCartAddedClicked = {  },
                )
            }
        }

        composeTestRule.onNode(imageNode, useUnmergedTree = true)
            .onParent()
            .performClick()

        assertThat(counter).isEqualTo(1)
    }

    companion object {
        const val TEST_TITLE = "Test title"
        const val TEST_PRICE = "9232"
        const val TEST_IMAGE_SRC = "broken link"
        const val TEST_IMAGE_CONTENT_DESCRIPTION = "image content description"
    }
}