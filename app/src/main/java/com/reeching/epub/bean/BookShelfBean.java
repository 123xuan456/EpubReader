package com.reeching.epub.bean;

import java.util.List;

/**
 * Created by 绍轩 on 2017/10/23.
 */

public class BookShelfBean {

    /**
     * result : 1
     * msg : 成功
     * infos : [{"id":"f7f74cd2e73b40e287a9520504c3c388","isNewRecord":false,"remarks":"","createDate":"2017-10-20 16:18:36","updateDate":"2017-10-20 16:18:36","booksId":"54b9e4efbbdf42d78bb48ed72db848ad","bookName":"ee","bookPhoto":"","isRead":"2","encryptedUrl":"/message/epub/1/c091c642-6908-4018-9e28-7f115a535aa5/ee_encryption.epub","rsaKeyUrl":"/message/epub/1/c091c642-6908-4018-9e28-7f115a535aa5/decrypt/54b9e4efbbdf42d78bb48ed72db848ad","isDownload":"","memberId":"6a008a9a7d074c6fbc49696bc1064f25","phoneNumber":"222","nickname":"","booksChapter":"11","booksContent":"22"}]
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
         * id : f7f74cd2e73b40e287a9520504c3c388
         * isNewRecord : false
         * remarks :
         * createDate : 2017-10-20 16:18:36
         * updateDate : 2017-10-20 16:18:36
         * booksId : 54b9e4efbbdf42d78bb48ed72db848ad
         * bookName : ee
         * bookPhoto :
         * isRead : 2
         * encryptedUrl : /message/epub/1/c091c642-6908-4018-9e28-7f115a535aa5/ee_encryption.epub
         * rsaKeyUrl : /message/epub/1/c091c642-6908-4018-9e28-7f115a535aa5/decrypt/54b9e4efbbdf42d78bb48ed72db848ad
         * isDownload :
         * memberId : 6a008a9a7d074c6fbc49696bc1064f25
         * phoneNumber : 222
         * nickname :
         * booksChapter : 11
         * booksContent : 22
         * unencryptedUrl: 图书路径
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String booksId;
        private String bookName;
        private String bookPhoto;
        private String isRead;
        private String encryptedUrl;
        private String rsaKeyUrl;
        private String isDownload;
        private String memberId;
        private String phoneNumber;
        private String nickname;
        private String booksChapter;
        private String booksContent;
        private String unencryptedUrl;

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

        public String getBookPhoto() {
            return bookPhoto;
        }

        public void setBookPhoto(String bookPhoto) {
            this.bookPhoto = bookPhoto;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getEncryptedUrl() {
            return encryptedUrl;
        }

        public void setEncryptedUrl(String encryptedUrl) {
            this.encryptedUrl = encryptedUrl;
        }

        public String getRsaKeyUrl() {
            return rsaKeyUrl;
        }

        public void setRsaKeyUrl(String rsaKeyUrl) {
            this.rsaKeyUrl = rsaKeyUrl;
        }

        public String getIsDownload() {
            return isDownload;
        }

        public void setIsDownload(String isDownload) {
            this.isDownload = isDownload;
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
        public String getUnencryptedUrl() {
            return unencryptedUrl;
        }

        public void setUnencryptedUrl(String unencryptedUrl) {
            this.unencryptedUrl = unencryptedUrl;
        }
    }
}
