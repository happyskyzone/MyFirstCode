package com.zhuoyue.activitycontroller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**用来优雅退出应用，建立一个管理类来将所有Activity添加到一个list进行管理
 * Created by zhuoyue on 2017/1/21.
 */

public class ActivityController {

    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //管理应用即清除所有activity
    public static void finishAll(){
        for (Activity activity : activities) {
            if (!activity.isFinishing()){
               activity.finish();
            }
        }
    }
}
