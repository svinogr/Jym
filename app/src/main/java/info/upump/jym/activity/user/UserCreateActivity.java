package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.entity.User;

import static info.upump.jym.activity.constant.Constants.ID;

public class UserCreateActivity extends AppCompatActivity {


    public static Intent createIntent(Context context, User user){
        Intent intent = new Intent(context, UserCreateActivity.class);
        intent.putExtra(ID, user.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
