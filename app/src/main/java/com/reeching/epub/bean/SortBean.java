package com.reeching.epub.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 绍轩 on 2017/11/6.
 */

public class SortBean implements Serializable {

    /**
     * result : 1
     * msg : 成功
     * infos : [{"bigList":{"bigName":"分类","bigId":"1","smallList":[{"sname":"3333333","sId":"43c2235156264e778d02e0450634db24"},{"sname":"222222111","sId":"b0369a0ef44a4bf9999b044c6673dcf5"}]}},{"bigList":{"bigName":"111","bigId":"4c06e25bf167471dbf0e05533c3eede5","smallList":[]}},{"bigList":{"bigName":"文学","bigId":"f1db676b22d842a5bd021365e4cf7382","smallList":[{"sname":"历史","sId":"b71318f720d74654bf6dbede3b31f6b3"}]}},{"bigList":{"bigName":"222222222","bigId":"b5da169060e5433c8c1d53587041f0c6","smallList":[{"sname":"漫画","sId":"a38be4f92a6a4f51b91ff3ee92634146"}]}}]
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
         * bigList : {"bigName":"分类","bigId":"1","smallList":[{"sname":"3333333","sId":"43c2235156264e778d02e0450634db24"},{"sname":"222222111","sId":"b0369a0ef44a4bf9999b044c6673dcf5"}]}
         */

        private BigListBean bigList;

        public BigListBean getBigList() {
            return bigList;
        }

        public void setBigList(BigListBean bigList) {
            this.bigList = bigList;
        }

        public static class BigListBean implements Serializable{
            /**
             * bigName : 分类
             * bigId : 1
             * smallList : [{"sname":"3333333","sId":"43c2235156264e778d02e0450634db24"},{"sname":"222222111","sId":"b0369a0ef44a4bf9999b044c6673dcf5"}]
             */

            private String bigName;
            private String bigId;
            private List<SmallListBean> smallList;

            public String getBigName() {
                return bigName;
            }

            public void setBigName(String bigName) {
                this.bigName = bigName;
            }

            public String getBigId() {
                return bigId;
            }

            public void setBigId(String bigId) {
                this.bigId = bigId;
            }

            public List<SmallListBean> getSmallList() {
                return smallList;
            }

            public void setSmallList(List<SmallListBean> smallList) {
                this.smallList = smallList;
            }

            public static class SmallListBean implements Serializable{
                /**
                 * sname : 3333333
                 * sId : 43c2235156264e778d02e0450634db24
                 */

                private String sname;
                private String sId;

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getSId() {
                    return sId;
                }

                public void setSId(String sId) {
                    this.sId = sId;
                }
            }
        }
    }
}
