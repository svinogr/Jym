package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean update;
    private User user;
    private FloatingActionButton editFab;
    private TextView dateText, weightPicker, fatPicker, neckPicker, shoulderPicker, pectoralPicker,
            rightBicepsPicker, leftBicepsPicker, absPicker, leftLegPicker, rightLegPicker, leftCalvesPicker, rightCalvesPicker;

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(ID, user.getId());
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_datail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_activity_user);

        editFab = findViewById(R.id.user_activity_detail_fab_main);
        dateText = findViewById(R.id.content_user_detail_date_edit);
        weightPicker = findViewById(R.id.content_user_detail_weight_edit);
        fatPicker = findViewById(R.id.content_user_detail_fat_edit);
        neckPicker = findViewById(R.id.content_user_detail_neck_edit);
        shoulderPicker = findViewById(R.id.content_user_detail_shoulder);
        pectoralPicker = findViewById(R.id.content_user_detail_pectoral_edit);
        rightBicepsPicker = findViewById(R.id.content_user_detail_right_biceps_edit);
        leftBicepsPicker = findViewById(R.id.content_user_detail_left_biceps_edit);
        absPicker = findViewById(R.id.content_user_detail_abs_edit);
        leftLegPicker = findViewById(R.id.content_user_detail_left_leg_edit);
        rightLegPicker = findViewById(R.id.content_user_detail_right_leg_edit);
        leftCalvesPicker = findViewById(R.id.content_user_detail_left_calves_edit);
        rightCalvesPicker = findViewById(R.id.content_user_detail_right_calves_edit);
        editFab.setOnClickListener(this);

        getItemFromBundle();
        creteViewFrom();
    }

    private void creteViewFrom() {
        dateText.setText(user.getStringFormatDate());
        weightPicker.setText(String.valueOf(user.getWeight()));
        fatPicker.setText(String.valueOf(user.getFat()));
        neckPicker.setText(String.valueOf(user.getNeck()));
        shoulderPicker.setText(String.valueOf(user.getShoulder()));
        pectoralPicker.setText(String.valueOf(user.getPectoral()));
        rightBicepsPicker.setText(String.valueOf(user.getRightBiceps()));
        leftBicepsPicker.setText(String.valueOf(user.getLeftBiceps()));
        absPicker.setText(String.valueOf(user.getAbs()));
        leftLegPicker.setText(String.valueOf(user.getLeftLeg()));
        rightLegPicker.setText(String.valueOf(user.getRightLeg()));
        leftCalvesPicker.setText(String.valueOf(user.getLeftCalves()));
        rightCalvesPicker.setText(String.valueOf(user.getRightCalves()));

    }

    private void getItemFromBundle() {
        long id = getIntent().getLongExtra(ID, 0);
        UserDao userDao = new UserDao(this);
        user = userDao.getById(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        if (item.getItemId() == R.id.edit_menu_delete) {
            Intent intent = new Intent();
            intent.putExtra(UPDATE_DELETE, DELETE);
            intent.putExtra(ID, user.getId());
            setResult(RESULT_OK, intent);
        } else
            Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_set_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if (update) {
            Intent intent = new Intent();
            intent.putExtra(UPDATE_DELETE, UPDATE);
            intent.putExtra(ID, user.getId());
            setResult(RESULT_OK, intent);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = UserCreateActivity.createIntent(this, user);
        startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);
    }
}

