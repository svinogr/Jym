package info.upump.jym.adapters.holders;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
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
        weight.setText(String.valueOf(user.getWeight()));
        fat.setText(String.valueOf(user.getFat()));
        date.setText(user.getStringFormatDate());
    }

    @Override
    public void onClick(View v) {
        crud.createIntentForResult(null, user);

    }

}
