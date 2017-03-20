package alex.carbon_tracker.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sachin on 2017-03-19.
 */

public class SaveData {
    public static  void storeSharePreference(Context contex){
        SharedPreferences pref = contex.getSharedPreferences("app",0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gsonModel = new Gson();
        String dummy = gsonModel.toJson(CarbonTrackerModel.getInstance(contex));
        editor.putString("model",dummy);
        editor.apply();
    }

    public static CarbonTrackerModel getSharePreference(Context context){
        SharedPreferences preferences = context.getSharedPreferences("app",0);
        Gson gson = new Gson();
        String carbonModel = preferences.getString("model",null);
        Type type = new TypeToken<CarbonTrackerModel>(){}.getType();
        return gson.fromJson(carbonModel,type);
    }
}
