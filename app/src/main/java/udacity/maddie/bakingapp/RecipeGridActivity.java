package udacity.maddie.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecipeGridActivity extends AppCompatActivity implements OnRecipeClickListener {

    RecipeGridFragment recipeGridFragment;

    private static final String SELECTED_RECIPE_INDEX_KEY = "selectedRecipeIndex";

    public static int selectedRecipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectedRecipeIndex = savedInstanceState.getInt(SELECTED_RECIPE_INDEX_KEY, -1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (Recipes.getRecipes().isEmpty()) {
            getRecipes();
        }
    }

    private void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RecipeUtils.RECIPE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        RecipeUtils.RecipesEndpointInterface endpoints = retrofit.create(RecipeUtils.RecipesEndpointInterface.class);
        Call<List<Recipe>> call = endpoints.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Response response) {
                if (response == null && response.isSuccess()) {
                    return;
                }

                ArrayList<Recipe> recipes = (ArrayList<Recipe>) response.body();

                if (recipes == null || recipes == null || recipes.size() == 0) {
                    Toast.makeText(RecipeGridActivity.this, R.string.no_recipes, Toast.LENGTH_LONG).show();
                    return;
                }

                Recipes.addAll(recipes);
                selectedRecipeIndex = (!Recipes.getRecipes().isEmpty()) ? 0 : -1;
                setupRecipeListFragment();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RecipeGridActivity.this, R.string.failure_message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecipeListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recipeGridFragment = RecipeGridFragment.newInstance();
        fragmentTransaction.replace(R.id.recipe_list_fragment, recipeGridFragment);
        fragmentTransaction.commit();
    }

    private void loadRecipeDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (selectedRecipeIndex != -1) {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
            startActivity(intent);
        } else {
            //if no recipe selected, make details pane empty
            fragmentManager.beginTransaction().replace(R.id.recipe_detail_fragment, new Fragment()).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
        super.onSaveInstanceState(outState);
    }

    public static int getSelectedRecipeIndex() {
        return selectedRecipeIndex;
    }

    public void onRecipeClick(int position) {
        selectedRecipeIndex = position;
        loadRecipeDetailFragment();

    }
}
