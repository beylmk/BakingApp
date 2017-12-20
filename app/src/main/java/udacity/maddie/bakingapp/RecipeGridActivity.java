package udacity.maddie.bakingapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeGridActivity extends AppCompatActivity implements OnRecipeClickListener {

    RecipeGridFragment recipeGridFragment;

    private static int selectedRecipeIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (Recipes.getRecipes().isEmpty()) {
            getRecipes();
        }
        setupRecipeListFragment();
    }

    private void getRecipes() {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Recipe>>(){}.getType();

        JSONArray recipesObject = RecipeUtils.getRecipesArray(this);
        ArrayList<Recipe> recipes = gson.fromJson(recipesObject.toString(), collectionType);
        Recipes.addAll(recipes);

        //by default, show details for first recipe
        selectedRecipeIndex = (!Recipes.getRecipes().isEmpty()) ? 0 : -1;
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

    public static int getSelectedRecipeIndex() {
        return selectedRecipeIndex;
    }

    public void onRecipeClick(int position) {
        selectedRecipeIndex = position;
        loadRecipeDetailFragment();

    }
}
