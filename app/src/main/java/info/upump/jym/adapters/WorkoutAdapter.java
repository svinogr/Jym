package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.WorkoutHolder;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 23.03.2018.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {
    private List<Workout> workoutList;

    public WorkoutAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    @Override
    public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
        return new WorkoutHolder(inflate);
    }

    @Override
    public void onBindViewHolder(WorkoutHolder holder, int position) {
        holder.bind(workoutList.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
