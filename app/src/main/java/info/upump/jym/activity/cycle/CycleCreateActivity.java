package info.upump.jym.activity.cycle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;
import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

import static info.upump.jym.activity.constant.Constants.ID;

public class CycleCreateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_CREATE_CYCLE = 1;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private TextView startDataLabel, finishDataLabel, startTextData, finishTextData;
    private EditText description, title;
    private Cycle cycle;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }
        }
    };
    private Uri uriImage;
    private String startData, finishData;
    private Cycle fromIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cycle_create_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cycle = getFromIntent();

        imageView = findViewById(R.id.cycle_create_activity_image_view);
        collapsingToolbarLayout = findViewById(R.id.cycle_create_activity_collapsing);
        startDataLabel = findViewById(R.id.content_cycle_create_activity_label_edit_start);
        finishDataLabel = findViewById(R.id.content_cycle_create_activity_label_edit_finish);
        startTextData = findViewById(R.id.content_cycle_create_activity_data_edit_start);
        finishTextData = findViewById(R.id.content_cycle_create_activity_data_edit_finish);
        description = findViewById(R.id.content_cycle_create_activity_edit_web);
        title = findViewById(R.id.content_cycle_create_activity_edit_title);

        startTextData.setOnClickListener(this);
        finishTextData.setOnClickListener(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPic = createIntentForGetPic();
                startActivityForResult(photoPic, Constants.REQUEST_CODE_GALLERY_PHOTO);
            }
        });
        startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finish_cycle);

        if (savedInstanceState != null) {
//            cycle.setStartDate(savedInstanceState.getString(Constants.START_DATA));
//            cycle.setFinishDate(savedInstanceState.getString(Constants.FINISH_DATA));
            if(savedInstanceState.getString(Constants.START_DATA) != null){
                startData = savedInstanceState.getString(Constants.START_DATA);
            }
            if(savedInstanceState.getString(Constants.FINISH_DATA) != null){
                finishData = savedInstanceState.getString(Constants.FINISH_DATA);
            }

            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
            //    cycle.setImage(savedInstanceState.getString(Constants.URI_IMG));
//                uriImage = Uri.parse(cycle.getImage());
                uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
            }
        }
//        else {
//            cycle.setStartDate(cycle.getStartDate());
//            cycle.setFinishDate(cycle.getFinishDate());
            // cycle.setTitle();
