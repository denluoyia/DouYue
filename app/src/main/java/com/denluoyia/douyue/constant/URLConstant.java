package com.denluoyia.douyue.constant;


/**
 * Created by denluoyia
 * Date 2018/06/22
 * DouYue
 */
public class URLConstant {


    /**
     * q  查询关键字	q和tag必传其一
     * tag	查询的tag	q和tag必传其一
     * start 取结果的offset	默认为0
     * count 取结果的条数	默认为20，最大为100
     */
    public static final String URL_BOOK_SEARCH = "https://api.douban.com/v2/book/search?";


    /**
     * https://api.douban.com/v2/book/24934182
     */
    public static final String URL_BOOK_INFO = "https://api.douban.com/v2/book/";

    /**
     * <p>http://static.owspace.com/static/picture_list.txt?client=android&version=1.3.0&time=1467864021&device_id=866963027059338</p>
     *
     * @param client
     * @param version
     * @param time
     * @param deviceId
     * @return
     */

    //<p>http://static.owspace.com/static/picture_list.txt?client=android&version=1.3.0&time==1467864021&device_id=866963027059338
    public static final String URL_SPLASH_IMAGE = "static/picture_list.txt?client=android&version=1.3.0&";


    /**
     * http://static.owspace.com/?c=api&a=getPost&post_id=292296&show_sdv=1
     * <p>详情页</p>
     *
     * @param c
     * @param a
     * @param post_id
     * @param show_sdv
     * @return
     */
    public static final String URL_DETAIL = "http://static.owspace.com/?c=api&a=getPost&show_sdv=1&post_id=";

    /**
     * <p>分类列表</p>
     * <p>http://static.owspace.com/?c=api&a=getList&p=1&model=1&page_id=0&create_time=0&client=android&version=1.3.0&time=1467867330&device_id=866963027059338&show_sdv=1</p>
     *
     * @param c
     * @param a
     * @param page
     * @param model(0:首页，1：文字，2：声音，3：影像，4：单向历)
     * @param pageId
     * @param time
     * @param deviceId
     * @param show_sdv
     * @return
     */
    public static final String URL_CATEGORIES_LIST = "http://static.owspace.com/?c=api&a=getList&create_time=0&client=android&version=1.3.0&show_sdv=1&";

}
