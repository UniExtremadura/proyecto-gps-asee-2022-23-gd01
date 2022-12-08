package es.unex.infinitetime.EspressoTests.CU15;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.infinitetime.MainActivity;
import es.unex.infinitetime.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditUserEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void editarUsuarioTest() {
        ViewInteraction materialButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.btnRegister), withText("Registrarse"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_main),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.textEdit_username_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_username_register),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("CU15"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textEdit_email_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_email_register),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("CU15@unex.es"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.textEdit_password_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_password_register),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_register), withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_main),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.textEdit_username_user), withText("CU15"),
                        withParent(withParent(withId(R.id.txt_username_user))),
                        isDisplayed()));
        editText.check(matches(withText("CU15")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.textEdit_email_user), withText("CU15@unex.es"),
                        withParent(withParent(withId(R.id.txt_email_user))),
                        isDisplayed()));
        editText2.check(matches(withText("CU15@unex.es")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.textEdit_password_user), withText("12345"),
                        withParent(withParent(withId(R.id.txt_password_user))),
                        isDisplayed()));
        editText3.check(matches(withText("12345")));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.textEdit_username_user), withText("CU15"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_username_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("CU15edit"));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.textEdit_username_user), withText("CU15edit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_username_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.textEdit_email_user), withText("CU15@unex.es"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_email_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("CU15edit@unex.es"));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.textEdit_email_user), withText("CU15edit@unex.es"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_email_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.textEdit_password_user), withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_password_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("123456"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.textEdit_password_user), withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_password_user),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_user), withText("Editar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.favorite),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.textEdit_username_user), withText("CU15edit"),
                        withParent(withParent(withId(R.id.txt_username_user))),
                        isDisplayed()));
        editText4.check(matches(withText("CU15edit")));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.textEdit_email_user), withText("CU15edit@unex.es"),
                        withParent(withParent(withId(R.id.txt_email_user))),
                        isDisplayed()));
        editText5.check(matches(withText("CU15edit@unex.es")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.textEdit_password_user), withText("123456"),
                        withParent(withParent(withId(R.id.txt_password_user))),
                        isDisplayed()));
        editText6.check(matches(withText("123456")));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btn_delete_user), withText("ELIMINAR"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        materialButton5.perform(click());
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
    @BeforeClass
    public static void beforeClass() {
        InstrumentationRegistry.getTargetContext().deleteDatabase("infinite_database");
    }
}
