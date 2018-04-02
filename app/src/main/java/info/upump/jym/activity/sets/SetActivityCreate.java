package info.upump.jym.activity.sets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Set;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.SetDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.TypeMuscle;

import static info.upump.jym.activity.constant.Constants.ID;

public class SetActivityCreate extends AppCompatActivity {
    private Sets sets;
    private EditText weight, reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sets = getItemFromIntent();

        weight = findViewById(R.id.set_create_activity_weight_edit_weight);
        reps = findViewById(R.id.set_create_activity_label_weight_edit_reps);
        createView();

    }

    private void createView() {
        if(sets.getId()>0){
            weight.setText(String.valueOf(sets.getWeight()));
            reps.setText(String.valueOf(sets.getReps()));
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
        return super.onOptionsItemSelected(item);
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
                    if(sets.getId()>0) {
                        update();
                    }else save();


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
        if (weight.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reps.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        Sets changeableItem = getChangeableItem();
        long id = setDao.create(changeableItem);
        if (id != -1) {
            sets.setId(id);
            Toast.makeText(this, "времен, программа сохранена", Toast.LENGTH_SHORT).show();
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
        if(!weight.getText().toString().isEmpty()){
            changeableSets.setWeight(Double.parseDouble(weight.getText().toString()));
        }else changeableSets.setWeight(0);
        if(!reps.getText().toString().isEmpty()){
            changeableSets.setReps(Integer.parseInt(reps.getText().toString()));
        }else changeableSets.setReps(0);
        changeableSets.setParentId(sets.getParentId());

        return changeableSets;
    }


    private void update() {
        SetDao setDao = new SetDao(this);
        if (weight.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reps.getText().toString().trim().isEmpty()) {
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


}
