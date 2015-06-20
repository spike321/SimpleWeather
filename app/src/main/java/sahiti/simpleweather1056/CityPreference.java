package sahiti.simpleweather1056;

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreference {

    SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }

}