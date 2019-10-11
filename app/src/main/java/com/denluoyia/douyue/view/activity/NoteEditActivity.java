package com.denluoyia.douyue.view.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.event.NoteListChangeEvent;
import com.denluoyia.douyue.manager.EventManager;
import com.denluoyia.douyue.model.db.NoteBean;
import com.denluoyia.douyue.utils.TimeUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;

public class NoteEditActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_edit_text)
    MaterialEditText editTitle;
    @BindView(R.id.content_edit_text)
    MaterialEditText editContent;
    @BindView(R.id.opr_time_line_text)
    TextView timeText;

    private Menu mMenu;
    private int op_type;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_note_edit;
    }

    @Override
    protected void doBusiness() {
        initToolbar();
        final NoteBean item = getIntent().getParcelableExtra("noteItem");
        if (item != null) {
            editTitle.setText(item.getTitle());
            editTitle.setSelection(item.getTitle().length());
            editContent.setText(item.getContent());
            String timeInfo;
            if (item.getCreateTime() == item.getLastEditTime()){
                timeInfo = "创建:" + TimeUtil.getTime(item.getCreateTime());
            }else{
                timeInfo = "最后更新:" + TimeUtil.getTime(item.getLastEditTime()) +
                        "\n" + "创建:"  + TimeUtil.getTime(item.getCreateTime());
            }
            timeText.setText(timeInfo);
            switch (op_type) {
                case Constant.OP_NOTE_EDIT:
                    break;

                case Constant.OP_NOTE_READ:
                    editTitle.setCursorVisible(false);
                    editTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editTitle.requestFocus();
                            editTitle.setCursorVisible(true);
                        }
                    });
                    break;
            }

            editTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence != null && !charSequence.toString().trim().equals(item.getTitle())) {
                        mMenu.getItem(0).setVisible(true);
                    } else {
                        mMenu.getItem(0).setVisible(false);
                    }
                    mToolbar.setTitle("编辑笔记");

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence != null && !charSequence.toString().trim().equals(item.getContent())) {
                        mMenu.getItem(0).setVisible(true);
                    } else {
                        mMenu.getItem(0).setVisible(false);
                    }
                    mToolbar.setTitle("编辑笔记");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        editTitle.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        mMenu = menu;
        mMenu.getItem(0).setVisible(false); //开始进入隐藏不可见
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:
                NoteBean note = new NoteBean();
                note.setTitle(editTitle.getText().toString());
                note.setContent(editContent.getText().toString());
                note.setStatus(NoteBean.NOTE_STATUS_OK);
                switch (op_type){
                    case Constant.OP_NOTE_CREATE:
                        note.setCreateTime(TimeUtil.getCurrentTime());
                        note.setLastEditTime(TimeUtil.getCurrentTime());
                        EventManager.post(new NoteListChangeEvent(NoteListChangeEvent.TYPE_ADD,note));
                        break;
                    case Constant.OP_NOTE_READ:
                    case Constant.OP_NOTE_EDIT:
                        NoteBean temp = getIntent().getParcelableExtra("noteItem");
                        note.setId(temp.getId());
                        note.setCreateTime(temp.getCreateTime());
                        note.setLastEditTime(TimeUtil.getCurrentTime());
                        EventManager.post(new NoteListChangeEvent(NoteListChangeEvent.TYPE_UPDATE, note));
                        break;
                    default:
                        break;
                }

                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
    }


    private void initToolbar() {
        initToolbar(mToolbar);
        mToolbar.setTitleTextColor(UIUtil.getColor(R.color.white));
        op_type = getIntent().getIntExtra(Constant.INTENT_ACTION_DATA_KEY, 0);
        switch (op_type){
            case Constant.OP_NOTE_CREATE:
                mToolbar.setTitle("新建笔记");
                editContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence != null && charSequence.toString().trim().length() > 0){
                            mMenu.getItem(0).setVisible(true);
                        }else{
                            mMenu.getItem(0).setVisible(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;

            case Constant.OP_NOTE_EDIT:
                mToolbar.setTitle("编辑笔记");
                break;

            case Constant.OP_NOTE_READ:
                mToolbar.setTitle("查看笔记");
                break;
        }
    }
}
