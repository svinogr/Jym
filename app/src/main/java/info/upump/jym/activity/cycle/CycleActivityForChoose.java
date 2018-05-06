package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.ASTCycle;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_DEFAULT_TYPE;

public class CycleActivityForChoose extends AppCompatActivity implements /*LoaderManager.LoaderCallbacks<List<Cycle>>,*/ IChooseItem<Cycle> {
    private RecyclerView recyclerView;
    private CycleAdapter cycleAdapter;
    private List<Cycle> cycleList = new ArrayList<>();
    private ASTCycle astCycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_for_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.cycle_fragment_title_add_cycle);

        createAsyncTask();
//        getSupportLoaderManager().initLoader(0, null, this);

        cycleAdapter = new CycleAdapter(cycleList, DEFAULT_TYPE_CHOOSE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.activity_cycle_for_choose_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cycleAdapter);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, CycleActivityForChoose.class);
        return intent;
    }

    private void createAsyncTask() {
        astCycle = new ASTCycle(this);
        astCycle.execute(LOADER_BY_DEFAULT_TYPE);
        try {
            cycleList = astCycle.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    public Loader<List<Cycle>> onCreateLoader(int id, Bundle args) {
        CycleFragmentLoader cycleFragmentLoader = new CycleFragmentLoader(this, LOADER_BY_DEFAULT_TYPE);
        return cycleFragmentLoader;
    }*/

 /*   @Override
    public void onLoadFinished(Loader<List<Cycle>> loader, List<Cycle> data) {
        cycleList.clear();
        cycleList.addAll(data);
        cycleAdapter.notifyDataSetChanged();
    }*/

  /*  @Override
    public void onLoaderReset(Loader<List<Cycle>> loader) {

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
          exit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void createIntentForChooseResult(Cycle cycle) {
        Intent intent = new Intent();
        intent.putExtra(ID, cycle.getId());
        setResult(RESULT_OK, intent);
        exit();
    }

    public void exit() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
