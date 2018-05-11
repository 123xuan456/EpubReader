package com.reeching.epub.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.koolearn.android.util.LogUtils;
import com.reeching.epub.constant.Constant;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by 绍轩 on 2017/10/13.
 */

public class FileUtil {


    public static boolean fileIsExists(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 创建文件
     *
     * @param filePath 文件夹名称
     * @return
     */

    public static File createFile(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    //通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    //如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
    public static String getExternalFilesDirPath(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir("books").getPath();// /sdcard/Android/data/files
        } else {
            cachePath = context.getFilesDir().getPath();//  /data/data//files
        }
        return cachePath + "/";
    }

    /**
     * 保存Bitmap到指定目录
     *
     * @param filePath 文件路径
     * @param bitmap
     * @throws IOException
     */
    public static String saveBitmap(String filePath, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            LogUtils.i(isExsit(filePath)+"");
            File file = new File(filePath);
            if (isExsit(filePath)) {
                file.delete();
            }
            LogUtils.i(isExsit(filePath)+"");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 裁剪后，根据裁剪框的长宽比，同时根据图片的需求缩放尺寸进行缩放
     *
     * @param path
     * @param x    原始的需求尺寸width
     * @param y    heiht
     * @return
     */
    public static Bitmap toBigZoom(String path, float x, float y) {
        Log.e("bitmaputil", "path---" + path + "--x--y--" + x + "--" + y);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            float sx = 0;
            float sy = 0;
            if ((float) w / h >= 1) {
                sx = (float) y / w;
                sy = (float) x / h;
                Log.e("bitmaputil---", "w/h--->=1");
            } else {
                sx = (float) x / w;
                sy = (float) y / h;
                Log.e("bitmaputil---", "w/h---<1");
            }
            Matrix matrix = new Matrix();
            matrix.postScale(sx, sy); // 长和宽放大缩小的比例
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
            Log.e("bitmaputil---", "w---" + resizeBmp.getWidth() + "h--" + resizeBmp.getHeight());
            return resizeBmp;
        }

        return null;
    }

    /**
     * 创建目录
     *
     * @param context
     * @param dirName 文件夹名称
     * @return
     */
    public static File createFileDir(Context context, String dirName) {
        String filePath;
        // 如SD卡已存在，则存储；反之存在data目录下
        if (isMountedSDCard()) {
            // SD卡路径
            filePath = Environment.getExternalStorageDirectory() + File.separator + Constant.SD_READER + "/" + dirName;//+"/books"可分层添加
        } else {
            filePath = context.getCacheDir().getPath() + File.separator + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {//如果不存在,新建
            boolean isCreate = destDir.mkdirs();//可创建多层文件夹
            Log.i("FileUtils", filePath + " has created. " + isCreate);
        }
        return destDir;
    }

    /**
     * 删除文件（若为目录，则递归删除子目录和文件）
     *
     * @param file
     * @param delThisPath true代表删除参数指定file，false代表保留参数指定file
     */
    public static void delFile(File file, boolean delThisPath) {
        LogUtils.i(file.getPath());
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                // 删除子目录和文件
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
            }
        }
        if (delThisPath) {
            file.delete();
        }
    }

