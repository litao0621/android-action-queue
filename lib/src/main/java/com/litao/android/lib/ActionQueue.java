package com.litao.android.lib;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by litao on 2018/1/4.
 */

public class ActionQueue {
    /**
     * action 队列默认 容量
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    /**
     * 当前是否有正在执行的 action
     */
    private boolean isRunning = false;
    /**
     * action 队列
     */
    private Queue<Action> mPriorityQueue;
    /**
     * 当前 action
     */
    private Action mCurrentAction;

    public ActionQueue(){
        mPriorityQueue = new PriorityQueue<>(DEFAULT_INITIAL_CAPACITY, mPriorityComparator);
    }

    /**
     * 添加action
     * @param action
     */
    public void add (Action<?> action){
        if (action != null){
            boolean isContains = mPriorityQueue.contains(action) || (isRunning && mCurrentAction.equals(action));
            if (action.mConfig.isDuplicateEnabled || !isContains){
                mPriorityQueue.offer(action);
                tryPopNext();
            }
        }
    }

    /**
     * 试图弹出新action （非必定成功）
     */
    private void tryPopNext() {
        if (mPriorityQueue.isEmpty() || isRunning ) {
            return;
        }

        isRunning = true;
        mCurrentAction = mPriorityQueue.poll();

        if (mCurrentAction.mConfig.needsRemoveOther){
            clear();
        }

        mCurrentAction.doAction();
    }

    /**
     * 当前action任务确定已经执行完毕，开始执行下一个action
     */
    public void popNext() {
        isRunning = false;

        if (null != mCurrentAction){
            mCurrentAction.onDestroy();
            mCurrentAction = null;
        }

        tryPopNext();
    }

    /**
     * 清除数据
     * 受 needRemoveOther  和  isFixed 属性 影响
     * 只清除需要清除的数据 ，未必会清空数据
     */
    private void clear(){
        Iterator<Action> it = mPriorityQueue.iterator();
        while (it.hasNext()){
            Action temp = it.next();
            if (!temp.mConfig.isFixed){
                it.remove();
            }
        }
    }

    /**
     * 强制清空数据
     */
    private void forceClear(){
        mPriorityQueue.clear();
    }

    public int size(){
        return mPriorityQueue.size();
    }

    /**
     * 优先级比较器
     */
    private static Comparator<Action> mPriorityComparator = new Comparator<Action>(){

        @Override
        public int compare(Action a1, Action a2) {
            int value = a2.mConfig.mPriority - a1.mConfig.mPriority; //优先级相同情况下 按入队时间排序
            return value == 0 ? (int) (a2.mConfig.timestamp - a1.mConfig.timestamp) : value;
        }
    };
}
