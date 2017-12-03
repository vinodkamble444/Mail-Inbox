package com.example.userslist.user;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.userslist.R;
import com.example.userslist.UserListApplication;
import com.example.userslist.data.User;
import com.example.userslist.di.ActivityComponent;
import com.example.userslist.di.DaggerActivityComponent;
import com.example.userslist.di.UsersActivityModule;
import com.example.userslist.helper.DividerItemDecoration;
import com.example.userslist.sort.SortType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *UsersActivity responsible for presenting data in a way decided by the presenter
 */
public class UsersActivity extends AppCompatActivity implements UserContract.View, SwipeRefreshLayout.OnRefreshListener, UsersAdapter.UsersAdapterListener,CompoundButton.OnCheckedChangeListener{

    //Presenter will be provided by a dependency injector
    @Inject
    UsersPresenter mUsersPresenter;
    private final List<User> users = new ArrayList<>();
    private RecyclerView recyclerView;
    private UsersAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ToggleButton tbInfo,tbPoint,tbLastActive;
    private boolean mIsActive;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ActionModeCallback actionModeCallback;
    private CoordinatorLayout coordinatorLayout;
    private ActionMode actionMode;
    private static final String TAG = "MainActivity";
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        tbInfo= (ToggleButton) findViewById(R.id.chkInfo);
        tbPoint= (ToggleButton) findViewById(R.id.chkPoint);
        tbLastActive= (ToggleButton) findViewById(R.id.chkActive);
        tbInfo.setOnCheckedChangeListener(this);
        tbPoint.setOnCheckedChangeListener(this);
        tbLastActive.setOnCheckedChangeListener(this);

        //Inject Dependency
        getActivityComponent().inject(this);

        // Set up User list
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new UsersAdapter(this,users, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        actionModeCallback = new ActionModeCallback();
        mBottomSheetBehavior=BottomSheetBehavior.from(findViewById(R.id.bottomsheet_layout));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        mUsersPresenter.loadUsers();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsActive = false;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
// show loader and fetch users
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(active);
                    }
                }
        );
    }

    @Override
    public void showUsers(List<User> users) {
        replaceData(users);
    }

    @Override
    public void showLoadingUsersError() {
        showMessage(getString(R.string.loading_users_error));
    }

    public boolean isIsActive() {
        return mIsActive;
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
    }

    private void replaceData(List<User> users) {
        mAdapter.setData(users);
    }

    private void showMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        searchView.setQueryHint(getString(R.string.action_search));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort) {
            bottomSheetToggal();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bottomSheetToggal() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the users again
        mUsersPresenter.loadUsers();
    }

    @Override
    public void onMoreClicked(String groupLabel) {
        show(groupLabel);
    }
    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);

    }
    @Override
    public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        enableActionMode(position);
    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        }
    }
    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void show(String groupLabels) {
        Snackbar.make(coordinatorLayout, groupLabels, Snackbar.LENGTH_LONG).show();
    }

    /**
     *Sorting ASC/DESC icon clicked
     */

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.chkInfo:
                bottomSheetToggal();
                if(isChecked)
                    mAdapter.sort(SortType.INFO,SortType.DESC);
                else
                    mAdapter.sort(SortType.INFO,SortType.ASC);
                break;
            case R.id.chkPoint:
                bottomSheetToggal();
                if(isChecked)
                    mAdapter.sort(SortType.POINT,SortType.DESC);
                else
                    mAdapter.sort(SortType.POINT,SortType.ASC);
                break;
            case R.id.chkActive:
                bottomSheetToggal();
                if(isChecked)
                    mAdapter.sort(SortType.ACTIVE,SortType.DESC);
                else
                    mAdapter.sort(SortType.ACTIVE,SortType.ASC);
                break;
        }
    }
    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .usersActivityModule(new UsersActivityModule(this))
                    .userComponent(UserListApplication.get(this).getDaggerComponent())
                    .build();
        }
        return activityComponent;
    }

}
