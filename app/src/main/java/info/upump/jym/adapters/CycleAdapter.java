package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.CycleViewHolder;
import info.upump.jym.entity.Cycle;


/**
 * Created by explo on 22.03.2018.
 */

public class CycleAdapter extends RecyclerView.Adapter<CycleViewHolder> {
    private List<Cycle> cycleList;

    public CycleAdapter(List<Cycle> workouts) {
        this.cycleList = workouts;
    }

    @Override
    public CycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycle_card_layout, parent, false);
        return new CycleViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CycleViewHolder holder, int position) {
        holder.bind(cycleList.get(position));

    }

    @Override
    public int getItemCount() {
        return cycleList.size();
    }
}
