package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.WorkoutDayViewHolder;
import info.upump.jym.adapters.holders.WorkoutDefaultChooseViewHolder;
import info.upump.jym.adapters.holders.WorkoutDefaultViewHolder;
import info.upump.jym.adapters.holders.WorkoutViewHolder;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 23.03.2018.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Workout> workoutList;
    public static final int DAY = 0;
    public static final int DEFAULT_TYPE = 1;
    public static final int DEFAULT_TYPE_CHOOSE = 2;
    private int type_holder;

    public WorkoutAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public WorkoutAdapter(List<Workout> workoutList, int type_holder) {
        this(workoutList);
        this.type_holder = type_holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
        switch (viewType) {
            case DAY:
                viewHolder = new WorkoutDayViewHolder(inflate);
                break;
            case DEFAULT_TYPE:
                viewHolder = new WorkoutDefaultViewHolder(inflate);
                break;
            case DEFAULT_TYPE_CHOOSE:
                viewHolder = new WorkoutDefaultChooseViewHolder(inflate);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WorkoutViewHolder) {
            ((WorkoutViewHolder) holder).bind(workoutList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type_holder;
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
