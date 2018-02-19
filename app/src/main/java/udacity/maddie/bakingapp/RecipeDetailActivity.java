package udacity.maddie.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RecipeDetailActivity extends AppCompatActivity implements OnRecipeStepClickListener, OnRecipeClickListener {

    public static final String SELECTED_RECIPE_INDEX_KEY = "selectedRecipeIndex";

    private int selectedRecipeIndex = -1;

    private RecipeDetailFragment recipeDetailFragment;

    private RecipeStepDetailFragment recipeStepDetailFragment;

    private View navigationContainer;

    private static boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectedRecipeIndex = savedInstanceState.getInt(SELECTED_RECIPE_INDEX_KEY, -1);
        } else {
            selectedRecipeIndex = getIntent().getIntExtra(SELECTED_RECIPE_INDEX_KEY, -1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        isTablet = RecipeUtils.getIsTablet(this);

        setUpNavigation();

        loadRecipeDetailFragment();
        if (isTablet) {
            loadRecipeStepDetailFragment(0);
        }
    }

    private void setUpNavigation() {
        navigationContainer = findViewById(R.id.navigation_container);
        navigationContainer.setVisibility(View.VISIBLE);

        ImageButton backwardButton = navigationContainer.findViewById(R.id.navigate_back_button);
        ImageButton forwardButton = navigationContainer.findViewById(R.id.navigate_forward_button);

        if (selectedRecipeIndex == 0) {
            backwardButton.setVisibility(View.GONE);
        }
        if (selectedRecipeIndex == Recipes.getRecipes().size() - 1) {
            forwardButton.setVisibility(View.GONE);
        }

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedRecipeIndex != 0)  {
                    //if not the first step, allow navigation backwards
                    onRecipeClick(selectedRecipeIndex - 1);
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedRecipeIndex != Recipes.getRecipes().size() - 1) {
                    //if not the last step, allow navigation forwards
                    onRecipeClick(selectedRecipeIndex + 1);
                }
            }
        });
    }

    private void loadRecipeDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (selectedRecipeIndex != -1 && Recipes.getRecipes() != null && Recipes.getRecipes().size() > 0) {
            recipeDetailFragment = RecipeDetailFragment.newInstance(Recipes.get(selectedRecipeIndex));
            fragmentManager.beginTransaction().replace(R.id.recipe_detail_fragment, recipeDetailFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
        super.onSaveInstanceState(outState);
    }

    public void loadRecipeStepDetailFragment(int stepIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Recipe recipe = Recipes.get(selectedRecipeIndex);

        for (RecipeStep step : recipe.getSteps()) {
            step.setSelected(false);
        }

        RecipeStep step = recipe.getSteps().get(stepIndex);

        if (isTablet) {
            step.setSelected(true);
            recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step, recipe, this);
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

    @Override
    public void onRecipeClick(int index) {
        selectedRecipeIndex = index;
        loadRecipeDetailFragment();
        if (isTablet) {
            loadRecipeStepDetailFragment(0);
        }
    }
}
