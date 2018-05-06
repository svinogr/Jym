package info.upump.jym.adapters.holders;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.user.UserCreateActivity;
import info.upump.jym.entity.User;
import info.upump.jym.fragments.cycle.CRUD;

public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView weight, fat, date;
    private User user;
    private Context context;
    private View itemView;
    private CRUD crud;

    public UserHolder(View itemView, CRUD crud) {
        super(itemView);
        this.crud = crud;
        context = itemView.getContext();
        this.itemView = itemView;
        weight = itemView.findViewById(R.id.user_card_layout_weight);
        fat = itemView.findViewById(R.id.user_card_layout_fat);
        date = itemView.findViewById(R.id.user_card_layout_label_date);
        this.itemView = itemView;
        itemView.setOnClickListener(this);
    }

    public void bind(User user) {
        this.user = user;
        System.out.println(user);
        weight.setText(String.valueOf(user.getWeight()));
        fat.setText(String.valueOf(user.getFat()));
        date.setText(user.getStringFormatDate());
    }

    @Override
    public void onClick(View v) {
        crud.createIntentForResult(null, user);

    }

}