//        }


        System.out.println("onCrea " +cycle);
        createViewFrom(cycle);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(100);
                if ((title.getText().toString().trim()).isEmpty()) {
                    handler.sendMessageDelayed(handler.obtainMessage(100, title.getHint()), 250);
                } else handler.sendMessageDelayed(handler.obtainMessage(100, title.getText()), 250);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private RequestOptions getOptionsGlide() {
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .placeholder(R.drawable.view_place_holder_exercise)
                .error(R.drawable.iview_place_erore_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        return options;
    }

    private void setDefaultPic() {
        RequestOptions options = getOptionsGlide();
        int ident = getResources().getIdentifier(cycle.getDefaultImg(), "drawable", getPackageName());
        System.out.println(ident);
        Glide.with(this).load(ident).apply(options).into(imageView);
    }

    private void createViewFrom(Cycle cycle) {
        System.out.println("createViewFrom " +cycle);
        if (uriImage == null) {
            if (cycle.getDefaultImg() != null) {
                setDefaultPic();
            } else if (cycle.getImage() != null) {
                setPic(Uri.parse(cycle.getImage()));
            }
        } else setPic(uriImage);
      /*
        if (cycle.getImage() != null) {
            uri = Uri.parse(cycle.getImage().toString());
        }
        setPic(uri);*/
        title.setText(cycle.getTitle());
        if (cycle.getTitle() == null) {
            collapsingToolbarLayout.setTitle(title.getHint().toString());
        } else collapsingToolbarLayout.setTitle(cycle.getTitle());

        if(startData != null){
            startTextData.setText(startData);
        } else startTextData.setText(cycle.getStartStringFormatDate());
        if(finishData != null){
            finishTextData.setText(finishData);
        } else finishTextData.setText(cycle.getFinishStringFormatDate());

        description.setText(cycle.getComment());
    }

    private void setPic(Uri uri) {
        RequestOptions options = getOptionsGlide();
        Glide.with(this).load(uri).apply(options).into(imageView);
    }


    private Intent createIntentForGetPic() {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
        }
        intent.setType("image/*");
        return intent;
    }

    public static Intent createIntent(Context context, Cycle cycle) {
        Intent intent = new Intent(context, CycleCreateActivity.class);
        if (cycle != null) {
            intent.putExtra(ID, cycle.getId());
        }
        return intent;
    }

    @Override
    public void onClick(View v) {
        Calendar newCalendar = null;
        DatePickerDialog j = null;
        switch (v.getId()) {
            case R.id.content_cycle_create_activity_data_edit_start:
                newCalendar = Calendar.getInstance();
                newCalendar.setTime(cycle.getStartDate());
                j = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
//                        cycle.setStartDate(newDate.getTime());
                        Cycle dataCycle = new Cycle();
                        dataCycle.setStartDate(newDate.getTime());
                        startTextData.setText(dataCycle.getStartStringFormatDate());
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                j.show();
                break;
            case R.id.content_cycle_create_activity_data_edit_finish:
                newCalendar = Calendar.getInstance();
                newCalendar.setTime(cycle.getFinishDate());
                j = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
//                        cycle.setFinishDate(newDate.getTime());
                        Cycle dataCycle = new Cycle();
                        dataCycle.setStartDate(newDate.getTime());
                        finishTextData.setText(dataCycle.getFinishStringFormatDate());
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                j.show();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult  " + requestCode + "  " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_GALLERY_PHOTO:
                    uriImage = data.getData();
                    cycle.setImage(uriImage.toString());
                    setPic(Uri.parse(cycle.getImage()));
                    break;
            }
        }

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

    private void exit() {
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (cycle.getId() != 0) {
                        update();
                    } else save();
                }
            });
            ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }

    }

    private boolean itemIsNotChanged() {
        Cycle changeableItem = getChangeableItem();
        System.out.println("ch1 "+changeableItem);
        System.out.println("c1 "+cycle);
        if (!changeableItem.getTitle().equals(cycle.getTitle())) return false;
        if (!changeableItem.getComment().equals(cycle.getComment())) return false;
        System.out.println(changeableItem.getStartStringFormatDate().equals(cycle.getStartStringFormatDate()));
        if (!changeableItem.getStartStringFormatDate().equals(cycle.getStartStringFormatDate()))
            return false;
        if (!changeableItem.getFinishStringFormatDate().equals(cycle.getFinishStringFormatDate()))
            return false;

        if (uriImage != null) return false;
        System.out.println("c "+cycle);
        System.out.println("cha "+changeableItem);
        return true;
    }

    private Cycle getChangeableItem() {
        Cycle changeable = new Cycle();
        changeable.setId(cycle.getId());
        changeable.setTitle(title.getText().toString());
        changeable.setComment(description.getText().toString());
        changeable.setStartDate(startTextData.getText().toString());
        changeable.setFinishDate(finishTextData.getText().toString());
        if(uriImage != null){
            changeable.setImage(uriImage.toString());
            changeable.setDefaultImg(null);
        }
        return changeable;
    }

    private void update() {
        CycleDao cycleDao = new CycleDao(this);
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Cycle cycleUpdate = getChangeableItem();
        if (cycleDao.update(cycleUpdate)) {
            Toast.makeText(this, R.string.toast_cycle_update, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_update, Toast.LENGTH_SHORT).show();
    }

    private void save() {
        CycleDao cycleDao = new CycleDao(this);
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Cycle cycleSave = getChangeableItem();
       /* cycle.setTitle(title.getText().toString());
        cycle.setComment(description.getText().toString());
        cycle.setStartDate(startTextData.getText().toString());
        cycle.setFinishDate(finishTextData.getText().toString());
*/
        if (cycleDao.create(cycleSave) != -1) {
            Toast.makeText(this, R.string.toast_cycle_saved, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_save, Toast.LENGTH_SHORT).show();
    }


    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.START_DATA, startTextData.getText().toString());
        outState.putString(Constants.FINISH_DATA, finishTextData.getText().toString());
        if (uriImage != null) {
//            outState.putString(Constants.URI_IMG, cycle.getImage());
            outState.putString(Constants.URI_IMG, uriImage.toString());
        }
    }

    public Cycle getFromIntent() {
        long id = getIntent().getLongExtra(ID, 0);
        Cycle cycle;
        if (id != 0) {
            CycleDao cycleDao = new CycleDao(this);
            cycle = cycleDao.getById(id);
        } else {
            cycle = new Cycle();
            cycle.setStartDate(new Date());
            cycle.setFinishDate(new Date());
            cycle.setComment("");
            cycle.setTitle("");
        }
        System.out.println(cycle);
        return cycle;
    }
}
