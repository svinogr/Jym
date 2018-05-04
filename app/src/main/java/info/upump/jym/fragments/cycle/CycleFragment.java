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

import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_DELETE;

public class CycleFragment extends Fragment implements View.OnClickListener, CRUD {
    protected ITitleble iTitlable;
    protected RecyclerView recyclerView;
    protected CycleAdapter cycleAdapter;
    protected List<Cycle> cycleList = new ArrayList<>();
    private int index = -1;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected FloatingActionButton addFab;
    protected ASTCycle astCycle;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                   Toast.makeText(getContext(), R.string.toast_cycle_delete, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
//                    insertDeletedItem(id);
                }


            if (msg.what == 101) {
                long id = Long.parseLong(msg.obj.toString());
                CycleDao cycleDao = new CycleDao(getContext());
                Cycle cycle = cycleDao.alterCopyTemplate(id);
                cycleList.add(cycle);
                cycleAdapter.notifyItemInserted(cycleList.size() - 1);
            }
        }
    };

    private void insertDeletedItem(long id) {
        CycleDao cycleDao = new CycleDao(getContext());
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
        if (getArguments() != null) {
        }
        createAsyncTask();

        try {
            cycleList = astCycle.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        createAdapter();

    }

    protected void createAsyncTask() {
        astCycle = new ASTCycle(getContext());
        astCycle.execute(Constants.LOADER_BY_USER_TYPE);
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
    public void onResume() {
        System.out.println("1");
        super.onResume();
//        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        getLoaderManager().initLoader(0, null, this);
        iTitlable = (ITitleble) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == -1) {
            if (requestCode == REQUEST_CODE_DELETE) {
                long id = data.getLongExtra(ID, 0);
                deleteItem(id);
            }
            if (requestCode == REQUEST_CODE_CHOOSE) {
                long id = data.getLongExtra(ID, 0);
                addItem(id);
            }
        }
    }

    private void addItem(long id) {
        handler.sendMessageDelayed(handler.obtainMessage(101, id), 20);
    }

    private void deleteItem(final long id) {
        System.out.println(index);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CycleDao cycleDao = new CycleDao(getContext());

                Cycle cycle = new Cycle();
                cycle.setId(id);
                if (cycleDao.delete(cycle)) {
                    handler.sendMessageDelayed(handler.obtainMessage(100), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(102), 0);
            }
        });
        thread.start();
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
//            handler.sendMessageDelayed(handler.obtainMessage(100, id), 20);
        }
     /*   handler.sendMessageDelayed(handler.obtainMessage(100, id), 0);*/
    }

    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Cycle cycle) {
        Intent intent = CycleDetailActivity.createIntent(getContext(), cycle);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_DELETE, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_DELETE);

    }
}
