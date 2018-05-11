package com.koolearn.android.kooreader;

import java.util.List;

/**
 * Created by 绍轩 on 2018/1/23.
 */

public class BooksMarksBean {


    /**
     * result : 1
     * msg : 获取成功！
     * infos : [{"id":"21fd23ce885b419c9306d883c41f88e7","isNewRecord":false,"createDate":"2018-01-23 19:07:27","updateDate":"2018-01-23 19:07:27","memberId":"66e47723a38e49f59715ce187b0974cb","phoneNumber":"13716276410","nickname":"哇哈","booksChapter":"\n 第01章 献给拉斯和弗洛伦斯·多尔\n","booksContent":"成血腥事件，"},{"id":"cb1b94f204004c6cbeeaedb52aa283dd","isNewRecord":false,"remarks":"sa","createDate":"2017-10-20 17:17:22","updateDate":"2017-10-20 17:17:22","booksId":"54b9e4efbbdf42d78bb48ed72db848ad","bookName":"悲惨世界","memberId":"66e47723a38e49f59715ce187b0974cb","phoneNumber":"13716276410","nickname":"哇哈","booksChapter":"","booksContent":"fdsafdsfdsfsdfsf"},{"id":"cb1b94f204004c6cbeeaedb52aa283da","isNewRecord":false,"createDate":"2017-11-08 14:25:11","booksId":"54b9e4efbbdf42d78bb48ed72db848ad","bookName":"悲惨世界","memberId":"66e47723a38e49f59715ce187b0974cb","phoneNumber":"13716276410","nickname":"哇哈"}]
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
         * id : 21fd23ce885b419c9306d883c41f88e7
         * isNewRecord : false
         * createDate : 2018-01-23 19:07:27
         * updateDate : 2018-01-23 19:07:27
         * memberId : 66e47723a38e49f59715ce187b0974cb
         * phoneNumber : 13716276410
         * nickname : 哇哈
         * booksChapter :
         第01章 献给拉斯和弗洛伦斯·多尔

         * booksContent : 成血腥事件，
         * remarks : sa
         * booksId : 54b9e4efbbdf42d78bb48ed72db848ad
         * bookName : 悲惨世界
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String memberId;
        private String phoneNumber;
        private String nickname;
        private String booksChapter;
        private String booksContent;
        private String remarks;
        private String booksId;
        private String bookName;

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

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBooksChapter() {
            return booksChapter;
        }

        public void setBooksChapter(String booksChapter) {
            this.booksChapter = booksChapter;
        }

        public String getBooksContent() {
            return booksContent;
        }

        public void setBooksContent(String booksContent) {
            this.booksContent = booksContent;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getBooksId() {
            return booksId;
        }

        public void setBooksId(String booksId) {
            this.booksId = booksId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }
    }
}
