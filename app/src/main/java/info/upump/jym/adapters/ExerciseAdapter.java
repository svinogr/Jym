package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import info.upump.jym.R;
import info.upump.jym.entity.Exercise;

/**
 * Created by explo on 14.03.2018.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {
    private List<Exercise> exerciseList;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_layout, parent, false);
        return new ExerciseViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        holder.bind(exerciseList.get(position));

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
