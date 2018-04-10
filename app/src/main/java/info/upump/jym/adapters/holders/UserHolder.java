package info.upump.jym.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.user.UserCreateActivity;
import info.upump.jym.entity.User;

/**
 * Created by explo on 10.04.2018.
 */

public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView weight, height, fat, date;
    private User user;
    private Context context;
    private View itemView;

    public UserHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        this.itemView = itemView;
        weight = itemView.findViewById(R.id.user_card_layout_weight);
        height = itemView.findViewById(R.id.user_card_layout_height);
        fat = itemView.findViewById(R.id.user_card_layout_fat);
        date = itemView.findViewById(R.id.user_card_layout_label_date);
        this.itemView = itemView;
        itemView.setOnClickListener(this);

    }

    public void bind(User user) {
        this.user = user;
        System.out.println(user);
        weight.setText(String.valueOf(user.getWeight()));
        height.setText(String.valueOf(user.getHeight()));
        fat.setText(String.valueOf(user.getFat()));
        date.setText(user.getStringFormatDate());
    }

    @Override
    public void onClick(View v) {
        Intent intent = UserCreateActivity.createIntent(context, user);
        System.out.printf(user.toString());
        context.startActivity(intent);

    }
}
