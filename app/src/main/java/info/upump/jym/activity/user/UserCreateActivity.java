package info.upump.jym.activity.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.upump.jym.R;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.START_DATA;

public class UserCreateActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private NumberPicker weight, fat, neck, shoulder, pectoral,
            rightBiceps, leftBiceps, abs, rightLeg, leftLeg, leftCalves, rightCalves;
    private TextView dateText;
    private String[] valueNumber;
    private String[] valueNumberFat;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String date;


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
        dateText = findViewById(R.id.content_user_create_date_edit);
        weight = findViewById(R.id.content_user_create_weight_edit);
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

        dateText.setOnClickListener(this);

        initValueOfNumberPicker();

        user = getItemFromIntent();

        if(savedInstanceState != null){
            if(savedInstanceState.getString(START_DATA) != null){
              date = savedInstanceState.getString(START_DATA);
            }
        }


        createView();
    }

    private void initValueOfNumberPicker() {

        double min = 0;
        int max = 500;
        int maxfat = 200;
        double step = 0.5;
        valueNumber = getArrayWithSteps(min, max, step);
        valueNumberFat = getArrayWithSteps(min, maxfat,step);


        weight.setMinValue(0);
        weight.setMaxValue(max);
        weight.setDisplayedValues(valueNumber);
        weight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        fat.setMinValue(0);
        fat.setMaxValue(maxfat);
        fat.setDisplayedValues(valueNumberFat);
        fat.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        neck.setMinValue(0);
        neck.setMaxValue(max);
        neck.setDisplayedValues(valueNumber);
        neck.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        pectoral.setMinValue(0);
        pectoral.setMaxValue(max);
        pectoral.setDisplayedValues(valueNumber);
        pectoral.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        shoulder.setMinValue(0);
        shoulder.setMaxValue(max);
        shoulder.setDisplayedValues(valueNumber);
        shoulder.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftBiceps.setMinValue(0);
        leftBiceps.setMaxValue(max);
        leftBiceps.setDisplayedValues(valueNumber);
        leftBiceps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightBiceps.setMinValue(0);
        rightBiceps.setMaxValue(max);
        rightBiceps.setDisplayedValues(valueNumber);
        rightBiceps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        abs.setMinValue(0);
        abs.setMaxValue(max);
        abs.setDisplayedValues(valueNumber);
        abs.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftLeg.setMinValue(0);
        leftLeg.setMaxValue(max);
        leftLeg.setDisplayedValues(valueNumber);
        leftLeg.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightLeg.setMinValue(0);
        rightLeg.setMaxValue(max);
        rightLeg.setDisplayedValues(valueNumber);
        rightLeg.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftCalves.setMinValue(0);
        leftCalves.setMaxValue(max);
        leftCalves.setDisplayedValues(valueNumber);
        leftCalves.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightCalves.setMinValue(0);
        rightCalves.setMaxValue(max);
        rightCalves.setDisplayedValues(valueNumber);
        rightCalves.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    }

    public String[] getArrayWithSteps(double iMinValue, int iMaxValue, double iStep) {
        int iStepsArray = iMaxValue * 2; //get the lenght array that will return

        String[] arrayValues = new String[iStepsArray]; //Create array with length of iStepsArray

        for (int i = 0; i < iStepsArray; i++) {
            arrayValues[i] = String.valueOf(iMinValue + (i * iStep));
        }

        return arrayValues;
    }


    private int getPozValue(double value) {
        return Arrays.asList(valueNumber).indexOf(String.valueOf(value));
    }

    private void createView() {
        weight.setValue(getPozValue(user.getWeight()));
        fat.setValue(getPozValue(user.getFat()));
        neck.setValue(getPozValue(user.getNeck()));
        pectoral.setValue(getPozValue(user.getShoulder()));
        shoulder.setValue(getPozValue(user.getPectoral()));
        leftBiceps.setValue(getPozValue(user.getLeftBiceps()));
        rightBiceps.setValue(getPozValue(user.getRightBiceps()));
        abs.setValue(getPozValue(user.getAbs()));
        leftLeg.setValue(getPozValue(user.getLeftLeg()));
        rightLeg.setValue(getPozValue(user.getRightLeg()));
        leftCalves.setValue(getPozValue(user.getLeftCalves()));
        rightCalves.setValue(getPozValue(user.getRightCalves()));
        if (user.getDate() == null) {
            user.setDate(new Date());
        }
        if(date == null) {
            dateText.setText(sdf.format(user.getDate()));
        }else dateText.setText(date);
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
      /*  if (changeableItem.getWeight() == 0) {
            Toast.makeText(this, "времен, необходтио ввести вес", Toast.LENGTH_SHORT).show();
            return;
        }*/
        long id = userDao.create(changeableItem);
        if (id != -1) {
            user.setId(id);
            Toast.makeText(this, "времен, замер сохранен", Toast.LENGTH_SHORT).show();
            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).
                show();

    }

    private boolean itemIsNotChanged() {
        User changeableItem = getChangeableItem();
        System.out.println(changeableItem);
        System.out.println(changeableItem.getDate()+"  " +user.getDate());
        if (changeableItem.getDate().compareTo(user.getDate())!=0) return false;
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
        User changeableUser = new User();
        changeableUser.setId(user.getId());
      /*  if (user.getId() > 0) {
            changeableUser.setDate(user.getDate());
        } else changeableUser.setDate(new Date());*/
        try {
            changeableUser.setDate(sdf.parse(dateText.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
      //  changeableUser.setDate(new Date(dateText.getText().toString()));

        changeableUser.setWeight(Double.parseDouble(valueNumber[weight.getValue()]));
        changeableUser.setFat(Double.parseDouble(valueNumber[fat.getValue()]));
        changeableUser.setNeck(Double.parseDouble(valueNumber[neck.getValue()]));
        changeableUser.setShoulder(Double.parseDouble(valueNumber[shoulder.getValue()]));
        changeableUser.setPectoral(Double.parseDouble(valueNumber[pectoral.getValue()]));
        changeableUser.setRightBiceps(Double.parseDouble(valueNumber[rightBiceps.getValue()]));
        changeableUser.setLeftBiceps(Double.parseDouble(valueNumber[leftBiceps.getValue()]));
        changeableUser.setAbs(Double.parseDouble(valueNumber[abs.getValue()]));
        changeableUser.setRightLeg(Double.parseDouble(valueNumber[rightLeg.getValue()]));
        changeableUser.setLeftLeg(Double.parseDouble(valueNumber[leftLeg.getValue()]));
        changeableUser.setLeftCalves(Double.parseDouble(valueNumber[leftCalves.getValue()]));
        changeableUser.setRightCalves(Double.parseDouble(valueNumber[rightCalves.getValue()]));
        return changeableUser;
    }


    private void update() {
        User userUpdate = getChangeableItem();
/*
        if (userUpdate.getWeight() == 0) {
            Toast.makeText(this, "времен, необходтио ввести вес", Toast.LENGTH_SHORT).show();
            return;
        }*/
        UserDao userDao = new UserDao(this);
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
        Calendar newCalendar = null;
        DatePickerDialog j = null;
        switch (v.getId()) {
            case R.id.content_user_create_date_edit:
                newCalendar = Calendar.getInstance();
                newCalendar.setTime(user.getDate());
                j = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        user.setDate(newDate.getTime());
                        dateText.setText(sdf.format(user.getDate()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                j.show();

                break;
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(START_DATA, dateText.getText().toString());

    }
}
