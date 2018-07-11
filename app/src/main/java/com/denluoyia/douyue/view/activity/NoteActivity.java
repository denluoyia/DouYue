package com.denluoyia.douyue.view.activity;


import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.event.NoteListChangeEvent;
import com.denluoyia.douyue.manager.EventManager;
import com.denluoyia.douyue.manager.db.greendao.NoteDaoManager;
import com.denluoyia.douyue.model.db.NoteBean;
import com.denluoyia.douyue.utils.SPUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.view.adapter.NoteLeftDrawerAdapter;
import com.denluoyia.douyue.view.adapter.NoteListAdapter;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, NoteListAdapter.OnNoteOperateListener{
    public static final int NOTE_STATUS_NOTES = 1;
    public static final int NOTE_STATUS_TRASH = 0;
    public int currNoteStatus = 1;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.left_drawer_list_view)
    ListView leftListView;
    @BindView(R.id.left_drawer)
    LinearLayout llLeftDrawerLayout;

    private NoteListAdapter mAdapter;
    private NoteLeftDrawerAdapter mLeftAdapter;
    private List<String> mLeftDataList = Arrays.asList(UIUtil.getStringArray(R.array.note_left_drawer_array));


    @Override
    protected int setContentViewId() {
        return R.layout.activity_note;
    }

    @Override
    protected void doBusiness() {
        initToolbar(toolbar);
        toolbar.setTitle("笔记录");
        toolbar.setTitleTextColor(UIUtil.getColor(R.color.white));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager;
        if (SPUtil.getBoolean(Constant.NOTE_CARD_MODEL, true)){
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }else{
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NoteListAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnNoteOperateListener(this);
        mAdapter.refreshDataList(NoteDaoManager.queryAllByStatusOK());

        mLeftAdapter = new NoteLeftDrawerAdapter(this, mLeftDataList);
        leftListView.setAdapter(mLeftAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        currNoteStatus = NOTE_STATUS_NOTES;
                        toolbar.setTitle(mLeftDataList.get(0));
                        mAdapter.refreshDataList(NoteDaoManager.queryAllByStatusOK());
                        break;

                    case 1:
                        currNoteStatus = NOTE_STATUS_TRASH;
                        toolbar.setTitle(mLeftDataList.get(1));
                        mAdapter.refreshDataList(NoteDaoManager.queryAllByStatusIDLE());
                        break;

                    default:
                        break;
                }

                drawerLayout.closeDrawer(llLeftDrawerLayout);
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.refreshDataList(null);
        UIUtil.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.refreshDataList(currNoteStatus == NOTE_STATUS_NOTES  ? NoteDaoManager.queryAllByStatusOK() : NoteDaoManager.queryAllByStatusIDLE());
            }
        }, 1000);
    }

    @OnClick({R.id.btn_float})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_float:
                Intent intent = new Intent(this, NoteEditActivity.class);
                intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, Constant.OP_NOTE_CREATE);
                startActivity(intent);
                break;
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void NoteListChangeUpdateEvent(NoteListChangeEvent event){
        switch (event.nodeOp){
            case NoteListChangeEvent.TYPE_ADD:
                NoteDaoManager.insert(event.item);
                break;

            case NoteListChangeEvent.TYPE_DELETE:
                NoteDaoManager.delete(event.item);
                break;

            case NoteListChangeEvent.TYPE_UPDATE:
                NoteDaoManager.update(event.item);
                break;
        }
        if (currNoteStatus == NOTE_STATUS_NOTES){
            mAdapter.refreshDataList(NoteDaoManager.queryAllByStatusOK());
        }else {
            mAdapter.refreshDataList(NoteDaoManager.queryAllByStatusIDLE());
        }
    }


    @Override
    public void onOperateItem(View view, int position) {
        final NoteBean item = mAdapter.getItem(position);
        if (currNoteStatus == NoteActivity.NOTE_STATUS_NOTES){
            switch (view.getId()){
                case R.id.note_op:
                    PopupMenu popup = new PopupMenu(this, view);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_notes_more, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.edit:
                                    Intent intent = new Intent(NoteActivity.this, NoteEditActivity.class);
                                    intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, Constant.OP_NOTE_EDIT);
                                    intent.putExtra("noteItem", item);
                                    startActivity(intent);
                                    break;

                                case R.id.move_to_trash:
                                    item.setStatus(0); //移动到回收站
                                    EventManager.post(new NoteListChangeEvent(NoteListChangeEvent.TYPE_UPDATE, item));
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                    break;
            }
        }else{
            switch (view.getId()){
                case R.id.note_op:
                    PopupMenu popup = new PopupMenu(this, view);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_notes_trash_more, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.recover:
                                    item.setStatus(1);
                                    EventManager.post(new NoteListChangeEvent(NoteListChangeEvent.TYPE_UPDATE, item));
                                    break;

                                case R.id.delete:
                                    EventManager.post(new NoteListChangeEvent(NoteListChangeEvent.TYPE_DELETE, item));
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                    break;
            }
        }
    }
}
