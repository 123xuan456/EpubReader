package com.reeching.epub;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.db.BookDowload;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reeching.epub.constant.Constant.LOGIN_PHOTO;
import static com.reeching.epub.constant.Constant.SERVIECE_DOWNLOND;
import static com.reeching.epub.utils.FileUtil.getBytes;

public class Main2Activity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    private String srcFileName;
    private String destFileName;
    private String ras_key_url;
    private String FileName;
    Handler handler=new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        FileUtil.createFileDir(this,"ebook");
        FileUtil.createFileDir(this,"key");
        String photo = SharedPreferencesUtil.getInstance().getString(LOGIN_PHOTO, "");
        String photoUrl = SERVIECE_DOWNLOND + photo;
       LogUtils.i(photoUrl);
        textView3.setText(String.format(getResources().getString(R.string.my_info),"小王","打架","一起"));
        /**
         * android创建隐藏文件或者文件夹，其实只要在文件名或者文件夹名字前加一个点号即可。
         隐藏文件（夹）可直接进行读写。
         如果需要去除隐藏，那就是重命名，去除点即可。
         */
     //   ras_key_url=Constant.SD_KEY+"/.RSAKey.xml";//加密文件存放的位置
        ras_key_url=Environment.getExternalStorageDirectory()+ "/Tencent/QQfile_recv/.decrypt";//加密文件存放的位置
        FileName = Environment.getExternalStorageDirectory()+ "/Reader/books/91068.epub";//要加密电子书原始文件（可以打开）

      //  srcFileName = Environment.getExternalStorageDirectory()+ "/Reader/ebook/171294_encryption.epub";//加密后电子书（打不开）
        srcFileName = Environment.getExternalStorageDirectory()+ "/Tencent/QQfile_recv/171294_encryption(1).epub";//加密后电子书（打不开）
        destFileName = Constant.SD_KEY + "/91068_1.epub";//解密后电子书（可以打开和原始文件一样）

    }
    boolean a;
    @OnClick({R.id.button, R.id.button2, R.id.button3,R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                byte[] bytes=getBytes(FileName);
                BookDowload bookDowload=new BookDowload();
                bookDowload.setBook(bytes);
                bookDowload.setBookName(FileName);
                LogUtils.i(bookDowload.save()+"");
                if (bookDowload.save()){
                    ToastUtil.showToast(getApplicationContext(),"保存成功");
                }
//                saveRSAKey(ras_key_url);//生成加密文件
//                encryptFile(FileName, srcFileName, ras_key_url);//加密
                break;
            case R.id.button2:
                List<BookDowload> list= DataSupport.findAll(BookDowload.class);
                LogUtils.i(list.size());
                for (int i = 0; i<list.size(); i++){
                    BookDowload login=list.get(i);
                    LogUtils.i(login.getBookName());
                    ToastUtil.showToast(getApplicationContext(),login.getBookName());
                }



                handler.post(new Runnable() {
                    @Override
                    public void run() {

//                        a = Rsapassword.decryptFile(srcFileName, destFileName, ras_key_url);//解密
//                        if (a){
//                            ToastUtil.showToast(Main2Activity.this,"解密完成");
//                        }
                    }
                });



                break;
            case R.id.button3:
                byte[] byte1=getBytes(srcFileName);
                BookDowload bookDowload1=new BookDowload();
                bookDowload1.setBook(byte1);
                bookDowload1.setBookName(srcFileName);
                LogUtils.i(bookDowload1.save()+"");
                if (bookDowload1.save()){
                    ToastUtil.showToast(getApplicationContext(),"保存成功");
                }

//            if (a){
//                ZLFile file = ZLFile.createFileByPath(destFileName);
//                Log.i("file1=",file.toString());
////                org.geometerplus.fbreader.book.Book book = createBookForFile(file);
////                CoreReadActivity.openBookActivity(this, book,
////                        null);
//            }else{
//                ToastUtil.showToast(this,"先解密在打开");
//            }


                break;
            case R.id.button4:
//                textView3.setText(PhoneUtil.getInstance().getPhoneImei(this));
//                textView4.setText(PhoneUtil.getInstance().getPhoneModel()+PhoneUtil.getInstance().getSDKVersionNumber());

                break;
        }
    }


}
