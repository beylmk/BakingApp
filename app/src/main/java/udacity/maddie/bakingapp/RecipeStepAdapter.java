package udacity.maddie.bakingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rfl518 on 12/12/17.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private Context context;

    private ArrayList<RecipeStep> recipeSteps;

    private OnRecipeStepClickListener listener;

    public static class RecipeStepViewHolder extends RecyclerView.ViewHolder {

        public TextView descriptionTextView;


        public TextView stepNumberTextView;

        public RecipeStepViewHolder(View v) {
            super(v);
            descriptionTextView = v.findViewById(R.id.description_text_view);
            stepNumberTextView = v.findViewById(R.id.step_number_text_view);

        }

        public void setSelectedState() {
            descriptionTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }

        public void setUnselectedState() {
            descriptionTextView.setTypeface(Typeface.DEFAULT);
        }
    }

    public RecipeStepAdapter(Context context, ArrayList<RecipeStep> recipeSteps) {
        if (!(context instanceof OnRecipeStepClickListener)) {
            Log.e(RecipeStepAdapter.class.getSimpleName(), "activity does not implement OnRecipeStepClickListener");
        }
        this.context = context;
        this.recipeSteps = recipeSteps;
        this.listener = (OnRecipeStepClickListener) context;
    }

    @Override
    public RecipeStepAdapter.RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeStepAdapter.RecipeStepViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_step_row,
            parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, final int position) {
        RecipeStep currentStep = recipeSteps.get(position);
        holder.descriptionTextView.setText(currentStep.getShortDescription());
        holder.stepNumberTextView.setText(String.valueOf(currentStep.getId()));

        if (currentStep.isSelected()) {
            holder.setSelectedState();
        } else {
            holder.setUnselectedState();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeStepClick(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }
}
