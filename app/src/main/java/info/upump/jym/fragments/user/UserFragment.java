package info.upump.jym.fragments.user;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.user.UserCreateActivity;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.adapters.UserAdapter;
import info.upump.jym.entity.User;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.UserFragmentLoader;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;

public class UserFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<User>>, View.OnClickListener {
    protected ITitleble iTitleble;
    protected RecyclerView recyclerView;
    protected UserAdapter userAdapter;
    protected List<User> userList = new ArrayList<>();
    protected FloatingActionButton addFab;


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
        addFab = inflate.findViewById(R.id.user_fragment_fab_add);
        recyclerView = inflate.findViewById(R.id.user_fragment_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);
        addFab.setOnClickListener(this);
        setTitle();
        setFab();

        return inflate;
    }

    protected void setTitle() {
        iTitleble.setTitle(getResources().getString(R.string.title_user_fragment));
    }


    protected void createAdapter() {
        userAdapter = new UserAdapter(userList);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        iTitleble = (ITitleble) context;
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        UserFragmentLoader userFragmentLoader = new UserFragmentLoader(getContext());
        return userFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        userList.clear();
        userList.addAll(data);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_fragment_fab_add:
                addItem();
                break;
        }

    }

    private void addItem() {
        //TODO сделать вызов для резалт активити, типа красиво вставляем
        Intent intent = UserCreateActivity.createIntent(getContext(), new User());
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         menu.clear();
         inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
       /* MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);*/

    }
}
