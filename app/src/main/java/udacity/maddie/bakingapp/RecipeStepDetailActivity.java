package udacity.maddie.bakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by rfl518 on 12/18/17.
 */

public class RecipeStepDetailActivity extends AppCompatActivity implements OnRecipeStepClickListener {

    public static final String SELECTED_RECIPE_STEP_INDEX_KEY = "selectedRecipeStepIndex";

    public static final String SELECTED_RECIPE_INDEX_KEY = "selectedRecipeIndex";

    private static final String RECIPE_STEP_DETAIL_FRAGMENT_TAG = "recipeStepDetailFragment";

    private RecipeStepDetailFragment recipeStepDetailFragment;

    RecipeStep recipeStep;

    Recipe recipe;

    int selectedRecipeIndex;

    int selectedRecipeStepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Log.e("RSDActivity", "RecipeStepDetailActivities's onCreate with " + savedInstanceState == null ? "null" : "non-null"
            + " savedInstanceState");

        if (savedInstanceState != null) {
            selectedRecipeIndex = savedInstanceState.getInt(SELECTED_RECIPE_INDEX_KEY, -1);
            selectedRecipeStepIndex = savedInstanceState.getInt(SELECTED_RECIPE_STEP_INDEX_KEY, -1);
        } else {
            selectedRecipeIndex = getIntent().getIntExtra(RecipeDetailActivity.SELECTED_RECIPE_INDEX_KEY, -1);
            selectedRecipeStepIndex = getIntent().getIntExtra(SELECTED_RECIPE_STEP_INDEX_KEY, -1);
        }

        if (selectedRecipeIndex != -1 && selectedRecipeStepIndex != -1) {
            recipe = Recipes.get(selectedRecipeIndex);
            recipeStep = recipe.getSteps().get(selectedRecipeStepIndex);

            if (savedInstanceState == null) {
                loadRecipeStepDetailFragment();
            } else {
                recipeStepDetailFragment =
                    (RecipeStepDetailFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_STEP_DETAIL_FRAGMENT_TAG);
            }
        }
    }

    private void loadRecipeStepDetailFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(recipeStep, recipe, this);
        fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment,
            RECIPE_STEP_DETAIL_FRAGMENT_TAG)
            .commit();
    }

    @Override
    public void onRecipeStepClick(int index) {
        selectedRecipeStepIndex = index;
        recipeStep = recipe.getSteps().get(index);
        loadRecipeStepDetailFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("RSDActivity", "onSaveInstanceState");
        outState.putInt(SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
        outState.putInt(SELECTED_RECIPE_STEP_INDEX_KEY, selectedRecipeStepIndex);

        super.onSaveInstanceState(outState);
    }
}
