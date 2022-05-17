package info.upump.jym.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.WorkoutDayDefaultViewHolder;
import info.upump.jym.adapters.holders.WorkoutDayViewHolder;
import info.upump.jym.adapters.holders.WorkoutDefaultChooseViewHolder;
import info.upump.jym.adapters.holders.WorkoutDefaultViewHolder;
import info.upump.jym.adapters.holders.WorkoutTemplateViewHolder;
import info.upump.jym.adapters.holders.AbstractWorkoutViewHolder;
import info.upump.jym.entity.Workout;
import info.upump.jym.fragments.cycle.CRUD;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;

public class WorkoutAdapter extends RecyclerView.Adapter<AbstractWorkoutViewHolder> {
    private List<Workout> workoutList;
    public static final int DAY = 0;
    public static final int DAY_DEFAULT = 7;
    private CRUD crud;
    private int type_holder;

    public WorkoutAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public WorkoutAdapter(List<Workout> workoutList, int type_holder, CRUD crud) {
        this(workoutList);
        this.type_holder = type_holder;
        this.crud = crud;
    }

    @Override
    public AbstractWorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AbstractWorkoutViewHolder viewHolder = null;
        View inflate;
        switch (viewType) {
            case DAY:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
                viewHolder = new WorkoutDayViewHolder(inflate, crud);
                break;
            case DAY_DEFAULT:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
                viewHolder = new WorkoutDayDefaultViewHolder(inflate);
                break;
            case LOADER_BY_USER_TYPE:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_default_card_layout, parent, false);
                viewHolder = new WorkoutTemplateViewHolder(inflate, crud);
                break;
            case LOADER_BY_DEFAULT_TYPE:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_default_card_layout, parent, false);
                viewHolder = new WorkoutDefaultViewHolder(inflate);
                break;
            case DEFAULT_TYPE_CHOOSE:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
                viewHolder = new WorkoutDefaultChooseViewHolder(inflate);
                break;
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AbstractWorkoutViewHolder holder, int position) {
        holder.bind(workoutList.get(position));

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
