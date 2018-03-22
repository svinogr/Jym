package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.CycleHolder;
import info.upump.jym.entity.Cycle;


/**
 * Created by explo on 22.03.2018.
 */

public class CycleAdapter extends RecyclerView.Adapter<CycleHolder> {
    private List<Cycle> cycleList;

    public CycleAdapter(List<Cycle> workouts) {
        this.cycleList = workouts;
    }

    @Override
    public CycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_layout, parent, false);
        return new CycleHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CycleHolder holder, int position) {
        holder.bind(cycleList.get(position));

    }

    @Override
    public int getItemCount() {
        return cycleList.size();
    }
}
