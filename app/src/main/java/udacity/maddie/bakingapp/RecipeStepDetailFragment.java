package udacity.maddie.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static RecipeStep step;

    private static Recipe recipe;

    private TextView shortDescriptionTextView;

    private TextView longDescriptionTextView;


    public RecipeStepDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeGridFragment.
     */
    public static RecipeStepDetailFragment newInstance(RecipeStep inRecipeStep, Recipe inRecipe) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        step = inRecipeStep;
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
        return inflater.inflate(R.layout.fragment_recipe__step_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shortDescriptionTextView = view.findViewById(R.id.short_description_text_view);
        shortDescriptionTextView.setText(step.getShortDescription());

        longDescriptionTextView = view.findViewById(R.id.long_description_text_view);
        longDescriptionTextView.setText(step.getDescription());

        setUpVideoPlayer(view);

        if (!RecipeUtils.getIsTablet(getActivity())) {
            getActivity().setTitle(recipe.getName() + " Details");
            setUpNavigation(view);
        }

    }

    private void setUpNavigation(View view) {
        View navigationContainer = view.findViewById(R.id.navigation_container);
        navigationContainer.setVisibility(View.VISIBLE);
    }

    private void setUpVideoPlayer(View view) {

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
