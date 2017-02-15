package com.zhuoyue.boardcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherBoardcastReceiver extends BroadcastReceiver {
    public AnotherBoardcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"receive in AnotherBoardcastReceiver",Toast.LENGTH_SHORT).show();
    }
}
