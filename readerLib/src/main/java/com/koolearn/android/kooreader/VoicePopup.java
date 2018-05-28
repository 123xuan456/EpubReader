package com.koolearn.android.kooreader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.iflytek.cloud.SpeechSynthesizer;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.koolearn.klibrary.android.R;
import com.koolearn.klibrary.core.application.ZLApplication;
import com.koolearn.kooreader.kooreader.KooReaderApp;

final class VoicePopup extends ZLApplication.PopupPanel {
    final static String ID = "VoicePopup";
    private volatile RelativeLayout myRoot;
    private volatile VoiceWindow myWindow;
    private volatile KooReader myActivity;
    private final KooReaderApp myKooReader;
    private volatile boolean myIsInProgress;
    private SpeechSynthesizer mTts;
    // 数据接口
    VoicePopup(KooReaderApp kooReader,SpeechSynthesizer mTts) {
        super(kooReader);
        myKooReader = kooReader;
        this.mTts = mTts;
    }

    public void setPanelInfo(KooReader activity, RelativeLayout root) {
        myActivity = activity;
        myRoot = root;
    }

    public void runNavigation() {
        if (myWindow == null || myWindow.getVisibility() == View.GONE) {
            myIsInProgress = false;
            Application.showPopup(ID);
        }
    }

    @Override
    protected void show_() {
        setStatusBarVisibility(true);
        if (myActivity != null) {
            createPanel(myActivity, myRoot);
        }
        if (myWindow != null) {
            myWindow.show();
        }
        SharedPreferencesUtil.getInstance().putString("onSpeak","stop");
        mTts.pauseSpeaking();
    }

    @Override
    protected void hide_() {
        setStatusBarVisibility(false);
        if (myWindow != null) {
            myWindow.hide();
        }
        SharedPreferencesUtil.getInstance().putString("onSpeak","playing");
        mTts.resumeSpeaking();
    }

    private void setStatusBarVisibility(boolean visible) {
        if (visible) {
            myActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // 设置状态栏
        } else {
            myActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected void update() {
    }

    private void createPanel(final KooReader activity, RelativeLayout root) {
        if (myWindow != null && activity == myWindow.getContext()) {
            return;
        }
        activity.getLayoutInflater().inflate(R.layout.volice_panel, root);
        myWindow = (VoiceWindow) root.findViewById(R.id.voice_panel);

        final Button tts_play = (Button) myWindow.findViewById(R.id.tts_play);
        final Button tts_cancel = (Button) myWindow.findViewById(R.id.tts_cancel);
        final Button tts_pause = (Button) myWindow.findViewById(R.id.tts_pause);
        final Button tts_resume = (Button) myWindow.findViewById(R.id.tts_resume);
        //取消合成
        tts_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.hideActivePopup();
                mTts.stopSpeaking();
                SharedPreferencesUtil.getInstance().putString("onSpeak","stop");
            }
        });
        //暂停
        tts_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.hideActivePopup();
                mTts.pauseSpeaking();
                SharedPreferencesUtil.getInstance().putString("onSpeak","stop");
            }
        });
        //继续
        tts_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.hideActivePopup();
                mTts.resumeSpeaking();
                SharedPreferencesUtil.getInstance().putString("onSpeak","playing");
            }
        });

    }


    final void removeWindow(Activity activity) {
        if (myWindow != null && activity == myWindow.getContext()) {
            final ViewGroup root = (ViewGroup) myWindow.getParent();
            myWindow.hide();
            root.removeView(myWindow);
            myWindow = null;
        }
    }
}