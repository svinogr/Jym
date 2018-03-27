package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

public class CycleDetailActivity extends AppCompatActivity implements IChangeItem<Cycle> {
    private static final String ID_CYCLE = "id";
    private static final int REQUEST_CODE_GALLERY_PHOTO = 1;
    private static final String URI_IMG = "uri";
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private Cycle cycle;
    private Uri uriImage;
    private PagerAdapterCycle pagerAdapterCycleEdit;
    private CycleDao cycleDao;
    private IDescriptionFragment iDescriptionFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(URI_IMG) != null) {
                uriImage = Uri.parse(savedInstanceState.getString(URI_IMG));
            }
        }
        setContentView(R.layout.activity_cycle_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cycle_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        imageView = findViewById(R.id.exercise_activity_detail_edit_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPic = createIntentForGetPic();
                startActivityForResult(photoPic, REQUEST_CODE_GALLERY_PHOTO);
            }
        });
        collapsingToolbarLayout = findViewById(R.id.cycle_activity_detail_edit_collapsing);

        TabLayout tabLayout = findViewById(R.id.cycle_fragment_edit_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        cycle = getCycleFromIntent();

        pagerAdapterCycleEdit = new PagerAdapterCycle(getSupportFragmentManager(), cycle);
        viewPager.setAdapter(pagerAdapterCycleEdit);
        setPageTransform();

        createViewFrom(cycle);

    }

    private void setPageTransform() {
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();
                final float MIN_SCALE = 0.85f;
                float MIN_ALPHA = 0.5f;

                if (position < -1)

                { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1)

                { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else

                { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }


        });
    }

    public static Intent createIntent(Context context, Cycle cycle) {
        Intent intent = new Intent(context, CycleDetailActivity.class);
        intent.putExtra(ID_CYCLE, cycle.getId());
        return intent;
    }

    private void createViewFrom(Cycle cycle) {
        if (cycle != null) {
            collapsingToolbarLayout.setTitle(cycle.getTitle());
            if (cycle.getImage() != null) {
                setPic(Uri.parse(cycle.getImage()));
            }
        }


    }

    private Cycle getCycleFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID_CYCLE, 0);
        Cycle cycle = new Cycle();
        if(uriImage != null){
            cycle.setImage(uriImage.toString());
        }
        if (id >0) {
            CycleDao cycleDao = new CycleDao(this);
            cycle = cycleDao.getById(id);
        } else {
            cycle.setTitle(" ");
            cycle.setComment("");
            cycle.setStartDate(new Date());
            cycle.setFinishDate(new Date());
        }
        return cycle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY_PHOTO:
                    uriImage = data.getData();
                    setPic(uriImage);
            }
        }

    }


    private void setPic(Uri uri) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
          exit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
      exit();

    }
    private void exit(){
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Сохранить изменения описания?");
            ad.setPositiveButton("Да и выйти", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveOrUpdate();
                }
            });
            ad.setNegativeButton("Нет и выйти", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishActivityWithAnimation();
                }
            });
            ad.show();

        }
    }
    private void saveOrUpdate() {
        Cycle sOu = (Cycle) iDescriptionFragment.getChangeableItem();
        if (sOu.getTitle().trim().isEmpty()) {
            Toast.makeText(this, "необходимо имя", Toast.LENGTH_SHORT).show();
            return;
        }
        cycle.setComment(sOu.getComment());
        cycle.setTitle(sOu.getTitle());
        cycle.setFinishDate(sOu.getFinishDate());
        cycle.setStartDate(sOu.getStartDate());
        if (uriImage != null) {
            System.out.println("юрай имидж" +
                    "" + uriImage);
            cycle.setImage(uriImage.toString());
        }
        if (cycle.getId() > 0) {
            update(cycle);
        } else save(cycle);
    }

    @Override
    public void save(Cycle cycle) {
        cycleDao = getCycleDao();
        if (cycleDao.create(cycle) != -1) {
            Toast.makeText(this, "времен, программа сохранена", Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }


    private boolean itemIsNotChanged() {
        System.out.println(cycle);
        Cycle changeableItem = (Cycle) iDescriptionFragment.getChangeableItem();
        System.out.println(changeableItem);
        if (!changeableItem.getTitle().equals(cycle.getTitle())) return false;
        if (!changeableItem.getComment().equals(cycle.getComment())) return false;
        if (!changeableItem.getStartStringFormatDate().equals(cycle.getStartStringFormatDate()))
            return false;
        if (!changeableItem.getFinishStringFormatDate().equals(cycle.getFinishStringFormatDate()))
            return false;

        if (uriImage != null) return false;
        // if (!getChangeableItem.getImage().equals(cycle.getImage())) return false;

        return true;
    }


    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }


    @Override
    public void update(Cycle cycleSave) {
        cycleDao = getCycleDao();
        if (cycleDao.update(cycleSave)) {
            Toast.makeText(this, "времен, программа изменена", Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void setInterfaceForDescription(IDescriptionFragment interfaceForDescription) {
        this.iDescriptionFragment = interfaceForDescription;
    }

    private CycleDao getCycleDao() {
        if (cycleDao == null) {
            cycleDao = new CycleDao(this);
        }
        return cycleDao;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uriImage != null) {
            outState.putString(URI_IMG, uriImage.toString());
        }
    }


}
