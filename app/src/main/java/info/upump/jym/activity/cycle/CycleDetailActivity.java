package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

public class CycleDetailActivity extends AppCompatActivity implements IChangeItem<Cycle> {
    protected ViewPager viewPager;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected ImageView imageView;
    protected Cycle cycle;
    protected Uri uriImage;
    protected PagerAdapterCycle pagerAdapterCycleEdit;
    protected CycleDao cycleDao;
    protected IDescriptionFragment iDescriptionFragment;
    protected IItemFragment iItemFragment;
    protected FloatingActionButton addFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cycle_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cycle_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        imageView = findViewById(R.id.exercise_activity_detail_edit_image_view);
        addFab =   findViewById(R.id.cycle_activity_detail_fab_main);
        setFabVisible(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPic = createIntentForGetPic();
                startActivityForResult(photoPic, Constants.REQUEST_CODE_GALLERY_PHOTO);
            }
        });
        collapsingToolbarLayout = findViewById(R.id.cycle_activity_detail_edit_collapsing);

        TabLayout tabLayout = findViewById(R.id.cycle_fragment_edit_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        cycle = getItemFromIntent();

        setPagerAdapter();

        viewPager.setAdapter(pagerAdapterCycleEdit);
        setPageTransform();

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
                uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
                cycle.setImage(uriImage.toString());
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setFabVisible(true);
                } else setFabVisible(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        createViewFrom(cycle);

    }

    protected void setFabVisible(boolean visible) {
        if (visible) {
            addFab.setVisibility(View.VISIBLE);
        } else  addFab.setVisibility(View.GONE);

    }

    protected void setPagerAdapter() {
        pagerAdapterCycleEdit = new PagerAdapterCycle(getSupportFragmentManager(), cycle);
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
        intent.putExtra(Constants.ID, cycle.getId());
        return intent;
    }

    private void createViewFrom(Cycle cycle) {
        if (cycle != null) {
            collapsingToolbarLayout.setTitle(cycle.getTitle());
        }

        if (cycle.getImage() != null) {
            setPic(Uri.parse(cycle.getImage()));
        }

    }

    private Cycle getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(Constants.ID, 0);
        Cycle cycle = null;
        CycleDao cycleDao = new CycleDao(this);
        cycle = cycleDao.getById(id);
        return cycle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_GALLERY_PHOTO:
                    uriImage = data.getData();
                    setPic(uriImage);
                    break;
                case Constants.REQUEST_CODE_CHOOSE:
                    iItemFragment.addChosenItem(data.getLongExtra(Constants.ID, 0));
                    break;
                case Constants.REQUEST_CODE_CREATE:
                    iItemFragment.addItem(data.getLongExtra(Constants.ID, 0));
                    break;
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
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
                Snackbar.make(this.imageView, "Удалить программу?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(cycle.getId());
                            }
                        }).show();
                break;
            case R.id.edit_menu_clear:
                Snackbar.make(this.imageView, "Очистить программу?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clear();
                            }
                        }).show();
                break;
        }

        if (item.getItemId() == android.R.id.home) {
            exit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exit();

    }

    protected void exit() {
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Сохранить изменения описания?");
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveOrUpdate();
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


    //delete all childs
    private void clear() {
        if (iItemFragment.clear()) {
            Toast.makeText(this, "Программа очищена", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Не удалось очистить", Toast.LENGTH_SHORT).show();
    }


    private boolean itemIsNotChanged() {
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


    protected void finishActivityWithAnimation() {
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
        cycleDao = getCycleDao();
        if (cycleDao.delete(cycle)) {
            Toast.makeText(this, "времен, программа  удалена", Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
            //exit();
        } else Toast.makeText(this, "времен, программа  не удалена", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setInterfaceForDescription(IDescriptionFragment interfaceForDescription) {
        this.iDescriptionFragment = interfaceForDescription;
    }

    @Override
    public void setInterfaceForItem(IItemFragment interfaceForItem) {
        this.iItemFragment = interfaceForItem;
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
            outState.putString(Constants.URI_IMG, uriImage.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }


}
