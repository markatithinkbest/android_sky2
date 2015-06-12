package com.giant.Idcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PunchCardIint extends PunchCard{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bData = intent.getExtras();
        if(bData.get("msg").equals("ClockIn"))
        {
            Log.e("鬧鐘2","FFFFFFFFFFFFFFF");
        }else if(bData.get("msg").equals("ClockOut")){
            Log.e("鬧鐘2","FFFFFFFFFFFFFFF");
        }
    }
}
