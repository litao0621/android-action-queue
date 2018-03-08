package com.litao.android.lib;

import android.content.Context;

/**
 * Created by litao on 2018/1/4.
 */

public abstract class Action<T> {
    /**
     * 优先级：高
     */
    public static final int PRIORITY_HIGH = Integer.MAX_VALUE;
    /**
     * 优先级：中
     */
    public static final int PRIORITY_NORMAL = 0;
    /**
     * 优先级：低
     */
    public static final int PRIORITY_LOW = Integer.MIN_VALUE;

    protected Context mContext;

    /**
     * action id (必须指定)
     */
    private int mId;
    /**
     * action 携带的数据
     */
    private T mBadge;
    /**
     * action 配置
     */
    ActionConfig mConfig;


    protected OnActionFinishListener listener;

    public interface OnActionFinishListener {
        void onActionFinish();
    }


    public Action(Context mContext, T mBadge) {
        this(mContext, mBadge, null);
    }

    public Action(Context mContext, T mBadge, ActionConfig mConfig) {
        int id = createId();

        this.mBadge = mBadge;

        if (id == 0) {
            throw new IllegalStateException("必须在 createId 方法中 指定 action id.");
        }
        this.mId = id;

        this.mContext = mContext;

        ActionConfig config = null == mConfig ? createConfig() : mConfig;

        if (null == config) {
            config = new ActionConfig.Builder().build();
        }
        this.mConfig = config;
    }


    /**
     * 创建action ID
     */
    public abstract int createId();

    /**
     * 创建action 配置
     */
    public abstract ActionConfig createConfig();

    /**
     * 执行 action
     */
    public abstract void onAction();

    /**
     * 销毁 action
     */
    public abstract void onDestroy();


    public void setActionFinishListener(OnActionFinishListener listener) {
        this.listener = listener;
    }

    void doAction() {
        try {
            onAction();
        } catch (Exception e) {
            listener.onActionFinish();
            e.printStackTrace();
        }
    }

    public void setEmptyListenerAndDoAction(){
        setActionFinishListener(new OnActionFinishListener() {
            @Override
            public void onActionFinish() {

            }
        });
        doAction();
    }

    protected T getBadge() {
        return mBadge;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Action)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        return mBadge.equals(((Action<?>) o).mBadge) && mId == ((Action<?>) o).mId;
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + mBadge.hashCode();
        return result;
    }
}
