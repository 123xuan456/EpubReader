package com.reeching.epub.bean;

/**
 * Created by 绍轩 on 2017/10/31.
 */

public class LoginBean {

    /**
     * result : 1
     * msg : 注册成功
     * infos : {"id":"f7b3994b2c2d4af8b729074aa49a9027","isNewRecord":false,"createDate":"2018-05-10 19:09:20","updateDate":"2018-05-10 19:09:20","loginName":"100001","password":"de7457da84e9e625b0046fe96f8af29cb148e7d07da43fbffc4af88d","name":"12312312","phone":"13812311231","loginFlag":"1","photo":"|/message/user/2018-05-10/1525950560057/h148944erw48r9wp.png","nickname":"111","sex":"1","device":"mi 322","deviceId":"865166023081352","isCheck":"1","unitName":"1231","unitAdddress":"23","officerNumber":"123","officerTitle":"班长","officerPhoto":"|/message/user/2018-05-10/1525950560085/card.png","admin":false,"roleNames":""}
     */

    private String result;
    private String msg;
    private InfosBean infos;

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

    public InfosBean getInfos() {
        return infos;
    }

    public void setInfos(InfosBean infos) {
        this.infos = infos;
    }

    public static class InfosBean {
        /**
         * id : f7b3994b2c2d4af8b729074aa49a9027
         * isNewRecord : false
         * createDate : 2018-05-10 19:09:20
         * updateDate : 2018-05-10 19:09:20
         * loginName : 100001
         * password : de7457da84e9e625b0046fe96f8af29cb148e7d07da43fbffc4af88d
         * name : 12312312
         * phone : 13812311231
         * loginFlag : 1
         * photo : |/message/user/2018-05-10/1525950560057/h148944erw48r9wp.png
         * nickname : 111
         * sex : 1
         * device : mi 322
         * deviceId : 865166023081352
         * isCheck : 1
         * unitName : 1231
         * unitAdddress : 23
         * officerNumber : 123
         * officerTitle : 班长
         * officerPhoto : |/message/user/2018-05-10/1525950560085/card.png
         * admin : false
         * roleNames :
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String loginName;
        private String password;
        private String name;
        private String phone;
        private String loginFlag;
        private String photo;
        private String nickname;
        private String sex;
        private String device;
        private String deviceId;
        private String isCheck;
        private String unitName;
        private String unitAdddress;
        private String officerNumber;
        private String officerTitle;
        private String officerPhoto;
        private boolean admin;
        private String roleNames;

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

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(String isCheck) {
            this.isCheck = isCheck;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getUnitAdddress() {
            return unitAdddress;
        }

        public void setUnitAdddress(String unitAdddress) {
            this.unitAdddress = unitAdddress;
        }

        public String getOfficerNumber() {
            return officerNumber;
        }

        public void setOfficerNumber(String officerNumber) {
            this.officerNumber = officerNumber;
        }

        public String getOfficerTitle() {
            return officerTitle;
        }

        public void setOfficerTitle(String officerTitle) {
            this.officerTitle = officerTitle;
        }

        public String getOfficerPhoto() {
            return officerPhoto;
        }

        public void setOfficerPhoto(String officerPhoto) {
            this.officerPhoto = officerPhoto;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }
    }
}
