package udacity.maddie.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rfl518 on 12/18/17.
 */

public class RecipeStepDetailActivity  extends AppCompatActivity {

    public static final String SELECTED_RECIPE_STEP_INDEX_KEY = "selectedRecipeStepIndex";

    private RecipeStepDetailFragment recipeStepDetailFragment;

    RecipeStep recipeStep;

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        int selectedRecipeIndex = getIntent().getIntExtra(RecipeDetailActivity.SELECTED_RECIPE_INDEX_KEY, -1);
        int selectedRecipeStepIndex = getIntent().getIntExtra(SELECTED_RECIPE_STEP_INDEX_KEY, -1);

        if (selectedRecipeIndex != -1 && selectedRecipeStepIndex != -1) {
            recipe = Recipes.get(selectedRecipeIndex);
            recipeStep = recipe.getSteps().get(selectedRecipeStepIndex);

            loadRecipeStepDetailFragment();
        }
    }

    private void loadRecipeStepDetailFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(recipeStep, recipe);
        fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment).commit();
    }

}
