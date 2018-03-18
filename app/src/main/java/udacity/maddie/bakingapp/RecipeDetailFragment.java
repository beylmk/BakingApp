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

public class RecipeDetailFragment extends Fragment {

    private static Recipe recipe;

    private TextView ingredientsTextView;

    private RecyclerView stepsRecyclerView;

    private LinearLayoutManager layoutManager;

    private static int stepIndex = -1;

    private static int stepTop = -1;


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
        layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new RecipeStepAdapter(getActivity(), recipe.getSteps()));
    }

    private void setUpRecipeIngredients() {
        ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();
        String ingredientsText = RecipeUtils.formatIngredients(ingredients);

        ingredientsTextView.setText(ingredientsText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        stepIndex = layoutManager.findFirstVisibleItemPosition();
        View v = stepsRecyclerView.getChildAt(0);
        stepTop = (v == null) ? 0 : (v.getTop() - stepsRecyclerView.getPaddingTop());
        super.onPause();
    }

    @Override
    public void onResume() {
        if(stepIndex != -1) {
            layoutManager.scrollToPositionWithOffset(stepIndex, stepTop);
        }
        super.onResume();
    }


}
