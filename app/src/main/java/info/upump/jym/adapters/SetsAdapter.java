package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.SetsViewHolder;
import info.upump.jym.entity.Sets;

/**
 * Created by explo on 02.04.2018.
 */

public class SetsAdapter extends RecyclerView.Adapter<SetsViewHolder> {
    private List<Sets> setsList;

    public SetsAdapter(List<Sets> setsList) {
        this.setsList = setsList;
    }

    @Override
    public SetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_card_layout, parent, false);
        SetsViewHolder setsViewHolder = new SetsViewHolder(inflate);
        return setsViewHolder;
    }

    @Override
    public void onBindViewHolder(SetsViewHolder holder, int position) {
        holder.bind(setsList.get(position));
    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }
}
