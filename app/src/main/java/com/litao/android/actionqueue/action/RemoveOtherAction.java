package com.litao.android.actionqueue.action;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.litao.android.lib.Action;
import com.litao.android.lib.ActionConfig;

/**
 * Created by litao on 2018/3/8.
 */

public class RemoveOtherAction extends Action<String> {
    public RemoveOtherAction(Context mContext, String mBadge) {
        super(mContext, mBadge);
    }

    @Override
    public int createId() {
        return ActionIds.ACTION_ID_TEST_5;
    }

    @Override
    public ActionConfig createConfig() {
        return new ActionConfig.Builder()
                .setNeedRemoveOther(true)
                .build();
    }

    @Override
    public void onAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getBadge())
                .setMessage(getClass().getSimpleName() + "我将移除我之后所有Action,但不包括具有 fixed 属性的")
                .setNegativeButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        listener.onActionFinish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onDestroy() {

    }
}
