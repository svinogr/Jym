package info.upump.jym.fragments.cycle;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.cycle.CycleActivityForChoose;
import info.upump.jym.activity.cycle.CycleCreateActivity;
import info.upump.jym.activity.cycle.CycleDetailActivity;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.ASTCycle;

import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_IMAGE;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.FINISH_DATA;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.IMAGE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CREATE;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class CycleFragment extends Fragment implements View.OnClickListener, CRUD<Cycle> {
    protected ITitleble iTitlable;
    protected RecyclerView recyclerView;
    protected CycleAdapter cycleAdapter;
    protected List<Cycle> cycleList = new ArrayList<>();
    private int index = -1;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected FloatingActionButton addFab;
    protected static ASTCycle astCycle;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getContext(), R.string.toast_cycle_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                Cycle cycle = (Cycle) msg.obj;
                long id = cycle.getId();
                int index = -1;
                for (Cycle delCycle : cycleList) {
                    if (delCycle.getId() == id) {
                        index = cycleList.indexOf(delCycle);
                        break;
                    }
                }
                if (index != -1) {
                    cycleList.set(index, cycle);
                    cycleAdapter.notifyItemChanged(index);
                    recyclerView.smoothScrollToPosition(index);
                }
            }

            if (msg.what == ERROR) {
                long id = (long) msg.obj;
                Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                insertDeletedItem(id);
            }


            if (msg.what == CREATE) {
                Cycle cycle = (Cycle) msg.obj;
                cycleList.add(cycle);
                int position = cycleList.size() - 1;
                cycleAdapter.notifyItemInserted(position);
                recyclerView.smoothScrollToPosition(position);
            }
        }
    };

    private void insertDeletedItem(long id) {
        CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
        Cycle cycle = cycleDao.getById(id);
        cycleList.add(index, cycle);
        cycleAdapter.notifyItemInserted(index);
    }

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
        createAsyncTask();
        createAdapter();
    }

    protected void createAsyncTask() {
        astCycle = new ASTCycle(getContext());
        astCycle.execute(Constants.LOADER_BY_USER_TYPE);
        try {
            cycleList = astCycle.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected void createAdapter() {
        cycleAdapter = new CycleAdapter(cycleList, Constants.LOADER_BY_USER_TYPE, this);
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
        swipeRefreshLayout = inflate.findViewById(R.id.refresh);
        swipeRefreshLayout.setEnabled(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cycle_fragment_fab_add:
                createItem();
                break;
        }
    }

    private void createItem() {
        String[] inputs = {getString(R.string.cycle_dialog_create_new), getString(R.string.cycle_dialog_сhoose)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cycle_dialog_title); // заголовок для диалога
        builder.setItems(inputs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                Intent intent;
                switch (item) {
                    case 0:
                        intent = CycleCreateActivity.createIntent(getContext(), null);
                        startActivityForResult(intent, REQUEST_CODE_CREATE);
                        break;
                    case 1:
                        intent = CycleActivityForChoose.createIntent(getContext());
                        startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
                }
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iTitlable = (ITitleble) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (requestCode == REQUEST_CODE_CHANGE_OPEN) {
                long id = data.getLongExtra(ID, 0);
                int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                switch (changeOrDelete) {
                    case UPDATE:
                        updateItem(id);
                        break;
                    case DELETE:
                        deleteItem(id);
                        break;
                }
            }
            if (requestCode == REQUEST_CODE_CHOOSE) {
                long id = data.getLongExtra(ID, 0);
                addItem(id);
            }
            if (requestCode == REQUEST_CODE_CREATE) {
                long id = data.getLongExtra(ID, 0);
                addNewItem(data);
            }
        }
    }

    private void addNewItem(Intent data) {
        final Cycle cycle = new Cycle();
        cycle.setTitle(data.getStringExtra(TITLE));
        cycle.setComment(data.getStringExtra(DESCRIPTION));
        cycle.setStartDate(data.getStringExtra(START_DATA));
        cycle.setFinishDate(data.getStringExtra(FINISH_DATA));
        cycle.setImage(data.getStringExtra(IMAGE));
        cycle.setDefaultImg(data.getStringExtra(DEFAULT_IMAGE));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
                long id = cycleDao.create(cycle);
                if (id != -1) {
                    cycle.setId(id);
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, cycle), 0);
                }
            }
        });
        thread.start();

    }

    private void updateItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
                Cycle cycle = cycleDao.getById(id);
                if (cycle != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, cycle), 0);
                }
            }
        });
        thread.start();

    }

    private void addItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
                Cycle cycle = cycleDao.alterCopyTemplate(id);
                if (cycle != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, cycle), 0);
                }
            }
        });
        thread.start();
    }

    private void deleteItem(final long id) {
        for (Cycle delCycle : cycleList) {
            if (delCycle.getId() == id) {
                index = cycleList.indexOf(delCycle);
                break;
            }
        }

        if (index != -1) {
            System.out.println(index + " " + cycleList.size());
            cycleList.remove(index);
            cycleAdapter.notifyItemRemoved(index);
            cycleAdapter.notifyItemRangeChanged(index, cycleList.size());
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
                Cycle cycle = new Cycle();
                cycle.setId(id);
                if (cycleDao.delete(cycle)) {
                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, id), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR, id), 0);
            }
        });
        thread.start();

    }

    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Cycle cycle) {
        Intent intent = CycleDetailActivity.createIntent(getContext(), cycle);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);

    }
}
