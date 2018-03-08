package com.litao.android.lib;

/**
 * Created by litao on 2018/1/4.
 */

public class ActionConfig {

    /**
     * 优先级
     */
    final int mPriority;
    /**
     * 是否允许重复
     */
    final boolean isDuplicateEnabled;
    /**
     * 是否需要移除当前action 之后所有 action (受isFixed属性影响)
     */
    final boolean needsRemoveOther;
    /**
     * 是否强制在队列中保留 （当指定 needRemoveOther  后  生效）
     */
    final boolean isFixed;
    /**
     * 入队时间
     */
    final long timestamp;


    public ActionConfig(final Builder builder) {
        this.mPriority = builder.mPriority;
        this.needsRemoveOther = builder.needsRemoveOther;
        this.isDuplicateEnabled = builder.isDuplicateEnabled;
        this.isFixed = builder.isFixed;
        this.timestamp = builder.timestamp;
    }

    public static class Builder {
        private int mPriority;
        private boolean needsRemoveOther;
        private boolean isDuplicateEnabled;
        private boolean isFixed;
        private long timestamp;

        public Builder() {
            this.needsRemoveOther = false;
            this.isDuplicateEnabled = false;
            this.isFixed = false;
            this.timestamp = System.nanoTime();
        }

        public Builder setPriority(int mPriority) {
            this.mPriority = mPriority;
            return this;
        }

        public Builder setNeedRemoveOther(boolean needsRemoveOther) {
            this.needsRemoveOther = needsRemoveOther;
            return this;
        }

        public Builder setIsDuplicateEnabled(boolean isDuplicateEnabled) {
            this.isDuplicateEnabled = isDuplicateEnabled;
            return this;
        }

        public Builder setIsFixed(boolean isFixed) {
            this.isFixed = isFixed;
            return this;
        }

        public ActionConfig build() {
            return new ActionConfig(this);
        }
    }


}
