package com.reeching.epub.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koolearn.android.kooreader.KooReader;
import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.koolearn.android.util.LogUtils;
import com.koolearn.kooreader.book.Book;
import com.reeching.epub.R;
import com.reeching.epub.activity.DownloadActivity;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.GlideUtils;
import com.reeching.epub.view.NumberProgressBar;

import java.util.List;

import okserver.download.DownloadInfo;
import okserver.download.DownloadManager;
import okserver.listener.DownloadListener;

import static com.reeching.epub.utils.AesUtils.decrypt;
import static com.reeching.epub.utils.FileUtil.getBytes;
import static com.reeching.epub.utils.FileUtil.getFile;

/**
 * Created by 绍轩 on 2017/10/24.
 */

public class DownloadAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private DownloadActivity context;
    private List<DownloadInfo> allTask;
    private DownloadManager downloadManager;
    private BookCollectionShadow myCollection;
    public DownloadAdapter(DownloadActivity downloadActivity, List<DownloadInfo> allTask, DownloadManager downloadManager,BookCollectionShadow myCollection) {
        this.context=downloadActivity;
        this.allTask=allTask;
        this.downloadManager=downloadManager;
        this.myCollection=myCollection;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (allTask.size()==0){
            return 0;
        }
        return allTask.size();
    }

    @Override
    public DownloadInfo getItem(int position) {
        return allTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DownloadInfo downloadInfo = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_download_manager, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.refresh(downloadInfo);

        //对于非进度更新的ui放在这里，对于实时更新的进度ui，放在holder中
//        ApkModel apk = (ApkModel) downloadInfo.getData();
//        if (apk != null) {
//            Glide.with(context).load(apk.getIconUrl()).error(R.mipmap.ic_launcher).into(holder.icon);
//            holder.name.setText(apk.getName());
//        } else {
//        }
        LogUtils.i(downloadInfo.getImg());
        GlideUtils.loadImageViewError(context,downloadInfo.getImg(),holder.icon,R.mipmap.ic_launcher);
        holder.name.setText(downloadInfo.getFileName());
        holder.download.setOnClickListener(holder);
        holder.remove.setOnClickListener(holder);
        holder.restart.setOnClickListener(holder);

        DownloadListener downloadListener = new MyDownloadListener();
        downloadListener.setUserTag(holder);
        downloadInfo.setListener(downloadListener);
        return convertView;
    }
    private class ViewHolder implements View.OnClickListener {
        private DownloadInfo downloadInfo;
        private ImageView icon;
        private TextView name;
        private TextView downloadSize;
        private TextView tvProgress;
        private TextView netSpeed;
        private NumberProgressBar pbProgress;
        private Button download;
        private Button remove;
        private Button restart;

        public ViewHolder(View convertView) {
            icon = (ImageView) convertView.findViewById(R.id.icon);
            name = (TextView) convertView.findViewById(R.id.name);
            downloadSize = (TextView) convertView.findViewById(R.id.downloadSize);
            tvProgress = (TextView) convertView.findViewById(R.id.tvProgress);
            netSpeed = (TextView) convertView.findViewById(R.id.netSpeed);
            pbProgress = (NumberProgressBar) convertView.findViewById(R.id.pbProgress);
            download = (Button) convertView.findViewById(R.id.start);
            remove = (Button) convertView.findViewById(R.id.remove);
            restart = (Button) convertView.findViewById(R.id.restart);
        }

        public void refresh(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
            refresh();
        }

        //对于实时更新的进度ui，放在这里，例如进度的显示，而图片加载等，不要放在这，会不停的重复回调
        //也会导致内存泄漏
        private void refresh() {
            String downloadLength = Formatter.formatFileSize(context, downloadInfo.getDownloadLength());
            String totalLength = Formatter.formatFileSize(context, downloadInfo.getTotalLength());
            downloadSize.setText(downloadLength + "/" + totalLength);
            if (downloadInfo.getState() == DownloadManager.NONE) {
                netSpeed.setText("停止");
                download.setText("下载");
            } else if (downloadInfo.getState() == DownloadManager.PAUSE) {
                netSpeed.setText("暂停中");
                download.setText("继续");
            } else if (downloadInfo.getState() == DownloadManager.ERROR) {
                netSpeed.setText("下载出错");
                download.setText("出错");
            } else if (downloadInfo.getState() == DownloadManager.WAITING) {
                netSpeed.setText("等待中");
                download.setText("等待");
            } else if (downloadInfo.getState() == DownloadManager.FINISH) {
                netSpeed.setText("下载完成");
                download.setText("阅读");
            } else if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {
                String networkSpeed = Formatter.formatFileSize(context, downloadInfo.getNetworkSpeed());
                netSpeed.setText(networkSpeed + "/s");
                download.setText("暂停");
            }
            tvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
            pbProgress.setMax((int) downloadInfo.getTotalLength());
            pbProgress.setProgress((int) downloadInfo.getDownloadLength());
        }

        @Override
        public void onClick(View v) {

            final String filePath = FileUtil.getExternalFilesDirPath(context) + downloadInfo.getFileName() + ".epub";//解密完成
            if (v.getId() == download.getId()) {
                switch (downloadInfo.getState()) {
                    case DownloadManager.PAUSE:
                    case DownloadManager.NONE:
                    case DownloadManager.ERROR:
                        downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getImg(),downloadInfo.getBookid(),downloadInfo.getRequest(), downloadInfo.getListener());
                        break;
                    case DownloadManager.DOWNLOADING:
                        downloadManager.pauseTask(downloadInfo.getUrl());
                        break;
                    case DownloadManager.FINISH:
                        myCollection.bindToService(context, new Runnable() {
                            public void run() {
                                if (getFile(decrypt( getBytes( downloadInfo.getTargetPath())),filePath)){
                                    Book book = myCollection.getBookByFile(filePath);
                                    if (book != null) {
                                        openBook(book,downloadInfo.getBookid());
                                    } else {
                                        Toast.makeText(context, "打开失败,请重试", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    LogUtils.i("解密失败");
                                }
                            }
                        });
                        break;
                }
                refresh();
            } else if (v.getId() == remove.getId()) {
                new AlertDialog.Builder(context).setTitle("删除本地书籍")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadManager.removeTask(downloadInfo.getUrl(),true);
                                context.concealView();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                LogUtils.i(downloadInfo.getUrl());

            } else if (v.getId() == restart.getId()) {
                downloadManager.restartTask(downloadInfo.getUrl());
                refresh();
                notifyDataSetChanged();
            }
        }
    }
    private void openBook(Book book,String bookId) {
        KooReader.openBookActivity(context, book, null,bookId);
    }
    private class MyDownloadListener extends DownloadListener {

        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            if (getUserTag() == null) return;
            ViewHolder holder = (ViewHolder) getUserTag();
            holder.refresh();  //这里不能使用传递进来的 DownloadInfo，否者会出现条目错乱的问题
        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            LogUtils.i(downloadInfo.getUrl());

//            Toast.makeText(context, "下载完成:" + downloadInfo.getTargetPath(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            if (errorMsg != null) Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
    
}
