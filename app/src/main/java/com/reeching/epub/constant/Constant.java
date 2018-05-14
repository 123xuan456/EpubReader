package com.reeching.epub.constant;

import android.os.Environment;

/**
 * Created by 绍轩 on 2017/10/12.
 */

public class Constant {


    //主链接
    public static final String SERVIECE_CLIENT = "http://192.168.3.110:8080/ebook/f";
    //上传下载
    public static final String SERVIECE_DOWNLOND = "http://192.168.3.110:8080";

    //一级分类
    public static final String  FIRST_CLASSIFYING = SERVIECE_CLIENT+"/mobile/getBigType";
    //二级分类
    public static final String SECONDARY_CLASSIFYING =SERVIECE_CLIENT+ "/mobile/getSmallType";
    //二级分类下的书
    public static final String SECONDARY_BOOKS = SERVIECE_CLIENT+"/mobile/getBooksBySmallType";
    //根据会员id获取会员的书架
    public static final String BOOKSHELF = SERVIECE_CLIENT+"/mobile/getBookshelf";
    //获取书签---KooReaderIntents文件里
    public static final String GETBOOKMARKS = SERVIECE_CLIENT+"/mobile/getBookmarks";
    //加入书签---KooReaderIntents文件里
    public static final String ADDBOOKMARKS = SERVIECE_CLIENT+"/mobile/addBookmarks";
    //获取通知列表
    public static final String NOTICE = SERVIECE_CLIENT+"/mobile/getNotice";
    //获取公告列表
    public static final String BULLETIN = SERVIECE_CLIENT+"/mobile/getBulletinT";
    //注册修改会员
    public static final String REGISTERMEMBER = SERVIECE_CLIENT+"/mobile/registerMember1";
    //登录
    public static final String LOGIN = SERVIECE_CLIENT+"/mobile/login1";
    //版本更新
    public static final String APPUPDATE = SERVIECE_CLIENT+"/mobile/appUpdate";
    //加入书架
    public static final String ADDBOOKSHELF = SERVIECE_CLIENT+"/mobile/addBookshelf";
    //删除书架
    public static final String REMOVEBOOKSHELFS = SERVIECE_CLIENT+"/mobile/removeBookshelfs";
    //添加下载记录
    public static final String DOWNLOADBOOK = SERVIECE_CLIENT+"/mobile/downloadBook";
    //下载记录
    public static final String GETDOWNLOADLIST = SERVIECE_CLIENT+"/mobile/getDownloadList";
    //添加阅读记录
    public static final String ADDREADRECORD = SERVIECE_CLIENT+"/mobile/addReadRecord";
    //搜索书籍
    public static final String SEARCHBOOKS = SERVIECE_CLIENT+"/mobile/searchBooks";
    //评论列表
    public static final String GETGRADELIST = SERVIECE_CLIENT+"/mobile/getGradeList";
    //添加
    public static final String ADDGRADE = SERVIECE_CLIENT+"/mobile/addGrade";
    //添加意见建议
    public static final String ADDSUGGEST = SERVIECE_CLIENT+"/mobile/addSuggest";
    //修改密码
    public static final String UPDATEMEMBERPASSWORD = SERVIECE_CLIENT+"/mobile/updateMemberPassword1";
    //衔级选择
    public static final String GETSELECTLIST = SERVIECE_CLIENT+"/mobile/getSelectList";




    public static final String ISNIGHT = "isNight";

    public static final String ISBYUPDATESORT = "isByUpdateSort";
    public static final String FLIP_STYLE = "flipStyle";



    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public final static String TABLE_BOOK_INFO = "book_info";
    public final static String TABLE_BOOK_CATEGORY = "book_category";
    public final static String TABLE_BOOK_FAV = "book_fav";

    public final static String BOOK_ID = "book_id";
    public final static String BOOK_MEMBERID = "book_memberId";
    public final static String FIELD_BOOK_NAME = "book_name";
    public final static String FIELD_BOOK_AUTHOR = "book_author";
    public final static String FIELD_BOOK_PATH = "book_path";
    public final static String FIELD_BOOK_ADD_TIME = "book_add_time";
    public final static String FIELD_BOOK_OPEN_TIME = "book_open_time";
    public final static String FIELD_BOOK_CATEGORY_ID = "book_category_id";
    public final static String FIELD_BOOK_CATEGORY_NAME = "book_category_name";
    public final static String FIELD_BOOK_SIZE = "book_size";
    public final static String FIELD_BOOK_PROGRESS = "book_progress";

    //provider
    public final static String DB_PROVIDER = "content://com.reeching.reader.BookContentProvider";
    public final static String URI_TABLE_BOOK_INFO = DB_PROVIDER+"/"+TABLE_BOOK_INFO;
    public final static String URI_TABLE_BOOK_CATEGORY = DB_PROVIDER+"/"+TABLE_BOOK_CATEGORY;
    public final static String URI_TABLE_BOOK_FAV = DB_PROVIDER+"/"+TABLE_BOOK_FAV;

    //SharedPreferencesUtil
    public final static String ENCRYPT_BOOKNAME="encrypt_BookName";
    public final static String ENCRYPT_KEY="encrypt_Key";

    public final static String LOGIN_ID="login_id";
    public final static String LOGIN_NICKNAME="login_nickname";
    public final static String LOGIN_LOGINNAME="login_loginname";
    public final static String LOGIN_PASSWORD="login_password";
    public final static String LOGIN_PHONENUMBER="login_phonenumber";
    public final static String LOGIN_PHOTO="login_photo";
    public final static String LOGIN_OFFICERPHOTO="login_officerphoto";
    public final static String LOGIN_SEX="login_sex";
    public final static String LOGIN_NAME="login_name";
    public final static String DEVICEID="deviceId";
    public final static String DEVICE="device";

    public final static int FRAGMENT_HOT=0;
    public final static int FRAGMENT_NEWS=1;

    public static final int ADEQUATE=0;//内存充足
    public static final int DEFICIENCY=1;//内存不足



    /**
     *  文件保存路径
     */
    //sd
    public final static String SD_READER= "/Reader";//主目录
    //sd路径
    public final static String SD_FIEL= Environment.getExternalStorageDirectory()+SD_READER;//主目录
    //保存电子书目录（解密之后）
    public final static String SD_BOOKS= SD_FIEL+"/books/";
    //保存加密之后电子书目录
//    public final static String SD_KEYBOOKS= SD_FIEL+"/downlond/";
    //保存加密文件目录
    public final static String SD_KEY= SD_FIEL+"/key/";



}
