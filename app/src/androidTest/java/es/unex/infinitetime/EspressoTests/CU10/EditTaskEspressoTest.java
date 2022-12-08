package es.unex.infinitetime.EspressoTests.CU10;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static es.unex.infinitetime.utils.Delay.waitFor;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.DataInteraction;
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
public class EditTaskEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void editarTareaTest() {
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
        textInputEditText.perform(replaceText("CU10"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textEdit_email_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_email_register),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("CU10@unex.es"), closeSoftKeyboard());

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
                allOf(withId(R.id.projects),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.AddProject), withContentDescription("Boton flotante"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.ProjectNameEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Proyecto CU10"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.ProjectDescriptionEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Descripcion CU10"), closeSoftKeyboard());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.confirmAddProjectBtn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                0),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.AddProject), withContentDescription("Boton flotante"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.nameTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Tarea CU10"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.descriptionTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Descripcion"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerTaskState),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(0);
        materialTextView.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.priorityTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                10),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinnerTaskEffort),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                13),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(3);
        materialTextView2.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.dateTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                16),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("12/12/2023"), closeSoftKeyboard());

        onView(isRoot()).perform(waitFor(4000));

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.acceptTaskBtn),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                17)),
                                1),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction textView = onView(
                allOf(withId(R.id.nameTaskItem), withText("Tarea CU10"),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        textView.check(matches(withText("Tarea CU10")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.nameTask), withText("Tarea CU10"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Editada CU10"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.nameTask), withText("Editada CU10"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.acceptTaskBtn),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                17)),
                                1),
                        isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameTaskItem), withText("Editada CU10"),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        textView2.check(matches(withText("Editada CU10")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction navigationMenuItemView9 = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView9.perform(click());

        ViewInteraction button9 = onView(
                allOf(withId(R.id.btn_delete_user), withText("ELIMINAR"),
                        withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                        isDisplayed()));
        button9.check(matches(isDisplayed()));

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.btn_delete_user), withText("ELIMINAR"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        materialButton9.perform(click());
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
