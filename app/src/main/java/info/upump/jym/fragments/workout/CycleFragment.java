package info.upump.jym.fragments.workout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.ITitlable;
import info.upump.jym.R;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.CycleLoader;

public class CycleFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Cycle>> {
    private ITitlable iTitlable;
    private RecyclerView recyclerView;
    private CycleAdapter cycleAdapter;
    private List<Cycle> cycleList = new ArrayList<>();
    public CycleFragment() {
        // Required empty public constructor
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
        cycleAdapter = new CycleAdapter(cycleList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle, container, false);
        recyclerView = inflate.findViewById(R.id.cycle_fragment_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(cycleAdapter);

        iTitlable.setTitle(getResources().getString(R.string.cycle_title));
        return inflate;
    }

    @Override
    public Loader<List<Cycle>> onCreateLoader(int id, Bundle args) {
        CycleLoader cycleLoader = new CycleLoader(getContext());
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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        iTitlable = (ITitlable) context;
    }
}