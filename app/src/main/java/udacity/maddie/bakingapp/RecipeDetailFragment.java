package udacity.maddie.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment {

    private static Recipe recipe;

    private TextView ingredientsTextView;

    private RecyclerView stepsRecyclerView;

    public RecipeDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeGridFragment.
     */
    public static RecipeDetailFragment newInstance(Recipe inRecipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        recipe = inRecipe;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViews(view);

        if (!RecipeUtils.getIsTablet(getActivity())) {
            getActivity().setTitle(recipe.getName() + " Details");
        }

        setUpRecipeIngredients();
        setUpRecipeSteps();
    }

    private void getViews(View view) {

        ingredientsTextView = view.findViewById(R.id.ingredients_text_view);

        stepsRecyclerView = view.findViewById(R.id.steps_recycler_view);
    }

    private void setUpRecipeSteps() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new RecipeStepAdapter(getActivity(), recipe.getSteps()));
    }

    private void setUpRecipeIngredients() {
        ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();
        String ingredientsText = "";

        for (RecipeIngredient ingredient : ingredients) {
            ingredientsText += formatIngredientString(ingredient) + "\n";
        }
        ingredientsTextView.setText(ingredientsText);
    }

    private String formatIngredientString(RecipeIngredient ingredient) {

        Float quantityValue = ingredient.getQuantity();
        String ingredientQuantity = quantityValue != null ? String.valueOf(quantityValue) : "";

        String ingredientMeasure =
            ingredient.getMeasure() != null ? ingredient.getMeasure().toString().toLowerCase() + " ": "";

        String ingredientName = ingredient.getIngredient() != null ? ingredient.getIngredient() : "";

        return ingredientQuantity + " " + ingredientMeasure + ingredientName;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
