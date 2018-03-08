package com.litao.android.actionqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.litao.android.actionqueue.action.DuplicateAction;
import com.litao.android.actionqueue.action.FixedAction;
import com.litao.android.actionqueue.action.HighPriorityAction;
import com.litao.android.actionqueue.action.NormalAction;
import com.litao.android.actionqueue.action.RemoveOtherAction;
import com.litao.android.lib.Action;
import com.litao.android.lib.ActionQueue;

public class MainActivity extends AppCompatActivity implements Action.OnActionFinishListener {

    private ActionQueue mActionQueue = new ActionQueue();

    private StringBuilder mStringBuilder = new StringBuilder();

    private TextView mTextViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewLog = findViewById(R.id.log);

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                execute();
            }
        });
    }

    public void execute(){
        mStringBuilder.delete(0, mStringBuilder.length());

        for (int i = 0; i < 3; i++) {
            addAction(new NormalAction(this,"普通ACTION"));
        }

        for (int i = 0; i < 3; i++) {
            addAction(new DuplicateAction(this,"可以重复的ACTION"));
        }

        addAction(new RemoveOtherAction(this,"可移除其余未执行的ACTION"));

        for (int i = 0; i < 3; i++) {
            addAction(new DuplicateAction(this,"受前面action 影响，我不会执行"));
        }

        addAction(new FixedAction(this,"我具有 Fixed 属性，不会被移除"));

        addAction(new HighPriorityAction(this,"我具有 较高优先级 ，会优先显示"));


    }



    public void addAction(Action action){
        mStringBuilder.append("添加----》"+action.getClass().getSimpleName() + "\n");
        mTextViewLog.setText(mStringBuilder);

        //action 中任务结束 调用 listener.onActionFinish();
        action.setActionFinishListener(this);
        mActionQueue.add(action);
    }

    @Override
    public void onActionFinish() {
        mActionQueue.popNext();
    }
}
