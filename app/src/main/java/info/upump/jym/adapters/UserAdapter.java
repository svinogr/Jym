package info.upump.jym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.holders.UserHolder;
import info.upump.jym.entity.User;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> list) {
        this.userList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_layout, parent, false);
        RecyclerView.ViewHolder viewHolder = new UserHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserHolder){
            ((UserHolder)holder).bind(userList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
