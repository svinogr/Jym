package info.upump.jym.fragments.user;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.user.UserCreateActivity;
import info.upump.jym.activity.user.UserDetailActivity;
import info.upump.jym.activity.user.UserGraphActivity;
import info.upump.jym.adapters.UserAdapter;
import info.upump.jym.bd.UserDao;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.User;
import info.upump.jym.fragments.cycle.CRUD;
import info.upump.jym.loaders.ASTUser;

import static android.app.Activity.RESULT_OK;
import static info.upump.jym.activity.constant.Constants.ABS;
import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.FAT;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.L_BICEPS;
import static info.upump.jym.activity.constant.Constants.L_CALVES;
import static info.upump.jym.activity.constant.Constants.L_LEG;
import static info.upump.jym.activity.constant.Constants.NECK;
import static info.upump.jym.activity.constant.Constants.PECTORAL;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
import static info.upump.jym.activity.constant.Constants.R_BICEPS;
import static info.upump.jym.activity.constant.Constants.R_CALVES;
import static info.upump.jym.activity.constant.Constants.R_LEG;
import static info.upump.jym.activity.constant.Constants.SHOULDERS;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;
import static info.upump.jym.activity.constant.Constants.WEIGHT;


public class UserFragment extends Fragment implements View.OnClickListener, CRUD<User> {
    protected ITitleble iTitleble;
    protected RecyclerView recyclerView;
    protected UserAdapter userAdapter;
    protected List<User> userList = new ArrayList<>();
    protected FloatingActionButton addFab;
    private ASTUser astUser;
    private int index = -1;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getActivity(), R.string.toast_user_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                User user = (User) msg.obj;
                update(user);
            }

            if (msg.what == ERROR) {
                long id = ((Sets) msg.obj).getId();
                Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                //  insertDeletedItem(id);
            }

       /*     if (msg.what == CLEAR) {
                setsList.clear();
                setsAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), R.string.toast_exercise_delete_sets, Toast.LENGTH_SHORT).show();

            }*/

            if (msg.what == CREATE) {
                System.out.println(3);
                addItems((User) msg.obj);
            }
        }
    };

    private void addItems(User user) {
        userList.add(user);

        sortListByDate(userList);
        index = userList.indexOf(user);
        userAdapter.notifyItemInserted(index);
        userAdapter.notifyItemRangeChanged(index, userList.size()-1);

        recyclerView.smoothScrollToPosition(userList.size() - 1);
        Toast.makeText(getContext(), R.string.toast_user_saved, Toast.LENGTH_SHORT).show();
    }

    public UserFragment() {
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
        createAsyncTask();
        sortListByDate(userList);
        createAdapter();
    }

    private void createAsyncTask() {
        astUser = new ASTUser(getContext());
        astUser.execute();
        try {
            userList = astUser.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        userAdapter = new UserAdapter(userList, this);
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
        iTitleble = (ITitleble) context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_fragment_fab_add:
                addItem();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_menu_graph) {
            if (userList.size() < 2) {
                Toast.makeText(getContext(), R.string.toast_user_warning, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = UserGraphActivity.createIntent(getContext());
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
        //TODO сделать вызов для резалт активити, типа красиво вставляем
        Intent intent = UserCreateActivity.createIntent(getContext(), new User());
        startActivityForResult(intent, REQUEST_CODE_CREATE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void sortListByDate(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }


    @Override
    public void createIntentForResult(ActivityOptions activityOptions, User user) {
        Intent intent = UserDetailActivity.createIntent(getContext(), user);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CREATE:
                    addNewItem(data);
                    break;
                case REQUEST_CODE_CHANGE_OPEN:

                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    long id = data.getLongExtra(ID, 0);
                    switch (changeOrDelete) {
                        case UPDATE:
                            updateInnerItem(id);
                            break;
                        case DELETE:
                            deleteInnerItem(id);
                            break;
                    }
                    break;
            }
        }
    }

    private void update(User user) {
        int index = -1;
        long id = user.getId();
        boolean f = false;
        for (User userUpdate : userList) {
            if (userUpdate.getId() == id) {
                index = userList.indexOf(userUpdate);
                if ((userUpdate.getDate().compareTo(user.getDate()) !=0) ) {
                    f = true;
                }
                break;
            }
        }

        if (index != -1) {
            userList.set(index, user);
            userAdapter.notifyItemChanged(index);
            recyclerView.smoothScrollToPosition(index);
            Toast.makeText(getContext(), R.string.toast_user_update, Toast.LENGTH_SHORT).show();
        }
        if (f) {
            sortListByDate(userList);
            index = userList.indexOf(user);

            if (index != -1) {
                userList.set(index, user);
                userAdapter.notifyItemChanged(index);
                recyclerView.smoothScrollToPosition(index);
            }
        }

    }

    private void updateInnerItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = UserDao.getInstance(getContext(), null);
                User user = userDao.getById(id);
                if (user != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, user), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR), 0);
            }
        });
        thread.start();
    }

    private void deleteInnerItem(final long id) {
        for (User user : userList) {
            if (user.getId() == id) {
                index = userList.indexOf(user);
                break;
            }
        }
        if (index != -1) {
            userList.remove(index);
            userAdapter.notifyItemRemoved(index);
            userAdapter.notifyItemRangeChanged(index, userList.size());
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = UserDao.getInstance(getContext(), null);
                User user = new User();
                user.setId(id);
                boolean id = userDao.delete(user);
                if (id) {
                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, user), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR), 0);
            }
        });
        thread.start();

    }

    private void addNewItem(Intent data) {
        final User user = new User();

        user.setDate(data.getStringExtra(START_DATA));
        user.setWeight(data.getDoubleExtra(WEIGHT, 0));
        user.setFat(data.getDoubleExtra(FAT, 0));
        user.setNeck(data.getDoubleExtra(NECK, 0));
        user.setShoulder(data.getDoubleExtra(SHOULDERS, 0));
        user.setPectoral(data.getDoubleExtra(PECTORAL, 0));
        user.setRightBiceps(data.getDoubleExtra(R_BICEPS, 0));
        user.setLeftBiceps(data.getDoubleExtra(L_BICEPS, 0));
        user.setAbs(data.getDoubleExtra(ABS, 0));
        user.setRightLeg(data.getDoubleExtra(R_LEG, 0));
        user.setLeftLeg(data.getDoubleExtra(L_LEG, 0));
        user.setLeftCalves(data.getDoubleExtra(R_CALVES, 0));
        user.setRightCalves(data.getDoubleExtra(L_CALVES, 0));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = UserDao.getInstance(getContext(), null);
                long id = userDao.create(user);
                if (id != -1) {
                    user.setId(id);
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, user), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR), 0);
            }
        });
        thread.start();
    }

}
