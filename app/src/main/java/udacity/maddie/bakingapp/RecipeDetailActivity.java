package udacity.maddie.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeDetailActivity extends AppCompatActivity implements OnRecipeStepClickListener {

    public static final String SELECTED_RECIPE_INDEX_KEY = "selectedRecipeIndex";

    private int selectedRecipeIndex = -1;

    private RecipeDetailFragment recipeDetailFragment;

    private RecipeStepDetailFragment recipeStepDetailFragment;

    private static boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        isTablet = RecipeUtils.getIsTablet(this);

        selectedRecipeIndex = getIntent().getIntExtra(SELECTED_RECIPE_INDEX_KEY, -1);
        loadRecipeDetailFragment();
    }

    private void loadRecipeDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (selectedRecipeIndex != -1) {
            recipeDetailFragment = RecipeDetailFragment.newInstance(Recipes.get(selectedRecipeIndex));
            fragmentManager.beginTransaction().replace(R.id.recipe_detail_fragment, recipeDetailFragment).commit();
        }
    }

    private void loadRecipeStepDetailFragment(int stepIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Recipe recipe = Recipes.get(selectedRecipeIndex);
        RecipeStep step = recipe.getSteps().get(stepIndex);

        if (isTablet) {
            recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step, recipe);
            fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_fragment, recipeStepDetailFragment).commit();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.SELECTED_RECIPE_STEP_INDEX_KEY, stepIndex);
            intent.putExtra(SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
            startActivity(intent);
        }
    }

    @Override
    public void onRecipeStepClick(int stepIndex) {
        loadRecipeStepDetailFragment(stepIndex);
    }
}