    /**
     * 保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content)
            throws IOException {
        saveToFile(filePath, content, System.getProperty("file.encoding"));
    }

    /**
     * 指定编码保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     * @param encoding 写文件编码
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content,
                                  String encoding) throws IOException {
        BufferedWriter writer = null;
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false), encoding));
            writer.write(content);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    /**
     * 获取文件大小，单位为byte（若为目录，则包括所有子目录和文件）
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 获取文件大小，单位为String（若为目录，则包括所有子目录和文件）
     *
     * @return
     */
    public static String getFolderSize(Context context) {
        long size = 0;
        File file = new File(getExternalFilesDirPath(context));
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return Formatter.formatFileSize(context, size);
    }


    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取SD卡剩余容量
     * String 类型
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String gainSDFreeSize(Context context) {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
//            return freeBlocks * blockSize; // 单位Byte
            return Formatter.formatFileSize(context, freeBlocks * blockSize);// Byte转换为KB或者MB，内存大小规格化;
        } else {
            return "";
        }
    }

    /**
     * 获取SD卡剩余容量
     * long类型
     *
     * @return
     */
    public static long gainSDFreeSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
            return freeBlocks * blockSize; // 单位Byte
        } else {
            return 0;
        }
    }

    /**
     * 获取SD卡总容量
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String gainSDAllSize(Context context) {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
//            return allBlocks * blockSize;（单位Byte）
            return Formatter.formatFileSize(context, allBlocks * blockSize);// Byte转换为KB或者MB，内存大小规格化;
        } else {
            return "";
        }
    }


    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     *
     * @return
     */

    public static String gainSDCardPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
            }
            return sdcardDir.getPath();
        }
        return "";
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     * @throws Exception
     */
    public static Boolean isExsit(String filePath) {
        Boolean flag = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = true;
            }
        } catch (Exception e) {
            LogUtils.e("判断文件失败-->" + e.getMessage());
        }

        return flag;
    }

    /**
     * 快速读取程序应用包下的文件内容
     *
     * @param context  上下文
     * @param filename 文件名称
     * @return 文件内容
     * @throws IOException
     */
    public static String read(Context context, String filename)
            throws IOException {
        FileInputStream inStream = context.openFileInput(filename);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }


    /**
     * 读取指定目录文件的文件内容
     *
     * @param fileName 文件名称
     * @return 文件内容
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String read(String fileName) throws IOException {
        FileInputStream inStream = new FileInputStream(fileName);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

    /**
     * 根据路径获取文件名(带后缀名)
     *
     * @param pathandname//路径
     * @return
     */
    public static String getFileNameSuffix(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        //带后缀
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }
    }

    /**
     * 根据路径获取文件名(不带后缀名)
     *
     * @param pathandname//路径
     * @return
     */
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        //不带后缀
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }
    }

    /**
     * 根据路径获取文件夹中的所有文件(带后缀名)
     *
     * @param filePath//路径
     * @return
     */
    public static ArrayList<String> getFileDirSuffix(String filePath) {
        ArrayList<String> items = new ArrayList<String>();
        try {
            File f = new File(filePath);
            File[] files = f.listFiles();// 列出所有文件
            // 如果不是根目录,则列出返回根目录和上一目录选项
            // 将所有文件存入list中
            if (files != null) {
                int count = files.length;// 文件个数
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    items.add(file.getName());
                }
            }
        } catch (Exception e) {

        }

        return items;
    }

    /**
     * 根据路径获取文件夹中的所有文件(不带后缀名)
     *
     * @param filePath//路径
     * @return
     */
    public static ArrayList<String> getFileDir(String filePath) {
        ArrayList<String> items = new ArrayList<String>();
        try {
            File f = new File(filePath);
            File[] files = f.listFiles();// 列出所有文件
            // 如果不是根目录,则列出返回根目录和上一目录选项
            // 将所有文件存入list中
            if (files != null) {
                int count = files.length;// 文件个数
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    items.add(getFileName(file.getPath()));
                }
            }
        } catch (Exception e) {

        }

        return items;
    }

    /**
     * 复制文件
     *
     * @param oldFilePath
     * @param newFilePath
     * @return
     */
    public static String fileCopy(String oldFilePath, String newFilePath) {
        LogUtils.i(oldFilePath + "+" + newFilePath);
        //如果原文件不存在
        if (fileExists(oldFilePath) == false) {
            LogUtils.i("源文件不存在");
            return "";
        }
        File oldFile;
        File newFile;
        //获得原文件流
        FileInputStream inputStream = null;
        try {
            String name = getFileNameSuffix(oldFilePath);
            newFilePath = newFilePath + "/" + name;
            oldFile = new File(oldFilePath);
            inputStream = new FileInputStream(oldFile);

            byte[] data = new byte[1024];
            newFile = new File(newFilePath);
            //输出流
            FileOutputStream outputStream = new FileOutputStream(newFile);
            //开始处理流
            while (inputStream.read(data) != -1) {
                outputStream.write(data);
            }
            Long a = getFileSize(newFile);
            Long b = getFileSize(oldFile);
            LogUtils.i(a + "+" + b);
            delFile(new File(oldFilePath), true);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFilePath;
    }

    private static boolean fileExists(String oldFilePath) {
        File file = new File(oldFilePath);
        return file.exists();

    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            LogUtils.i(buffer.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 根据byte数组，生成文件
     */
    public static boolean getFile(byte[] bfile, String filePath) {
        boolean flag = false;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            if (bfile != null) {
                File dir = new File(filePath);
                if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                    dir.mkdirs();
                }
                file = new File(filePath);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bfile);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static String notice;

    public static String checkSD(Context contect) {

        File data = Environment.getDataDirectory();
        //获得data的路径
        StatFs data_stat = new StatFs(data.getPath()); //创建StatFs对象
        long data_blockSize = data_stat.getBlockSize(); //获取block的size
        float data_totalBlocks = data_stat.getBlockCount();//获取block的个数
        int data_sizeInMb = (int) (data_blockSize * data_totalBlocks) / 1024 / 1024;//计算总容量
        long data_availableBlocks = data_stat.getAvailableBlocks(); //获取可用block的个数
        float data_percent = (int) (data_blockSize * data_availableBlocks) / 1024 / 1024;//计算可用容量
        notice = "FLASH使用情况：\n总容量：" + data_sizeInMb + "M.\n已用:" + (data_sizeInMb - data_percent + "\n可用:" + data_percent + "M.");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File filePath = Environment.getExternalStorageDirectory();    //获得sd 卡的路径

            StatFs stat = new StatFs(filePath.getPath());                 //创建StatFs对象
            long blockSize = stat.getBlockSize();                         //获取block的size
            float totalBlocks = stat.getBlockCount();                     //获取block的个数
            int sizeInMb = (int) (blockSize * totalBlocks) / 1024 / 1024;       //计算总容量
            long availableBlocks = stat.getAvailableBlocks();             //可用block的个数
            float percent = (int) (blockSize * availableBlocks) / 1024 / 1024;              //计算可用容量

            notice = notice + "\nSD卡使用情况：\n总容量：" + sizeInMb + "M.\n已用：" + (sizeInMb - percent + "M\n可用:" + percent + "M.");
            return notice;

        } else {
            notice = notice + "\nSD卡使用情况：未插入SD卡";

            return notice;
        }
    }

}
