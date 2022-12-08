package es.unex.infinitetime.EspressoTests.CU14;


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
import org.hamcrest.core.IsInstanceOf;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.infinitetime.MainActivity;
import es.unex.infinitetime.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaskStateEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mostrarTareasPorEstadoTest() {
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
        textInputEditText.perform(replaceText("CU14"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textEdit_email_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.txt_email_register),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("CU14@unex.es"), closeSoftKeyboard());

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
        appCompatEditText.perform(replaceText("Proyecto CU14"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.ProjectDescriptionEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Descripcion"), closeSoftKeyboard());

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
        appCompatEditText3.perform(replaceText("Tarea 1 CU14"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.descriptionTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Desc"), closeSoftKeyboard());

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
                .atPosition(0);
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

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.AddProject), withContentDescription("Boton flotante"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.nameTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Tarea 2 CU14"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.descriptionTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("Desc"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinnerTaskState),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction materialTextView3 = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(1);
        materialTextView3.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.priorityTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                10),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerTaskEffort),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                13),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        DataInteraction materialTextView4 = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(1);
        materialTextView4.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.dateTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                16),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("13/12/2023"), closeSoftKeyboard());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.acceptTaskBtn),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                17)),
                                1),
                        isDisplayed()));
        floatingActionButton6.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.AddProject), withContentDescription("Boton flotante"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.nameTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("Tarea 3 CU14"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.descriptionTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("Desc"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spinnerTaskState),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        DataInteraction materialTextView5 = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(2);
        materialTextView5.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.priorityTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                10),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.spinnerTaskEffort),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                13),
                        isDisplayed()));
        appCompatSpinner6.perform(click());

        DataInteraction materialTextView6 = onData(anything())
                .inAdapterView(allOf(withId(androidx.constraintlayout.widget.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.constraintlayout.widget.R.id.contentPanel),
                                0)))
                .atPosition(2);
        materialTextView6.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.dateTask),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                16),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("14/12/2023"), closeSoftKeyboard());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.acceptTaskBtn),
                        childAtPosition(
                                allOf(withId(R.id.frameLayout),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                17)),
                                1),
                        isDisplayed()));
        floatingActionButton8.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textView = onView(
                allOf(withText("SIN EMPEZAR"),
                        withParent(allOf(withContentDescription("Sin empezar"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("SIN EMPEZAR")));

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameTaskItem), withText("Tarea 1 CU14"),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        textView2.check(matches(withText("Tarea 1 CU14")));

        ViewInteraction tabView = onView(
                allOf(withContentDescription("En progreso"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tab_layout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textView3 = onView(
                allOf(withText("EN PROGRESO"),
                        withParent(allOf(withContentDescription("En progreso"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("EN PROGRESO")));

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.nameTaskItem), withText("Tarea 2 CU14"),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        textView4.check(matches(withText("Tarea 2 CU14")));

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Hechas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tab_layout),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textView5 = onView(
                allOf(withText("HECHAS"),
                        withParent(allOf(withContentDescription("Hechas"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView5.check(matches(withText("HECHAS")));

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.nameTaskItem), withText("Tarea 3 CU14"),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        textView6.check(matches(withText("Tarea 3 CU14")));

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

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_delete_user), withText("ELIMINAR"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        materialButton4.perform(click());
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
