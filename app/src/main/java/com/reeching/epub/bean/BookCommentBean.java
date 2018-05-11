package com.reeching.epub.bean;

import java.util.List;

/**
 * Created by 绍轩 on 2017/11/14.
 */

public class BookCommentBean {

    /**
     * result : 1
     * msg : 获取成功！
     * infos : [{"id":"926f27d024ca448f828c84c223f0cc5d","isNewRecord":false,"createDate":"2017-11-14 16:09:58","updateDate":"2017-11-14 16:09:58","memberId":"66e47723a38e49f59715ce187b0974cb","bookId":"365b509d51334a9b80582ca5b9d339a2","content":"请问二群无","bookName":"狼图腾","phoneNumber":"13716276410","nickname":"哇哈////88","name":"111","photo":"|/message/front/gallery/2017-11-13/1510558406108/6f21d1d5aa954174bbfa5d222ce15c1c.jpg"},{"id":"365b509d51334a9b80582ca5b9d339a2","isNewRecord":false,"createDate":"2017-11-14 11:55:07","memberId":"66e47723a38e49f59715ce187b0974cb","bookId":"365b509d51334a9b80582ca5b9d339a2","content":"好，","bookName":"狼图腾","phoneNumber":"13716276410","nickname":"哇哈////88","name":"111","photo":"|/message/front/gallery/2017-11-13/1510558406108/6f21d1d5aa954174bbfa5d222ce15c1c.jpg"}]
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
         * id : 926f27d024ca448f828c84c223f0cc5d
         * isNewRecord : false
         * createDate : 2017-11-14 16:09:58
         * updateDate : 2017-11-14 16:09:58
         * memberId : 66e47723a38e49f59715ce187b0974cb
         * bookId : 365b509d51334a9b80582ca5b9d339a2
         * content : 请问二群无
         * bookName : 狼图腾
         * phoneNumber : 13716276410
         * nickname : 哇哈////88
         * name : 111
         * photo : |/message/front/gallery/2017-11-13/1510558406108/6f21d1d5aa954174bbfa5d222ce15c1c.jpg
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String memberId;
        private String bookId;
        private String content;
        private String bookName;
        private String phoneNumber;
        private String nickname;
        private String name;
        private String photo;

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

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
