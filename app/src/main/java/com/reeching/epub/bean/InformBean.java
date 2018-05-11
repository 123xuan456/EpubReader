package com.reeching.epub.bean;

import java.util.List;

/**
 * Created by 绍轩 on 2017/10/28.
 */

public class InformBean {


    /**
     * result : 1
     * msg : 成功
     * infos : [{"id":"53e4cc6bcbc341ef8f63f88280141848","isNewRecord":false,"remarks":"fdsa","createDate":"2017-10-20 17:12:41","updateDate":"2017-10-20 17:12:41","title":"fsadfds","noticeContent":"dfsafds","ifPublic":"1"}]
     */

    private String result;
    private String msg;
    private List<InfosBean> infos;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<InfosBean> getInfos() {
        return infos;
    }

    public void setInfos(List<InfosBean> infos) {
        this.infos = infos;
    }

    public static class InfosBean {
        /**
         * id : 53e4cc6bcbc341ef8f63f88280141848
         * isNewRecord : false
         * remarks : fdsa
         * createDate : 2017-10-20 17:12:41
         * updateDate : 2017-10-20 17:12:41
         * title : fsadfds
         * noticeContent : dfsafds
         * ifPublic : 1
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String title;
        private String noticeContent;
        private String ifPublic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getIfPublic() {
            return ifPublic;
        }

        public void setIfPublic(String ifPublic) {
            this.ifPublic = ifPublic;
        }
    }
}
