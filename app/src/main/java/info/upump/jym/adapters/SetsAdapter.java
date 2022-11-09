package info.upump.jym.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.AbstractSetViewHolder;
import info.upump.jym.adapters.holders.SetsViewHolder;
import info.upump.jym.adapters.holders.SetsViewHolderDefault;
import info.upump.jym.entity.Sets;
import info.upump.jym.fragments.cycle.CRUD;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.USER_TYPE;


public class SetsAdapter extends RecyclerView.Adapter<AbstractSetViewHolder> {
    private List<Sets> setsList;
    private int type;
    private CRUD crud;

    public SetsAdapter(List<Sets> setsList, int type, CRUD crud) {
        this.setsList = setsList;
        this.type = type;
        this.crud = crud;
    }

    @Override
    public AbstractSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_card_layout_v2, parent, false);
        AbstractSetViewHolder setsViewHolder = null;
        switch (type) {
            case DEFAULT_TYPE:
                setsViewHolder = new SetsViewHolderDefault(inflate);
                break;
            case USER_TYPE:
                setsViewHolder = new SetsViewHolder(inflate, crud);
                break;
        }

        return setsViewHolder;
    }

    @Override
    public void onBindViewHolder(AbstractSetViewHolder holder, int position) {
        holder.bind(setsList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }

    public List<Sets> getSetsList() {
        return setsList;
    }

    public void setSetsList(List<Sets> setsList) {
        this.setsList = setsList;
    }
}
