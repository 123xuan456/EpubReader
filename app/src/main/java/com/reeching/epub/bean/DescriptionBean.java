package com.reeching.epub.bean;

import java.util.List;

/**
 * Created by 绍轩 on 2018/5/10.
 */

public class DescriptionBean {

    /**
     * result : 1
     * msg : 获取成功！
     * infos : [{"id":"311aa565a9614c728a92917c64050805","isNewRecord":false,"remarks":"","createDate":"2018-05-09 16:14:14","updateDate":"2018-05-09 16:14:14","value":"班长","label":"班长","type":"titleName","description":"衔级","sort":10},{"id":"9c97efdca4df4dbf98046267d4bb61d2","isNewRecord":false,"remarks":"","createDate":"2018-05-09 16:14:24","updateDate":"2018-05-09 16:14:24","value":"中校","label":"中校","type":"titleName","description":"衔级","sort":20},{"id":"d6ffffd2d8244437b13250776715f3d8","isNewRecord":false,"remarks":"","createDate":"2018-05-09 16:14:33","updateDate":"2018-05-09 16:14:33","value":"上校","label":"上校","type":"titleName","description":"衔级","sort":30},{"id":"3aa25681d6b04d92800dc861126df9cd","isNewRecord":false,"remarks":"","createDate":"2018-05-09 16:14:42","updateDate":"2018-05-09 16:14:42","value":"少校","label":"少校","type":"titleName","description":"衔级","sort":40}]
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
         * id : 311aa565a9614c728a92917c64050805
         * isNewRecord : false
         * remarks :
         * createDate : 2018-05-09 16:14:14
         * updateDate : 2018-05-09 16:14:14
         * value : 班长
         * label : 班长
         * type : titleName
         * description : 衔级
         * sort : 10
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
