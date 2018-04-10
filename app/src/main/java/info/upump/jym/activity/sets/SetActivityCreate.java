package info.upump.jym.activity.sets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;

import static info.upump.jym.activity.constant.Constants.ID;

public class SetActivityCreate extends AppCompatActivity {
    private Sets sets;
    private NumberPicker weight, reps;
    private NumberPicker numberPicker;
    private String[] valuesForWeight;
    private int qSet = 1;
    private CardView cardView;
    private int[] arrayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weight = findViewById(R.id.numberPickerWeight);
        reps = findViewById(R.id.numberPickerReps);
        numberPicker = findViewById(R.id.numberPickerSets);
        cardView = findViewById(R.id.data_card3);

        sets = getItemFromIntent();
        double min = 0;
        int max = 200;
        double step = 1.25;

        valuesForWeight = getArrayWithSteps(min, max, step);
        weight.setMinValue(0);
        weight.setMaxValue(200);
        weight.setDisplayedValues(valuesForWeight);

        reps.setMaxValue(100);
        reps.setMinValue(0);

        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
        createView();

    }

    public String[] getArrayWithSteps(double iMinValue, int iMaxValue, double iStep) {
        int iStepsArray = 800; //get the lenght array that will return

        String[] arrayValues = new String[iStepsArray]; //Create array with length of iStepsArray

        for (int i = 0; i < iStepsArray; i++) {
            arrayValues[i] = String.valueOf(iMinValue + (i * iStep));
        }

        return arrayValues;
    }

    private void createView() {
        if (sets.getId() > 0) {
            int i = Arrays.asList(valuesForWeight).indexOf(String.valueOf(sets.getWeight()));
            weight.setValue(i);
            reps.setValue(sets.getReps());
        }
    }

    public static Intent createIntent(Context context, Sets sets) {
        Intent intent = new Intent(context, SetActivityCreate.class);
        intent.putExtra(ID, sets.getId());
        return intent;
    }

    private Sets getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        Sets sets;
        if (id > 0) {
            SetDao setDao = new SetDao(this);
            sets = setDao.getById(id);
            setTitle("Изменить подход");
            cardView.setVisibility(View.GONE);
        } else {
            sets = new Sets();
            setTitle("Новый подход");
        }
        System.out.println(sets);
        return sets;
    }

    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(ID, sets.getId());
        return intent;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        if (item.getItemId() == R.id.edit_menu_delete) {
            if (sets.getId() > 0) {
                switch (item.getItemId()) {
                    case R.id.edit_menu_delete:
                        Snackbar.make(reps, "Удалить подход?", Snackbar.LENGTH_LONG)
                                .setAction("Да", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        delete(sets.getId());

                                    }
                                }).show();
                        break;
                }
            } else Toast.makeText(this, "времен, подход  не сохранен", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(long id) {
        SetDao setDao = new SetDao(this);
        if (setDao.delete(sets)) {
            Toast.makeText(this, "времен, подход  удален", Toast.LENGTH_SHORT).show();
            //exit();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, подход  не удалено", Toast.LENGTH_SHORT).show();

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
                    if (sets.getId() > 0) {
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
        SetDao setDao = new SetDao(this);
     /*   if (weight.getValue() < 0) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reps.getValue() < 0) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }*/
        Sets changeableItem = getChangeableItem();
        qSet = numberPicker.getValue();
        long id = -1;
        for (int i = 0; i < qSet; i++) {

            id = setDao.create(changeableItem);
            if (i == 0) {
                sets.setId(id); // если делаем много одинаковых подходов то передаем только первый, чтоб получить остальные начиная с него ;)
            }
            System.out.println(id);
        }
        if (id != -1) {
            // sets.setId(id);
            Toast.makeText(this, "времен, подход сохранен", Toast.LENGTH_SHORT).show();
            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    private boolean itemIsNotChanged() {
        Sets changeableItem = getChangeableItem();
        System.out.println(changeableItem);
        if (changeableItem.getWeight() != sets.getWeight()) return false;
        if (changeableItem.getReps() != sets.getReps()) return false;
        return true;
    }

    private Sets getChangeableItem() {
        Sets changeableSets = new Sets();
        changeableSets.setId(sets.getId());
        changeableSets.setStartDate(new Date());
        changeableSets.setFinishDate(new Date());
        changeableSets.setWeight(Double.parseDouble(valuesForWeight[weight.getValue()]));
        changeableSets.setReps(reps.getValue());
        changeableSets.setParentId(sets.getParentId());
        return changeableSets;
    }


    private void update() {
        SetDao setDao = new SetDao(this);
        if (weight.getValue() < 0) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reps.getValue() < 0) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        Sets exerciseUpdate = getChangeableItem();
        boolean id = setDao.update(exerciseUpdate);
        if (id) {
            Toast.makeText(this, "времен, программа сохранена", Toast.LENGTH_SHORT).show();
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

}
