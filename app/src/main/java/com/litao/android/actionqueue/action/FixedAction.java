package com.litao.android.actionqueue.action;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.litao.android.lib.Action;
import com.litao.android.lib.ActionConfig;

/**
 * Created by litao on 2018/3/8.
 */

public class FixedAction extends Action<String> {
    public FixedAction(Context mContext, String mBadge) {
        super(mContext, mBadge);
    }

    @Override
    public int createId() {
        return ActionIds.ACTION_ID_TEST_3;
    }

    @Override
    public ActionConfig createConfig() {
        return new ActionConfig.Builder()
                .setIsFixed(true)
                .build();
    }

    @Override
    public void onAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getBadge())
                .setMessage(getClass().getSimpleName() + "我具有 Fixed 属性，不会被移除")
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
