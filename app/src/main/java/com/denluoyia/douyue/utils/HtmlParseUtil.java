package com.denluoyia.douyue.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.widget.LinearLayout;

import com.denluoyia.douyue.manager.ThreadManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/16
 * DouYue
 */
public class HtmlParseUtil {
    public enum HtmlType{
        URL,
        STRING
    }

    private Activity mActivity;
    private Document mDocument;

    private LinearLayout totalView;

    private String imgHeight;
    private String imgUrl;
    private String imgWidth;
    private PaintHtmlViewUtil mPaintUtil;
    private Thread strThread;
    private Thread urlThread;
    private InnerHandler mHandler = new InnerHandler();

    public HtmlParseUtil(Activity activity){
        mActivity = activity;
    }

    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            parseDocument(mDocument);
        }
    }

    private void loadHtmlString(final String htmlString){
        strThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDocument = Jsoup.parseBodyFragment(htmlString);
                mHandler.sendEmptyMessage(0);
            }
        });
        ThreadManager.getSinglePool().execute(strThread);
    }

    private void loadHtmlUrl(final String url){
        urlThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mDocument = Jsoup.connect(url).get();
                    mHandler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadManager.getShortPool().execute(urlThread);
    }


    private void parseDocument(Document paramDocument){
        mPaintUtil = new PaintHtmlViewUtil(mActivity);
        Iterator its = paramDocument.getAllElements().iterator();
        while (its.hasNext()){
            Element e = (Element) its.next();
            if (e.nodeName().matches("p") || e.nodeName().matches("h[1-9]?[0-9]?") || e.nodeName().matches("block")){
                String strTemp = e.text().replaceAll("br;", "\n");
                SpannableStringBuilder ssb = new SpannableStringBuilder("\n" + strTemp);
                if (e.nodeName().equals("h5")){
                    mPaintUtil.addH5TextView(mActivity, totalView, ssb);
                }else{
                    mPaintUtil.addPTextView(mActivity, totalView, ssb);
                }

            }

            if (e.nodeName().matches("img")) {
                imgUrl = e.attr("src");
                this.imgWidth = e.attr("width");
                this.imgHeight = e.attr("height");
                mPaintUtil.addImageView(mActivity, null, totalView, this.imgWidth, this.imgHeight, imgUrl);
            }
        }
    }


    public void loadHtml(String content, HtmlType type, LinearLayout paramLinearLayout){
        this.totalView = paramLinearLayout;
        String str = content.replaceAll("<br/>", "br;");
        if (type == HtmlType.URL){
            loadHtmlUrl(str);
        }else{
            loadHtmlString(str);
        }
    }

}
