package com.reeching.epub.bean;

import java.util.List;

/**
 * Created by 绍轩 on 2017/10/28.
 */

public class SorkBookBean {

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
         *
         * "id": "9eb2682f903e456892fd164c1fe71a53",
         "isNewRecord": false,
         "remarks": "",
         "createDate": "2017-10-19 17:10:10",
         "updateDate": "2017-10-24 13:53:44",
         "title": "阿瑟东发送到法", //书名
         "author": "a's'd'fa's'd'f", //作者
         "summaryContent": "啊手动阀手动阀打发士大夫", //简介
         "photo": "", //封面
         "designItem": "asdfasdfasdf", //设计项目
         "field": "",  //领域
         "bigType": "1",  //一级分类
         "bigTypeName": "分类", //一级分类名称
         "smallType": "43c2235156264e778d02e0450634db24",   //二级分类
         "smallTypeName": "3333333", // //二级分类名称
         "bookPublisher": "", //出版人
         "chapterName": "",
         "contentPure": "",
         "bookUrl": "",
         "bookContributor": "",
         "bookIsbn": "",
         "original": "/message/epub/1/794cf77f-0b14-4bd7-a65b-9985dd3dfb5b/阿瑟东发送到法",
         "unencryptedUrl": "/message/epub/1/794cf77f-0b14-4bd7-a65b-9985dd3dfb5b/阿瑟东发送到法.epub",
         "encryptedUrl": "/message/epub/1/794cf77f-0b14-4bd7-a65b-9985dd3dfb5b/阿瑟东发送到法_encryption.epub",
         "rsaKeyUrl": "/message/epub/1/794cf77f-0b14-4bd7-a65b-9985dd3dfb5b/decrypt/9eb2682f903e456892fd164c1fe71a53",
         "bookLanguage": "",
         "keyword": "",
         "ifDownload": "1",
         "ifRead": "1",
         "bookFileUuid": "794cf77f-0b14-4bd7-a65b-9985dd3dfb5b",
         "memberId":"0"  //如果memberId为0，说明此书没有被加入书架，不为0，说明已经加入书架了
         ,"downloadCount": "1"   //新增的下载次数

         */
        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String title;
        private String author;
        private String summaryContent;
        private String photo;
        private String designItem;
        private String field;
        private String bigType;
        private String bigTypeName;
        private String smallType;
        private String smallTypeName;
        private String bookPublisher;
        private String bookUrl;
        private String bookContributor;
        private String bookIsbn;
        private String original;
        private String unencryptedUrl;
        private String encryptedUrl;
        private String rsaKeyUrl;
        private String bookLanguage;
        private String keyword;
        private String ifDownload;
        private String ifRead;
        private String bookFileUuid;
        private String publicDate;
        private String memberId;
        private String downloadCount;

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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSummaryContent() {
            return summaryContent;
        }

        public void setSummaryContent(String summaryContent) {
            this.summaryContent = summaryContent;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDesignItem() {
            return designItem;
        }

        public void setDesignItem(String designItem) {
            this.designItem = designItem;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getBigType() {
            return bigType;
        }

        public void setBigType(String bigType) {
            this.bigType = bigType;
        }

        public String getBigTypeName() {
            return bigTypeName;
        }

        public void setBigTypeName(String bigTypeName) {
            this.bigTypeName = bigTypeName;
        }

        public String getSmallType() {
            return smallType;
        }

        public void setSmallType(String smallType) {
            this.smallType = smallType;
        }

        public String getSmallTypeName() {
            return smallTypeName;
        }

        public void setSmallTypeName(String smallTypeName) {
            this.smallTypeName = smallTypeName;
        }

        public String getBookPublisher() {
            return bookPublisher;
        }

        public void setBookPublisher(String bookPublisher) {
            this.bookPublisher = bookPublisher;
        }

        public String getBookUrl() {
            return bookUrl;
        }

        public void setBookUrl(String bookUrl) {
            this.bookUrl = bookUrl;
        }

        public String getBookContributor() {
            return bookContributor;
        }

        public void setBookContributor(String bookContributor) {
            this.bookContributor = bookContributor;
        }

        public String getBookIsbn() {
            return bookIsbn;
        }

        public void setBookIsbn(String bookIsbn) {
            this.bookIsbn = bookIsbn;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public String getUnencryptedUrl() {
            return unencryptedUrl;
        }

        public void setUnencryptedUrl(String unencryptedUrl) {
            this.unencryptedUrl = unencryptedUrl;
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

        public String getBookLanguage() {
            return bookLanguage;
        }

        public void setBookLanguage(String bookLanguage) {
            this.bookLanguage = bookLanguage;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getIfDownload() {
            return ifDownload;
        }

        public void setIfDownload(String ifDownload) {
            this.ifDownload = ifDownload;
        }

        public String getIfRead() {
            return ifRead;
        }

        public void setIfRead(String ifRead) {
            this.ifRead = ifRead;
        }

        public String getBookFileUuid() {
            return bookFileUuid;
        }

        public void setBookFileUuid(String bookFileUuid) {
            this.bookFileUuid = bookFileUuid;
        }

        public String getPublicDate() {
            return publicDate;
        }

        public void setPublicDate(String publicDate) {
            this.publicDate = publicDate;
        }
        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
        public String getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(String downloadCount) {
            this.downloadCount = downloadCount;
        }
    }
}
