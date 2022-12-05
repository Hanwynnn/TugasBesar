package com.willychia.TugasBesar


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EditFilmActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(EditFilmActivity::class.java)

    @Test
    fun editFilmActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(500)

        val kTLoadingButton = onView(
            allOf(
                withId(R.id.btn_save),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        kTLoadingButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_judul),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_judul),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(
            scrollTo(),
            replaceText("Sapiderman: Jauh Dari Rumai"),
            closeSoftKeyboard()
        )

        val kTLoadingButton2 = onView(
            allOf(
                withId(R.id.btn_save),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        kTLoadingButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_genre),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_genre),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("Gelak Tawa"), closeSoftKeyboard())

        val kTLoadingButton3 = onView(
            allOf(
                withId(R.id.btn_save),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        kTLoadingButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_rating),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_rating),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("0.0"), closeSoftKeyboard())

        val kTLoadingButton4 = onView(
            allOf(
                withId(R.id.btn_save),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        kTLoadingButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_rating), withText("0.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_rating),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(scrollTo(), click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_rating), withText("0.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_rating),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("4.0"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.et_rating), withText("4.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_rating),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(closeSoftKeyboard())

        val kTLoadingButton5 = onView(
            allOf(
                withId(R.id.btn_save),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_button),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        kTLoadingButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction?{
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for "+delay+"milliseconds"
            }

            override fun perform(uiController: UiController, view: View){
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
