package info.upump.jym.activity.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.upump.jym.R;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.User;

import static info.upump.jym.activity.constant.Constants.ABS;
import static info.upump.jym.activity.constant.Constants.FAT;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.L_BICEPS;
import static info.upump.jym.activity.constant.Constants.L_CALVES;
import static info.upump.jym.activity.constant.Constants.L_LEG;
import static info.upump.jym.activity.constant.Constants.NECK;
import static info.upump.jym.activity.constant.Constants.PECTORAL;
import static info.upump.jym.activity.constant.Constants.R_BICEPS;
import static info.upump.jym.activity.constant.Constants.R_CALVES;
import static info.upump.jym.activity.constant.Constants.R_LEG;
import static info.upump.jym.activity.constant.Constants.SHOULDERS;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;
import static info.upump.jym.activity.constant.Constants.WEIGHT;


public class UserCreateActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private NumberPicker weightPicker, fatPicker, neckPicker, shoulderPicker, pectoralPicker,
            rightBicepsPicker, leftBicepsPicker, absPicker, rightLegPicker, leftLegPicker, leftCalvesPicker, rightCalvesPicker;
    private TextView dateText;
    private String[] valueNumber;
    private String[] valueNumberFat;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String date;
    private int weightValue = -1, fatValue = -1, neckValue = -1, shoulderValue = -1, pectoralValue = -1,
            rightBicepsValue = -1, leftBicepsValue = -1, absValue = -1, rightLegValue = -1, leftLegValue = -1, leftCalvesValue = -1, rightCalvesValue = -1;


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
        weightPicker = findViewById(R.id.content_user_create_weight_edit);
        fatPicker = findViewById(R.id.content_user_create_fat_edit);
        neckPicker = findViewById(R.id.content_user_create_neck_edit);
        shoulderPicker = findViewById(R.id.content_user_create_shoulder);
        pectoralPicker = findViewById(R.id.content_user_create_pectoral_edit);
        rightBicepsPicker = findViewById(R.id.content_user_create_right_biceps_edit);
        leftBicepsPicker = findViewById(R.id.content_user_create_left_biceps_edit);
        absPicker = findViewById(R.id.content_user_create_abs_edit);
        leftLegPicker = findViewById(R.id.content_user_create_left_leg_edit);
        rightLegPicker = findViewById(R.id.content_user_create_right_leg_edit);
        leftCalvesPicker = findViewById(R.id.content_user_create_left_calves_edit);
        rightCalvesPicker = findViewById(R.id.content_user_create_right_calves_edit);

        dateText.setOnClickListener(this);

        initValueOfNumberPicker();

        user = getItemFromIntent();


        if (savedInstanceState != null) {
            valuesFromSavedInstanceState(savedInstanceState);

        }
        createView();
    }

    private void valuesFromSavedInstanceState(Bundle saved) {
        if (saved.getString(START_DATA) != null) {
            date = saved.getString(START_DATA);
        }
        if (saved.getString(WEIGHT) != null) {
            weightValue = Integer.parseInt(saved.getString(WEIGHT));
        }
        if (saved.getString(FAT) != null) {
            fatValue = Integer.parseInt(saved.getString(FAT));
        }
        if (saved.getString(NECK) != null) {
            neckValue = Integer.parseInt(saved.getString(NECK));
        }
        if (saved.getString(SHOULDERS) != null) {
            shoulderValue = Integer.parseInt(saved.getString(SHOULDERS));
        }
        if (saved.getString(PECTORAL) != null) {
            pectoralValue = Integer.parseInt(saved.getString(PECTORAL));
        }
        if (saved.getString(R_BICEPS) != null) {
            rightBicepsValue = Integer.parseInt(saved.getString(R_BICEPS));
        }
        if (saved.getString(L_BICEPS) != null) {
            leftBicepsValue = Integer.parseInt(saved.getString(L_BICEPS));
        }
        if (saved.getString(ABS) != null) {
            absValue = Integer.parseInt(saved.getString(ABS));
        }
        if (saved.getString(R_LEG) != null) {
            rightLegValue = Integer.parseInt(saved.getString(R_LEG));
        }
        if (saved.getString(L_LEG) != null) {
            leftLegValue = Integer.parseInt(saved.getString(L_LEG));
        }
        if (saved.getString(R_CALVES) != null) {
            rightCalvesValue = Integer.parseInt(saved.getString(R_CALVES));
        }
        if (saved.getString(L_CALVES) != null) {
            leftCalvesValue = Integer.parseInt(saved.getString(L_CALVES));
        }
    }

    private void initValueOfNumberPicker() {

        double min = 0;
        int max = 500;
        int maxfat = 200;
        double step = 0.5;
        valueNumber = getArrayWithSteps(min, max, step);
        valueNumberFat = getArrayWithSteps(min, maxfat, step);

        weightPicker.setMinValue(0);
        weightPicker.setMaxValue(max);
        weightPicker.setDisplayedValues(valueNumber);
        weightPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        fatPicker.setMinValue(0);
        fatPicker.setMaxValue(maxfat);
        fatPicker.setDisplayedValues(valueNumberFat);
        fatPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        neckPicker.setMinValue(0);
        neckPicker.setMaxValue(max);
        neckPicker.setDisplayedValues(valueNumber);
        neckPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        pectoralPicker.setMinValue(0);
        pectoralPicker.setMaxValue(max);
        pectoralPicker.setDisplayedValues(valueNumber);
        pectoralPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        shoulderPicker.setMinValue(0);
        shoulderPicker.setMaxValue(max);
        shoulderPicker.setDisplayedValues(valueNumber);
        shoulderPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftBicepsPicker.setMinValue(0);
        leftBicepsPicker.setMaxValue(max);
        leftBicepsPicker.setDisplayedValues(valueNumber);
        leftBicepsPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightBicepsPicker.setMinValue(0);
        rightBicepsPicker.setMaxValue(max);
        rightBicepsPicker.setDisplayedValues(valueNumber);
        rightBicepsPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        absPicker.setMinValue(0);
        absPicker.setMaxValue(max);
        absPicker.setDisplayedValues(valueNumber);
        absPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftLegPicker.setMinValue(0);
        leftLegPicker.setMaxValue(max);
        leftLegPicker.setDisplayedValues(valueNumber);
        leftLegPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightLegPicker.setMinValue(0);
        rightLegPicker.setMaxValue(max);
        rightLegPicker.setDisplayedValues(valueNumber);
        rightLegPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        leftCalvesPicker.setMinValue(0);
        leftCalvesPicker.setMaxValue(max);
        leftCalvesPicker.setDisplayedValues(valueNumber);
        leftCalvesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        rightCalvesPicker.setMinValue(0);
        rightCalvesPicker.setMaxValue(max);
        rightCalvesPicker.setDisplayedValues(valueNumber);
        rightCalvesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
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
        if (weightValue != -1) {
            weightPicker.setValue(weightValue);
        } else weightPicker.setValue(getPozValue(user.getWeight()));

        if (fatValue != -1) {
            fatPicker.setValue(fatValue);
        } else fatPicker.setValue(getPozValue(user.getFat()));

        if (neckValue != -1) {
            neckPicker.setValue(neckValue);
        } else neckPicker.setValue(getPozValue(user.getNeck()));

        if (pectoralValue != -1) {
            pectoralPicker.setValue(pectoralValue);
        } else pectoralPicker.setValue(getPozValue(user.getPectoral()));

        if (shoulderValue != -1) {
            shoulderPicker.setValue(shoulderValue);
        } else shoulderPicker.setValue(getPozValue(user.getShoulder()));

        if (leftBicepsValue != -1) {
            leftBicepsPicker.setValue(leftBicepsValue);
        } else leftBicepsPicker.setValue(getPozValue(user.getLeftBiceps()));

        if (rightBicepsValue != -1) {
            rightBicepsPicker.setValue(rightBicepsValue);
        } else rightBicepsPicker.setValue(getPozValue(user.getRightBiceps()));

        if (absValue != -1) {
            absPicker.setValue(absValue);
        } else absPicker.setValue(getPozValue(user.getAbs()));

        if (leftLegValue != -1) {
            leftLegPicker.setValue(leftLegValue);
        } else leftLegPicker.setValue(getPozValue(user.getLeftLeg()));

        if (rightLegValue != -1) {
            rightLegPicker.setValue(rightLegValue);
        } else rightLegPicker.setValue(getPozValue(user.getRightLeg()));

        if (leftCalvesValue != -1) {
            leftCalvesPicker.setValue(leftCalvesValue);
        } else leftCalvesPicker.setValue(getPozValue(user.getLeftCalves()));

        if (rightCalvesValue != -1) {
            rightCalvesPicker.setValue(rightCalvesValue);
        } else rightCalvesPicker.setValue(getPozValue(user.getRightCalves()));

        if (user.getDate() == null) {
            user.setDate(new Date());
        }
        if (date == null) {
            dateText.setText(sdf.format(user.getDate()));
        } else dateText.setText(date);
    }

    private User getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        User user;
        UserDao userDao;
        if (id > 0) {
            userDao = UserDao.getInstance(this, null);
            user = userDao.getById(id);
            setTitle(R.string.user_update_title);
        } else {
            userDao = UserDao.getInstance(this, null);
            user = userDao.getByOldDate();
            if (user != null) {
                user.setId(0);
                user.setDate(new Date());
            } else {
                user = new User();
                setDefaultParameters(user);
            }

            setTitle(R.string.user_create_title);
        }
        return user;
    }

    private void setDefaultParameters(User user) {
        user.setWeight(80);
        user.setFat(20);
        user.setNeck(20);
        user.setShoulder(100);
        user.setPectoral(100);
        user.setRightBiceps(30);
        user.setLeftBiceps(30);
        user.setAbs(60);
        user.setRightLeg(40);
        user.setLeftLeg(40);
        user.setRightCalves(35);
        user.setLeftCalves(35);
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
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }
    }

    private void save() {
            Intent intent = getIntentForResult();
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
    }

    private boolean itemIsNotChanged() {
        User changeableItem = getChangeableItem();
        if (changeableItem.getDate().compareTo(user.getDate()) != 0)
            return false;
        if (changeableItem.getWeight() != user.getWeight())
            return false;
        if (changeableItem.getHeight() != user.getHeight())
            return false;
        if (changeableItem.getFat() != user.getFat())
            return false;
        if (changeableItem.getNeck() != user.getNeck())
            return false;
        if (changeableItem.getShoulder() != user.getShoulder())
            return false;
        if (changeableItem.getPectoral() != user.getPectoral())
            return false;
        if (changeableItem.getRightBiceps() != user.getRightBiceps())
            return false;
        if (changeableItem.getLeftBiceps() != user.getLeftBiceps())
            return false;
        if (changeableItem.getAbs() != user.getAbs())
            return false;
        if (changeableItem.getRightLeg() != user.getRightLeg())
            return false;
        if (changeableItem.getLeftLeg() != user.getLeftLeg())
            return false;
        if (changeableItem.getLeftCalves() != user.getLeftCalves())
            return false;
        return changeableItem.getRightCalves() == user.getRightCalves();
    }

    private User getChangeableItem() {
        User changeableUser = new User();
        changeableUser.setId(user.getId());
        try {
            changeableUser.setDate(sdf.parse(dateText.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        changeableUser.setWeight(Double.parseDouble(valueNumber[weightPicker.getValue()]));
        changeableUser.setFat(Double.parseDouble(valueNumber[fatPicker.getValue()]));
        changeableUser.setNeck(Double.parseDouble(valueNumber[neckPicker.getValue()]));
        changeableUser.setShoulder(Double.parseDouble(valueNumber[shoulderPicker.getValue()]));
        changeableUser.setPectoral(Double.parseDouble(valueNumber[pectoralPicker.getValue()]));
        changeableUser.setRightBiceps(Double.parseDouble(valueNumber[rightBicepsPicker.getValue()]));
        changeableUser.setLeftBiceps(Double.parseDouble(valueNumber[leftBicepsPicker.getValue()]));
        changeableUser.setAbs(Double.parseDouble(valueNumber[absPicker.getValue()]));
        changeableUser.setRightLeg(Double.parseDouble(valueNumber[rightLegPicker.getValue()]));
        changeableUser.setLeftLeg(Double.parseDouble(valueNumber[leftLegPicker.getValue()]));
        changeableUser.setLeftCalves(Double.parseDouble(valueNumber[leftCalvesPicker.getValue()]));
        changeableUser.setRightCalves(Double.parseDouble(valueNumber[rightCalvesPicker.getValue()]));
        return changeableUser;
    }

    private Intent getIntentForResult() {
        User user = getChangeableItem();
        Intent intent = new Intent();
        intent.putExtra(ID, user.getId());
        intent.putExtra(START_DATA, user.getStringFormatDate());
        intent.putExtra(WEIGHT, user.getWeight());
        intent.putExtra(FAT, user.getFat());
        intent.putExtra(NECK, user.getNeck());
        intent.putExtra(SHOULDERS, user.getShoulder());
        intent.putExtra(PECTORAL, user.getPectoral());
        intent.putExtra(R_BICEPS, user.getRightBiceps());
        intent.putExtra(L_BICEPS, user.getLeftBiceps());
        intent.putExtra(ABS, user.getAbs());
        intent.putExtra(L_LEG, user.getLeftLeg());
        intent.putExtra(R_LEG, user.getRightLeg());
        intent.putExtra(L_CALVES, user.getLeftCalves());
        intent.putExtra(R_CALVES, user.getRightCalves());
        return intent;
    }

    private void update() {
        Intent intent = getIntentForResult();
        intent.putExtra(UPDATE_DELETE, UPDATE);
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
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
        return super.onOptionsItemSelected(item);
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
        outState.putString(WEIGHT, String.valueOf(weightPicker.getValue()));
        outState.putString(FAT, String.valueOf(fatPicker.getValue()));
        outState.putString(NECK, String.valueOf(neckPicker.getValue()));
        outState.putString(SHOULDERS, String.valueOf(shoulderPicker.getValue()));
        outState.putString(PECTORAL, String.valueOf(pectoralPicker.getValue()));
        outState.putString(R_BICEPS, String.valueOf(rightBicepsPicker.getValue()));
        outState.putString(L_BICEPS, String.valueOf(leftBicepsPicker.getValue()));
        outState.putString(ABS, String.valueOf(absPicker.getValue()));
        outState.putString(R_LEG, String.valueOf(rightLegPicker.getValue()));
        outState.putString(L_LEG, String.valueOf(leftLegPicker.getValue()));
        outState.putString(R_CALVES, String.valueOf(rightCalvesPicker.getValue()));
        outState.putString(L_CALVES, String.valueOf(leftCalvesPicker.getValue()));
        outState.putString(START_DATA, dateText.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
