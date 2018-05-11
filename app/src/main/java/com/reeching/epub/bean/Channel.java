package com.reeching.epub.bean;

/**
 * Created by de on 2016/8/16.
 * 首页页面的上标题内容
 */
public class Channel {
    private String id;
    private String name;
    private int order;
    private String weburl;
    private String hweburl;



    public Channel(){

    }
    public Channel(String id, String name, int order, String weburl, String hweburl) {
        super();
        this.id=id;
        this.name = name;
        this.order = order;
        this.weburl = weburl;
        this.hweburl = hweburl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public String getWeburl() {
        return weburl;
    }
    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }
    public String getHweburl() {
        return hweburl;
    }
    public void setHweburl(String hweburl) {
        this.hweburl = hweburl;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
