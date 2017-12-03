package com.example.userslist.user;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.userslist.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UsersActivityTest {

    @Rule
    public ActivityTestRule<UsersActivity> mActivityTestRule = new ActivityTestRule<>(UsersActivity.class);

    @Test
    public void searchTest() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        withParent(allOf(withId(R.id.search_bar),
                                withParent(withId(R.id.search)))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("vinod"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_name), withText("Vinod Kamble"),
                        childAtPosition(
                                allOf(withId(R.id.message_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Vinod Kamble")));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        appCompatImageView3.perform(click());
    }

    @Test
    public void sortByInfoDescTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_sort), withContentDescription("Sort By"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.bottomsheet_layout),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.chkInfo),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomsheet_layout),
                                        2),
                                1),
                        isDisplayed()));
        toggleButton.check(matches(isDisplayed()));

        ViewInteraction toggleButton2 = onView(
                allOf(withId(R.id.chkInfo),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomsheet_layout),
                                        2),
                                1),
                        isDisplayed()));
        toggleButton2.check(matches(isDisplayed()));

        ViewInteraction toggleButton3 = onView(
                allOf(withId(R.id.chkInfo), isDisplayed()));
        toggleButton3.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txt_name), withText("Vinod Kamble"),
                        childAtPosition(
                                allOf(withId(R.id.message_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Vinod Kamble")));
    }

    @Test
    public void sortByPointDescTest() {

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_sort), withContentDescription("Sort By"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction toggleButton4 = onView(
                allOf(withId(R.id.chkPoint), isDisplayed()));
        toggleButton4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.txt_points), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("5")));


    }

    @Test
    public void sortByPointAscTest() {
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_sort), withContentDescription("Sort By"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction toggleButton5 = onView(
                allOf(withId(R.id.chkPoint), isDisplayed()));
        toggleButton5.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.txt_points), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("0")));

    }

    @Test
    public void sortBYLastActiveAscTest() {
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_sort), withContentDescription("Sort By"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction toggleButton6 = onView(
                allOf(withId(R.id.chkActive), isDisplayed()));
        toggleButton6.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.txt_lastActive), withText("2 days ago"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("2 days ago")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
