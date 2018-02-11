package udacity.maddie.bakingapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeGridActivityTest {

    public static final int RECIPE_INDEX = 0;

    @Rule
    public ActivityTestRule<RecipeGridActivity> mActivityTestRule =
        new ActivityTestRule<>(RecipeGridActivity.class);

    @Test
    public void recipeClick_opensRecipeDetails_Test() throws Exception {
        onData(anything()).inAdapterView(withId(R.id.recipe_grid_view)).atPosition(RECIPE_INDEX).perform(click());

        onView(withId(R.id.ingredients_text_view)).check(ViewAssertions.matches(withText(RecipeUtils.formatIngredients(Recipes.get
            (RECIPE_INDEX).getIngredients()))));

    }
}
