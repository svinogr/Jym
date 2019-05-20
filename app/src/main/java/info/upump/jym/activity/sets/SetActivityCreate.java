package info.upump.jym.activity.sets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Sets;

import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.QUANTITY;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class SetActivityCreate extends AppCompatActivity {
    private static final String WEIGHT = "weight";
    private static final String REPS = "reps";
    private static final String QUANTITY_SETS = "quantity";
    private Sets sets;
    private NumberPicker weight, reps;
    private NumberPicker quantitySets;
    private int weightValue = -1, repsValue = -1;
    private String[] valuesForWeight;
    private int quantitySetsValue = 1;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weight = findViewById(R.id.numberPickerWeight);
        reps = findViewById(R.id.numberPickerReps);
        quantitySets = findViewById(R.id.numberPickerSets);
        cardView = findViewById(R.id.data_card3);

        sets = getItemFromIntent();
        double min = 0;
        int max = 200;
        double step = 1.25;

        valuesForWeight = getArrayWithSteps(min, max, step);
        weight.setMinValue(0);
        weight.setMaxValue(200);
        weight.setDisplayedValues(valuesForWeight);
        weight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        reps.setMaxValue(100);
        reps.setMinValue(0);
        reps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        quantitySets.setMaxValue(20);
        quantitySets.setMinValue(1);
        quantitySets.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(WEIGHT) != null) {
                weightValue = Integer.parseInt(savedInstanceState.getString(WEIGHT));
            }
            if (savedInstanceState.getString(REPS) != null) {
                repsValue = Integer.parseInt(savedInstanceState.getString(REPS));
            }
            if (savedInstanceState.getString(REPS) != null) {
                quantitySetsValue = Integer.parseInt(savedInstanceState.getString(QUANTITY_SETS));
            }
        }
        createView();
    }

    public String[] getArrayWithSteps(double iMinValue, int iMaxValue, double iStep) {
        int iStepsArray = 800;

        String[] arrayValues = new String[iStepsArray];

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
        if (weightValue != -1) {
            weight.setValue(weightValue);
        }
        if (repsValue != -1) {
            reps.setValue(repsValue);
        }
        if (quantitySetsValue > 1) {
            quantitySets.setValue(quantitySetsValue);
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
            SetDao setDao = SetDao.getInstance(this, null);
            sets = setDao.getById(id);
            setTitle(R.string.set_update_title);
            cardView.setVisibility(View.INVISIBLE);
        } else {
            sets = new Sets();
            setTitle(R.string.set_create_title);
        }
        return sets;
    }

    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(ID, sets.getId());
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
            if (sets.getId() > 0) {
                switch (item.getItemId()) {
                    case R.id.edit_menu_delete:
                        Snackbar.make(reps, R.string.snack_delete, Snackbar.LENGTH_LONG)
                                .setAction(R.string.yes, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        delete(sets.getId());

                                    }
                                }).show();
                        break;
                }
            } else Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(long id) {
        Intent intent = new Intent();
        intent.putExtra(ID, id);
        intent.putExtra(UPDATE_DELETE, DELETE);
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
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
        Sets changeableItem = getChangeableItem();
        quantitySetsValue = quantitySets.getValue();

        Intent intent = new Intent();
        intent.putExtra(WEIGHT, changeableItem.getWeight());
        intent.putExtra(REPS, changeableItem.getReps());
        intent.putExtra(QUANTITY, quantitySetsValue);
        /*
        long id = -1;
        for (int i = 0; i < quantitySetsValue; i++) {

            id = setDao.create(changeableItem);
            if (i == 0) {
                sets.setId(id);
            }
        }*/
//        if (id != -1) {
//            Toast.makeText(this, R.string.toast_set_saved, Toast.LENGTH_SHORT).show();
//            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
//        } else Toast.makeText(this,R.string.toast_dont_save, Toast.LENGTH_SHORT).show();
    }

    private boolean itemIsNotChanged() {
        Sets changeableItem = getChangeableItem();
        if (changeableItem.getWeight() != sets.getWeight()) return false;
        return changeableItem.getReps() == sets.getReps();
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
        if (weight.getValue() < 0) {
            Toast.makeText(this, R.string.toast_not_corect_value, Toast.LENGTH_SHORT).show();
            return;
        }
        if (reps.getValue() < 0) {
            Toast.makeText(this, R.string.toast_not_corect_value, Toast.LENGTH_SHORT).show();
            return;
        }
        Sets setsUpdate = getChangeableItem();

        Intent intent = new Intent();
        intent.putExtra(UPDATE_DELETE, UPDATE);
        intent.putExtra(WEIGHT, setsUpdate.getWeight());
        intent.putExtra(ID, setsUpdate.getId());
        intent.putExtra(REPS, setsUpdate.getReps());
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sets.getId()!=0) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.edit_set_menu, menu);
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(WEIGHT, String.valueOf(weight.getValue()));
        outState.putString(REPS, String.valueOf(reps.getValue()));
        outState.putString(QUANTITY_SETS, String.valueOf(quantitySets.getValue()));
        super.onSaveInstanceState(outState);
    }
}
