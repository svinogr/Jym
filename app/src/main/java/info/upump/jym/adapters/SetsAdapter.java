package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.SetsViewHolder;
import info.upump.jym.adapters.holders.SetsViewHolderDefault;
import info.upump.jym.entity.Sets;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.USER_TYPE;


public class SetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Sets> setsList;
    private int type;

    public SetsAdapter(List<Sets> setsList, int type) {
        this.setsList = setsList;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_card_layout, parent, false);
        SetsViewHolder setsViewHolder = null;
        switch (type) {
            case DEFAULT_TYPE:
                setsViewHolder = new SetsViewHolderDefault(inflate);
                break;
            case USER_TYPE:
                setsViewHolder = new SetsViewHolder(inflate);
                break;
        }

        return setsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SetsViewHolder) {
            ((SetsViewHolder) holder).bind(setsList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }
}
