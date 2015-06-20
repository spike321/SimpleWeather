package sahiti.simpleweather1056;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


//better naming scheme
public class SecondActivity extends Activity {

    private static final String API ="https://api.forecast.io/forecast/ca05a018bff6ebdd72951bb203fac735/%s";

    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    int backgroundImageId = 0;

    Button new_york;
    Button paris;
    Button london;
    Button los_angeles;
    Button tokyo;

    String coord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        Bundle extras = getIntent().getExtras();

        System.out.print("LALALALA" + extras.get("CityName"));
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CityName");
        setupNavBtn();

//        View lay = (View) findViewById(R.id.rLayout);
//        lay.setBackgroundResource(R.drawable.bluepink);

//        RelativeLayout baseLayout = (RelativeLayout) this.findViewById(R.id.the_layout_id);
//
//        Drawable drawable = loadImageFromAsset();
//
//        if(drawable != null){
//            baseLayout.setBackground(drawable);
//            Log.d("TheActivity", "Setting the background");
//        }

        chooseCity(cityName);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rLayout);
        rl.setBackgroundResource(backgroundImageId);

        int alphaAmount = 50;
        rl.getBackground().setAlpha(alphaAmount);

        //spacing issue

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //declare at the top if possible
        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        weatherFont = Typeface.createFromAsset(this.getAssets(), "weather.ttf");
        weatherIcon.setText(getString(R.string.weather_clear_night));
        cityField.setText(cityName);

        Context myContext = this;
        chooseCity(cityName);
        final JSONObject json = getJSON(myContext, coord);
        //Log.v("JSON:", json.toString());
        renderWeather(json);

    }

    //instead of using case, build a database and retrieve from databasel.
    private void chooseCity(String cityName){
        switch (cityName){
            case("New York"):
                coord = "42.3482,-75.1890";
                backgroundImageId = R.drawable.newyork;
                break;
            case("London"):
                coord = "51.5072,-0.1275";
                backgroundImageId = R.drawable.london;
                break;
            case("Los Angeles"):
                coord = "34.0500,-118.2500";
                backgroundImageId = R.drawable.losangeles;
                break;
            case("Paris"):
                coord = "48.8567,2.3508";
                backgroundImageId = R.drawable.paris;
                break;
            case("Tokyo"):
                backgroundImageId = R.drawable.tokyo;
                coord = "35.6833,139.6833";
                break;
            default:
                coord = "42.3482,-75.1890";
                break;
        }
    }

    private void setupNavBtn(){
        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "second click", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void renderWeather(JSONObject json){
        try {
            //detailsField.setText("");
            //cityField.setText("");
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);

            String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
            JSONArray data_array = json.getJSONObject("daily").getJSONArray("data");
            for (int i=0; i<7;i++){
                JSONObject item = data_array.getJSONObject(i);

                String temperatureMax = item.getString("temperatureMax");
                String temperatureMin = item.getString("temperatureMin");
                String w_summary = item.getString("summary");
                temperatureMax = temperatureMax.substring(0,2);
                temperatureMin = temperatureMin.substring(0,2);

                detailsField.setText(detailsField.getText()  + days[(today+i)%7] + ": "+temperatureMin+" - "+temperatureMax +" "+w_summary+ "\n");
            }



            //cityField.setText("New York");
            if(json.getString("timezone").contains("York"))
                cityField.setText("New York");
            if(json.getString("timezone").contains("London"))
                cityField.setText("London");
            if(json.getString("timezone").contains("Los"))
                cityField.setText("Los Angeles");
            if(json.getString("timezone").contains("Paris"))
                cityField.setText("Paris");
            if(json.getString("timezone").contains("Tokyo"))
                cityField.setText("Tokyo");


            currentTemperatureField.setText(json.getJSONObject("currently").getString("temperature") + " \u00b0 F");
            updatedField.setText(

                    // "SUMMARY OF WEEK  : " +
                    json.getJSONObject("daily").getString("summary")
                    // +      "\nTIME ZONE  : " + json.getString("timezone")
            );



        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    public static JSONObject getJSON(Context context, String coord){
        try {
            //coord = "40.7127,-74.0059";//debug
            URL url = new URL(String.format((API), coord));

            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            connection.getInputStream();

           System.out.print("CONNECTION:::" + connection.getInputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            System.out.print("url:::");
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            return data;
        }catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
