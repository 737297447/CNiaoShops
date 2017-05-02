package com.chhd.cniaoshops.http.bmob;

import com.chhd.cniaoshops.R;
import com.chhd.cniaoshops.util.LoggerUtil;
import com.chhd.cniaoshops.util.ToastyUtil;
import com.chhd.per_library.util.UiUtil;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by CWQ on 2017/4/20.
 */

public class BmobEx {

    public static int handlerError(BmobException e) {
        LoggerUtil.e(e);
        switch (e.getErrorCode()) {
            case 101:// username or password incorrect
                ToastyUtil.error(UiUtil.getString(R.string.username_or_pwd_incoreect));
                break;
            case 202:// username already taken
                ToastyUtil.error(UiUtil.getString(R.string.username_already_exists));
                break;
            case 10010:// mobile phone send message limited.
                ToastyUtil.error(UiUtil.getString(R.string.moblie_phone_send_message_limited));
                break;
            case 10011:// no remaining sdk sms number for send messages.
                ToastyUtil.error(UiUtil.getString(R.string.no_remaining_sdk_sms_number_for_send_messages));
                break;
            default:
                ToastyUtil.error("Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
                break;
        }
        return e.getErrorCode();
    }
}
