package com.example.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todoc.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todoc.utils.DeleteViewAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TodocInstrumentedTest {

    private static int INITIAL_TASKS = 5;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void onAddMeetingButtonClickedVerifyAddMeetingFragmentIsLaunched() {
        // When
        onView(withId(R.id.fab_add_task))
                .perform(click());

        //Then
        onView(withId(R.id.scrollView_addMeeting))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCorrectlyAddNewTask() {
        //WHEN
        onView(withId(R.id.fab_add_task))
                .perform(click());

        onView(withId(R.id.task_edit))
                .perform(replaceText("Test task"));

        onView(withId(R.id.project_list))
                .perform(click());

        onView(withText("Projet Lucidia"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.addBtn))
                .perform(click());

        onView(allOf(withId(R.id.task_recycler_view), isDisplayed()))
                .check(withItemCount(INITIAL_TASKS + 1));
    }

    @Test
    public void checkAddButtonIsDisabledWhenAllFieldsAreNotCompleted() {
        // Given
        onView(withId(R.id.fab_add_task))
                .perform(click());
        //WHEN
        onView(withId(R.id.task_edit))
                .perform(replaceText("Test task"));

        //THEN
        onView(withId(R.id.addBtn))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void deleteTaskShouldRemoveOneItem() throws InterruptedException {
        // Given
        onView(allOf(withId(R.id.task_recycler_view), isDisplayed()))
                .check(withItemCount(INITIAL_TASKS));

        // When
        onView(allOf(withId(R.id.task_recycler_view), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        Thread.sleep(5000);

        // Then
        onView(allOf(withId(R.id.task_recycler_view), isDisplayed()))
                .check(withItemCount(INITIAL_TASKS - 1));
    }

    @Test
    public void tasksListFilterByTaskShouldDisplayCorrectTasks() throws InterruptedException {

        //WHEN
        onView(withId(R.id.filterButton))
            .perform(click());
        onView(withText("Filtrer par nom"))
            .perform(click());
        onView(withText("Projet Circus"))
            .perform(click());

        // TODO FADEL il faut préciser un peu plus le "onView"
//        onView(allOf(withText("Projet Circus"), isRoot()))
//            .check(matches(ViewMatchers.isChecked()));

        onView(isRoot()).perform(pressBack());

        //THEN
        onView(allOf(withId(R.id.task_recycler_view), isDisplayed()))
                .check(withItemCount(2));
    }
}