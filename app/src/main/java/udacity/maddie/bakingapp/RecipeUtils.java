package udacity.maddie.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by rfl518 on 10/13/17.
 */

public class RecipeUtils {

    public static final String TAG = RecipeUtils.class.getSimpleName();

    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

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

    public interface RecipesEndpointInterface {
        @GET(RECIPE_URL)
        Call<List<Recipe>> getRecipes();
    }
}
