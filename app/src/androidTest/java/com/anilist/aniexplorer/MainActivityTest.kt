package com.anilist.aniexplorer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenRendersMockSections() {
        waitForText("Now Showing")

        composeRule.onNodeWithText("Now Showing").assertIsDisplayed()
        composeRule.onNodeWithText("Popular").assertIsDisplayed()
        composeRule.onNodeWithText("Frieren: Beyond Journey's End").assertIsDisplayed()
    }

    @Test
    fun clickingAnimeNavigatesToDetailsScreen() {
        waitForText("Frieren: Beyond Journey's End")

        composeRule.onNodeWithText("Frieren: Beyond Journey's End").performClick()

        waitForText("Description")
        composeRule.onNodeWithText("Description").assertIsDisplayed()
        composeRule.onNodeWithText("Cast").assertIsDisplayed()
    }

    @Test
    fun detailsBackNavigationReturnsToHome() {
        waitForText("Frieren: Beyond Journey's End")

        composeRule.onNodeWithText("Frieren: Beyond Journey's End").performClick()
        waitForText("Description")

        composeRule.onNodeWithContentDescription("Back").performClick()

        waitForText("Now Showing")
        composeRule.onNodeWithText("Now Showing").assertIsDisplayed()
    }

    @Test
    fun disabledActionsShowSnackbarFeedback() {
        waitForText("Now Showing")

        composeRule.onNodeWithContentDescription("Menu").performClick()
        waitForText("Feature not implemented yet")
        composeRule.onNodeWithText("Feature not implemented yet").assertIsDisplayed()

        composeRule.onNodeWithText("Frieren: Beyond Journey's End").performClick()
        waitForText("Description")

        composeRule.onNodeWithContentDescription("Favorite").performClick()
        waitForText("Favorite not implemented yet")
        composeRule.onNodeWithText("Favorite not implemented yet").assertIsDisplayed()
    }

    private fun waitForText(text: String) {
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
    }
}
