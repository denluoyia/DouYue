package com.denluoyia.douyue.constant;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.utils.UIUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class Constant {

    public static final String INTENT_ACTION_DATA_KEY = "intent_action_data_key";

    public static final String NOTE_CARD_MODEL = "note_card_model";

    public static final int OP_NOTE_CREATE = 1;
    public static final int OP_NOTE_READ = 2;
    public static final int OP_NOTE_EDIT = 3;

    public static final int MY_COLLECTION_TYPE_TEXT = 0;
    public static final int MY_COLLECTION_TYPE_VIDEO = 1;
    public static final int MY_COLLECTION_TYPE_AUDIO = 2;
    public static final int MY_COLLECTION_TYPE_BOOK = 3;

    public static final List<String> MyCollectionTypeArray = Arrays.asList(UIUtil.getStringArray(R.array.collection_type_array));


}
