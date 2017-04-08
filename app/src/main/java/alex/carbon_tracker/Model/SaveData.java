package alex.carbon_tracker.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sachin on 2017-03-19.
 * <p>
 * SaveData class responsible for saving
 * data between sessions.
 */
public class SaveData {
    public static void storeSharePreference(Context contex) {
        SharedPreferences pref = contex.getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gsonModel = new Gson();
        String dummy = gsonModel.toJson(CarbonTrackerModel.getInstance());
        editor.putString("model18", dummy);
        editor.commit();
    }

    public static CarbonTrackerModel getSharePreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("app", MODE_PRIVATE);
        Gson gson = new Gson();
        String carbonModel = preferences.getString("model18", null);
        Type type = new TypeToken<CarbonTrackerModel>() {
        }.getType();
        return gson.fromJson(carbonModel, type);
    }
}
