package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.CycleDefaultChooseViewHolder;
import info.upump.jym.adapters.holders.CycleDefaultViewHolder;
import info.upump.jym.adapters.holders.CycleViewHolder;
import info.upump.jym.entity.Cycle;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;


/**
 * Created by explo on 22.03.2018.
 */

public class CycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Cycle> cycleList;
    private int type;

    public CycleAdapter(List<Cycle> workouts, int type) {
        this.cycleList = workouts;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycle_card_layout, parent, false);
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case LOADER_BY_DEFAULT_TYPE:
                holder = new CycleDefaultViewHolder(inflate);
                break;
            case LOADER_BY_USER_TYPE:
                holder = new CycleViewHolder(inflate);
                break;
            case DEFAULT_TYPE_CHOOSE:
                holder = new CycleDefaultChooseViewHolder(inflate);
                break;


        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CycleViewHolder) {
            ((CycleViewHolder)holder).bind(cycleList.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return cycleList.size();
    }
}
