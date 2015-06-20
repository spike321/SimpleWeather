package sahiti.simpleweather1056;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RemoteFetch {

    private static final String API ="https://api.forecast.io/forecast/ca05a018bff6ebdd72951bb203fac735/%s";


    public static JSONObject getJSON(Context context, String coord){
        try {
            //coord = "40.7127,-74.0059";//debug
            URL url = new URL(String.format((API), coord));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            return data;
        }catch(Exception e){
            return null;
        }
    }
}