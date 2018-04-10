package info.upump.jym.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.sets.SetActivityCreate;
import info.upump.jym.bd.SetDao;
import info.upump.jym.bd.UserDao;
import info.upump.jym.dialog.PickerDialog;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.User;

import static info.upump.jym.activity.constant.Constants.ID;

public class UserCreateActivity extends AppCompatActivity implements IPicable, View.OnClickListener {
    private User user;
    private EditText weight, height, fat, neck, shoulder, pectoral,
            rightBiceps, leftBiceps, abs, rightLeg, leftLeg, leftCalves, rightCalves;
    private EditText curView;

    @Override
    public void setPicker(double number) {
        curView.setText(String.valueOf(number));
    }


    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, UserCreateActivity.class);
        intent.putExtra(ID, user.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        weight = findViewById(R.id.content_user_create_weight_edit);
        height = findViewById(R.id.content_user_create_height_edit);
        fat = findViewById(R.id.content_user_create_fat_edit);
        neck = findViewById(R.id.content_user_create_neck_edit);
        shoulder = findViewById(R.id.content_user_create_shoulder);
        pectoral = findViewById(R.id.content_user_create_pectoral_edit);
        rightBiceps = findViewById(R.id.content_user_create_right_biceps_edit);
        leftBiceps = findViewById(R.id.content_user_create_left_biceps_edit);
        abs = findViewById(R.id.content_user_create_abs_edit);
        leftLeg = findViewById(R.id.content_user_create_left_leg_edit);
        rightLeg = findViewById(R.id.content_user_create_right_leg_edit);
        leftCalves = findViewById(R.id.content_user_create_left_calves_edit);
        rightCalves = findViewById(R.id.content_user_create_right_calves_edit);

        user = getItemFromIntent();
        createView();
        setInputDialog();
    }

    private void setInputDialog() {
        weight.setOnClickListener(this);
        height.setOnClickListener(this);
        fat.setOnClickListener(this);
        neck.setOnClickListener(this);
        shoulder.setOnClickListener(this);
        pectoral.setOnClickListener(this);
        rightBiceps.setOnClickListener(this);
        leftBiceps.setOnClickListener(this);
        abs.setOnClickListener(this);
        leftLeg.setOnClickListener(this);
        rightLeg.setOnClickListener(this);
        leftCalves.setOnClickListener(this);
        rightCalves.setOnClickListener(this);

    }

    private void createView() {
      //  if (user.getId() > 0) {
            weight.setText(String.valueOf(user.getWeight()));
            height.setText(String.valueOf(user.getHeight()));
            fat.setText(String.valueOf(user.getFat()));
            neck.setText(String.valueOf(user.getNeck()));
            shoulder.setText(String.valueOf(user.getShoulder()));
            pectoral.setText(String.valueOf(user.getPectoral()));
            rightBiceps.setText(String.valueOf(user.getRightBiceps()));
            leftBiceps.setText(String.valueOf(user.getLeftBiceps()));
            abs.setText(String.valueOf(user.getAbs()));
            rightLeg.setText(String.valueOf(user.getRightLeg()));
            leftLeg.setText(String.valueOf(user.getLeftLeg()));
            rightCalves.setText(String.valueOf(user.getRightCalves()));
            leftCalves.setText(String.valueOf(user.getLeftCalves()));
        //}
    }


    private User getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        User user;
        if (id > 0) {
            UserDao userDao = new UserDao(this);
            user = userDao.getById(id);
            setTitle("Изменить замеры");
        } else {
            user = new User();
            setTitle("Новый замеры");
        }
        return user;
    }

    private void exit() {
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (user.getId() > 0) {
                        update();
                    } else save();


                }
            });
            ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = createIntentForResult(); // при создании из вне
                    setResult(RESULT_CANCELED, intent);
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }

    }

    private void save() {
        UserDao userDao = new UserDao(this);
        User changeableItem = getChangeableItem();
        long id = userDao.create(changeableItem);
       if(id != -1){
           user.setId(id);
        Toast.makeText(this, "времен, замер сохранен", Toast.LENGTH_SHORT).show();
        Intent intent = createIntentForResult(); // при создании из вне
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
    } else Toast.makeText(this,"времен, не возможно сохранить",Toast.LENGTH_SHORT).
    show();

}

    private boolean itemIsNotChanged() {
        User changeableItem = getChangeableItem();
        System.out.println(changeableItem);
        if (changeableItem.getWeight() != user.getWeight()) return false;
        if (changeableItem.getHeight() != user.getHeight()) return false;
        if (changeableItem.getFat() != user.getFat()) return false;
        if (changeableItem.getNeck() != user.getNeck()) return false;
        if (changeableItem.getShoulder() != user.getShoulder()) return false;
        if (changeableItem.getPectoral() != user.getPectoral()) return false;
        if (changeableItem.getRightBiceps() != user.getRightBiceps()) return false;
        if (changeableItem.getLeftBiceps() != user.getLeftBiceps()) return false;
        if (changeableItem.getAbs() != user.getAbs()) return false;
        if (changeableItem.getRightLeg() != user.getRightLeg()) return false;
        if (changeableItem.getLeftLeg() != user.getLeftLeg()) return false;
        if (changeableItem.getLeftCalves() != user.getLeftCalves()) return false;
        if (changeableItem.getRightCalves() != user.getRightCalves()) return false;
        return true;
    }

    private User getChangeableItem() {
        User changeableSets = new User();
        changeableSets.setId(user.getId());
        if(user.getId()>0){
            changeableSets.setDate(user.getDate());
        } else  changeableSets.setDate(new Date());
        changeableSets.setWeight(Double.parseDouble(weight.getText().toString()));
        changeableSets.setHeight(Double.parseDouble(height.getText().toString()));
        changeableSets.setFat(Double.parseDouble(fat.getText().toString()));
        changeableSets.setNeck(Double.parseDouble(neck.getText().toString()));
        changeableSets.setPectoral(Double.parseDouble(pectoral.getText().toString()));
        changeableSets.setRightBiceps(Double.parseDouble(rightBiceps.getText().toString()));
        changeableSets.setLeftBiceps(Double.parseDouble(leftBiceps.getText().toString()));
        changeableSets.setAbs(Double.parseDouble(abs.getText().toString()));
        changeableSets.setRightLeg(Double.parseDouble(rightLeg.getText().toString()));
        changeableSets.setLeftLeg(Double.parseDouble(leftLeg.getText().toString()));
        changeableSets.setLeftCalves(Double.parseDouble(leftCalves.getText().toString()));
        changeableSets.setRightCalves(Double.parseDouble(rightCalves.getText().toString()));
        return changeableSets;
    }


    private void update() {
        UserDao userDao = new UserDao(this);
        if (weight.getText().toString().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести вес", Toast.LENGTH_SHORT).show();
            return;
        }
        User userUpdate = getChangeableItem();
        boolean id = userDao.update(userUpdate);
        if (id) {
            Toast.makeText(this, "времен, замер  сохранен", Toast.LENGTH_SHORT).show();
            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_set_menu, menu);
        return true;
    }
    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(ID, user.getId());
        return intent;
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        if (item.getItemId() == R.id.edit_menu_delete) {
            if (user.getId() > 0) {
                switch (item.getItemId()) {
                    case R.id.edit_menu_delete:
                        Snackbar.make(getWindow().getDecorView(), "Удалить замер?", Snackbar.LENGTH_LONG)
                                .setAction("Да", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        delete(user.getId());

                                    }
                                }).show();
                        break;
                }
            } else Toast.makeText(this, "времен, замер  не сохранен", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(long id) {
        UserDao userDao = new UserDao(this);
        if (userDao.delete(user)) {
            Toast.makeText(this, "времен, замер  удален", Toast.LENGTH_SHORT).show();
            //exit();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, замер  не удалено", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        curView = (EditText) v;
        System.out.println(curView.getText().toString());
        PickerDialog pickerDialog = new PickerDialog();
        Bundle bundle = new Bundle();
        bundle.putString(PickerDialog.VALUE, curView.getText().toString());
        pickerDialog.setArguments(bundle);
        pickerDialog.show(getFragmentManager(), null);
    }
}
