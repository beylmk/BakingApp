package udacity.maddie.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.ArrayList;


/**
 * Created by rfl518 on 10/13/17.
 */

public final class RecipeUtils {

    public static final String TAG = RecipeUtils.class.getSimpleName();

    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public RecipeUtils() {
        throw new AssertionError("No RecipeUtils instances for you!");
    }

    public static String formatIngredients(ArrayList<RecipeIngredient> ingredientArrayList) {
        String ingredientsText = "";

        for (RecipeIngredient ingredient : ingredientArrayList) {
            ingredientsText += formatIngredientString(ingredient) + "\n";
        }
        return ingredientsText;
    }

    public static String formatIngredientString(RecipeIngredient ingredient) {

        Float quantityValue = ingredient.getQuantity();
        String ingredientQuantity = quantityValue != null ? String.valueOf(quantityValue) : "";

        String ingredientMeasure =
            ingredient.getMeasure() != null ? ingredient.getMeasure().toString().toLowerCase() + " ": "";

        String ingredientName = ingredient.getIngredient() != null ? ingredient.getIngredient() : "";

        return ingredientQuantity + " " + ingredientMeasure + ingredientName;
    }

    public static boolean getIsTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == context.getResources().getConfiguration().ORIENTATION_LANDSCAPE;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 200);
        return noOfColumns;
    }

    public static ArrayList<String> getSpinnerNameArray() {
        ArrayList<String> recipeNamesArray = new ArrayList<>();
        for (Recipe recipe : Recipes.getRecipes()) {
            recipeNamesArray.add(recipe.getName());
        }
        return recipeNamesArray;
    }

    public static int getRecipeIndexByName(String recipeName) {
        for (int i = 0; i < Recipes.getRecipes().size(); i++) {
            if (TextUtils.equals(recipeName, Recipes.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
