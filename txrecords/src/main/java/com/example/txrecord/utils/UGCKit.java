package com.example.txrecord.utils;

import android.content.Context;

import com.example.txrecord.UGCKitImpl;

public class UGCKit {

    /**
     * UGCKit的初始化函数
     *
     * @param context 应用的上下文，一般为对应应用的ApplicationContext
     */
    public static void init(Context context) {
        UGCKitImpl.init(context);
    }

    /**
     * 获取UGCKit保存的上下文Context，该Context会长期持有，所以应该为Application级别的上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return UGCKitImpl.getAppContext();
    }

}
