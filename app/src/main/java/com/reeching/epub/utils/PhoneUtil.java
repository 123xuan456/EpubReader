package com.reeching.epub.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by 绍轩 on 2017/10/25.
 */

public class PhoneUtil {
    private static PhoneUtil phoneUtil;

    public static PhoneUtil getInstance() {
        if (phoneUtil == null) {
            synchronized (PhoneUtil.class) {
                if (phoneUtil == null) {
                    phoneUtil = new PhoneUtil();
                }
            }
        }
        return phoneUtil;
    }
    /**
     * 获取手机型号
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sdkVersion = 0;
        }
        return sdkVersion;
    }
    /**
     * 获取手机imei串号 ,GSM手机的 IMEI 和 CDMA手机的 MEID.
     * @param context
     */
    public String getPhoneImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return null;
        return tm.getDeviceId();
    }


}
