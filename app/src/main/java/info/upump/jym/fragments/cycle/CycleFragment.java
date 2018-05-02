package info.upump.jym.fragments.cycle;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.cycle.CycleActivityForChoose;
import info.upump.jym.activity.cycle.CycleCreateActivity;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.CycleFragmentLoader;

public class CycleFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Cycle>> {
    protected ITitleble iTitlable;
    protected RecyclerView recyclerView;
    protected CycleAdapter cycleAdapter;
    protected List<Cycle> cycleList = new ArrayList<>();
    protected FloatingActionButton addFab;

    public CycleFragment() {
    }

    public static CycleFragment newInstance() {
        CycleFragment fragment = new CycleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        createAdapter();
    }

    protected void createAdapter() {
        cycleAdapter = new CycleAdapter(cycleList, Constants.LOADER_BY_USER_TYPE);
    }

    protected void setTitle() {
        iTitlable.setTitle(getResources().getString(R.string.cycle_fragment_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setTitle();
        View inflate = inflater.inflate(R.layout.fragment_cycle, container, false);

        recyclerView = inflate.findViewById(R.id.cycle_fragment_recycler_view);
        addFab = inflate.findViewById(R.id.cycle_fragment_fab_add);
        setFab();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cycleAdapter);

        return inflate;
    }

    protected void setFab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (addFab.isShown()) {
                        addFab.hide();
                    }
                } else if (dy <= 0) {
                    // Scroll Up
                    if (!addFab.isShown()) {
                        addFab.show();
                    }
                }
            }
        });

        addFab.setOnClickListener(this);
    }

    @Override
    public Loader<List<Cycle>> onCreateLoader(int id, Bundle args) {
        CycleFragmentLoader cycleLoader = new CycleFragmentLoader(getContext(), Constants.LOADER_BY_USER_TYPE);
        return cycleLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Cycle>> loader, List<Cycle> data) {
        cycleList.clear();
        cycleList.addAll(data);
        cycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Cycle>> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cycle_fragment_fab_add:
                addItem();
                break;
        }
    }

    private void addItem() {
        String[] inputs = {getString(R.string.cycle_dialog_create_new), getString(R.string.cycle_dialog_сhoose)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cycle_dialog_title); // заголовок для диалога
        builder.setItems(inputs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                switch (item) {
                    case 0:
                        Intent intent0 = CycleCreateActivity.createIntent(getContext(), null);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = CycleActivityForChoose.createIntent(getContext());
                        startActivityForResult(intent1, Constants.REQUEST_CODE_CHOOSE);
                }
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        iTitlable = (ITitleble) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
