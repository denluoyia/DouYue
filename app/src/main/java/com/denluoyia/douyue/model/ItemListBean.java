package com.denluoyia.douyue.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class ItemListBean {

    private int code;
    private String msg;
    private String status;
    private List<ListBean> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ListBean> datas) {
        this.datas = datas;
    }

    public static class ListBean implements Parcelable {
        private String author;
        private String avatar;
        private String bookmark;
        private String category;
        private String create_time;
        private String excerpt;
        private String fm;
        private String fm_play;
        private String good;
        private String hot_comments;
        private String html5;
        private String id;
        private String lead;
        private String link_url;
        private String lunar_type;
        private String model;
        private String name;
        private int parseXML;
        private String position;
        private String publish_time;
        private String share;
        private String show_uid;
        private String status;
        private String thumbnail;
        private String title;
        private int tpl;
        private String uid;
        private String update_time;
        private String video;
        private String view;
        private List<TagBean> tags;

        public ListBean(){

        }

        protected ListBean(Parcel in) {
            author = in.readString();
            avatar = in.readString();
            bookmark = in.readString();
            category = in.readString();
            create_time = in.readString();
            excerpt = in.readString();
            fm = in.readString();
            fm_play = in.readString();
            good = in.readString();
            hot_comments = in.readString();
            html5 = in.readString();
            id = in.readString();
            lead = in.readString();
            link_url = in.readString();
            lunar_type = in.readString();
            model = in.readString();
            name = in.readString();
            parseXML = in.readInt();
            position = in.readString();
            publish_time = in.readString();
            share = in.readString();
            show_uid = in.readString();
            status = in.readString();
            thumbnail = in.readString();
            title = in.readString();
            tpl = in.readInt();
            uid = in.readString();
            update_time = in.readString();
            video = in.readString();
            view = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(author);
            dest.writeString(avatar);
            dest.writeString(bookmark);
            dest.writeString(category);
            dest.writeString(create_time);
            dest.writeString(excerpt);
            dest.writeString(fm);
            dest.writeString(fm_play);
            dest.writeString(good);
            dest.writeString(hot_comments);
            dest.writeString(html5);
            dest.writeString(id);
            dest.writeString(lead);
            dest.writeString(link_url);
            dest.writeString(lunar_type);
            dest.writeString(model);
            dest.writeString(name);
            dest.writeInt(parseXML);
            dest.writeString(position);
            dest.writeString(publish_time);
            dest.writeString(share);
            dest.writeString(show_uid);
            dest.writeString(status);
            dest.writeString(thumbnail);
            dest.writeString(title);
            dest.writeInt(tpl);
            dest.writeString(uid);
            dest.writeString(update_time);
            dest.writeString(video);
            dest.writeString(view);
        }


        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getFm() {
            return fm;
        }

        public void setFm(String fm) {
            this.fm = fm;
        }

        public String getFm_play() {
            return fm_play;
        }

        public void setFm_play(String fm_play) {
            this.fm_play = fm_play;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getHot_comments() {
            return hot_comments;
        }

        public void setHot_comments(String hot_comments) {
            this.hot_comments = hot_comments;
        }

        public String getHtml5() {
            return html5;
        }

        public void setHtml5(String html5) {
            this.html5 = html5;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLead() {
            return lead;
        }

        public void setLead(String lead) {
            this.lead = lead;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getLunar_type() {
            return lunar_type;
        }

        public void setLunar_type(String lunar_type) {
            this.lunar_type = lunar_type;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParseXML() {
            return parseXML;
        }

        public void setParseXML(int parseXML) {
            this.parseXML = parseXML;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public String getShow_uid() {
            return show_uid;
        }

        public void setShow_uid(String show_uid) {
            this.show_uid = show_uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTpl() {
            return tpl;
        }

        public void setTpl(int tpl) {
            this.tpl = tpl;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public List<TagBean> getTags() {
            return tags;
        }

        public void setTags(List<TagBean> tags) {
            this.tags = tags;
        }

        public static class TagBean{
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
