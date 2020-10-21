import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class ExChangeAPI {
    //class attributes
    private String result;
    private int time_last_update_unix;
    private String time_last_update_utc;
    private int time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private JSONObject eachRete;
    private static String url_APT = "https://v6.exchangerate-api.com/v6/6f18ee46219319be9c30e085/latest/";
    private static JSONObject jsonObject;
    //connect to sever
    public boolean getConnection(String base_code) {
        String json = "";
        URL url = null;
        HttpURLConnection request = null;
        try {
            url = new URL(url_APT + base_code );
            request = (HttpURLConnection) url.openConnection();
            //connect to sever
            request.connect();
            //read data from sever
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            if (line.length() >0){
                json += line;
            }
            //่json to jsonObject
            jsonObject = new JSONObject(json);
            if (jsonObject == null)
                return false;
            this.result = jsonObject.getString("result");
            this.base_code = jsonObject.getString("base_code");
            this.eachRete = jsonObject.getJSONObject("conversion_rates");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }//getConnection
    public String getResult() {
        return result;
    }
    public String getBase_code() {
        return base_code;
    }
    public double  getEachRete(String newCurrency) {
        double eachRate = 0.0;
        try {
            eachRate = this.eachRete.getDouble(newCurrency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eachRate;
    }
    public JSONObject getEachRete() {
        return eachRete;
    }
}