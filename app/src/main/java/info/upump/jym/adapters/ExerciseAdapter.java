package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.ExerciseTemplateChooseViewHolder;
import info.upump.jym.adapters.holders.ExerciseTemplateViewHolder;
import info.upump.jym.adapters.holders.ExerciseWithInfoViewHolder;
import info.upump.jym.adapters.holders.ExerciseViewHolder;
import info.upump.jym.entity.Exercise;

/**
 * Created by explo on 14.03.2018.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Exercise> exerciseList;
    public static final int INFO = 0;
    public static final int DEFAULT_TYPE = 1;
    public static final int CHOOSE = 2;
    private int type_holder;

    public ExerciseAdapter(List<Exercise> exerciseList, int type_holder) {
        this.exerciseList = exerciseList;
        this.type_holder = type_holder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_layout, parent, false);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case INFO:
                viewHolder = new ExerciseWithInfoViewHolder(inflate);
                break;

            case DEFAULT_TYPE:
                viewHolder = new ExerciseTemplateViewHolder(inflate);
                break;
            case CHOOSE:
                viewHolder = new ExerciseTemplateChooseViewHolder(inflate);
                break;
        }

        return  viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return type_holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ExerciseViewHolder) {
            ((ExerciseViewHolder) holder).bind(exerciseList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
