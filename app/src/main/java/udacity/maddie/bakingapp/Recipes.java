package udacity.maddie.bakingapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfl518 on 12/7/17.
 */

public class Recipes {

    private static final Recipes INSTANCE = new Recipes();

    private static ArrayList<Recipe> recipes = new ArrayList();

    private Recipes() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Instance of Recipes already exists");
        }
    }

    public static Recipes getInstance() {
        return INSTANCE;
    }

    public static ArrayList<Recipe> getRecipes() { return recipes; }

    public static void setRecipes(ArrayList<Recipe> inRecipes) { recipes = inRecipes;}

    public static void add(Recipe recipe) { recipes.add(recipe); }

    public static void addAll(List<Recipe> inRecipes) {
        if (inRecipes.size() == 0) {
            return;
        }
        recipes.addAll(inRecipes);

    }

    public static Recipe getById(long recipeId) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == recipeId) {
                return recipe;
            }
        }
        return null;
    }

    public static void clear() { recipes.clear(); }

    public static int size() { return recipes.size(); }

    public static Recipe get(int position) { return recipes.get(position); }

}
