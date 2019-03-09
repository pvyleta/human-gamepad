package cz.butab.humanpad;

import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by petrkristan on 09.03.19.
 */

public class makeRequest {
    static  void  postJSON(String urlweb, String id, String player, String action) throws UnsupportedEncodingException {
        BufferedReader reader=null;
        String text = "0";

        try {
            URL url = new URL(urlweb);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("keycode", id);
            jsonParam.put("player", player);
            jsonParam.put("action", action);


            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//            os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString().trim();
            Log.i("REQUEST: ", "Odpoved serveru: " + text);

            conn.disconnect();

        }
            catch(Exception ex)
        {
            ex.printStackTrace();
        }
            finally
        {
            try
            {
                reader.close();
            }
            catch(Exception ex) {}
        }

    }


    static void postURL(String urlweb, String id, String player, String action) throws UnsupportedEncodingException {
        BufferedReader reader=null;
        String text = "0";

        try {
            URL url = new URL(urlweb);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // https removed HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("keycode", id)
                    .appendQueryParameter("player", player)
                    .appendQueryParameter("action", action);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            Log.i("REQUEST: ",  "Odesilam: " + String.valueOf(id));


            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString().trim();
            Log.i("REQUEST: ", "Odpoved serveru: " + text);




            conn.connect();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch(Exception ex) {}
        }
    }


    // obsolate, it will be removed
    static void getURL(String urlweb, String id) throws UnsupportedEncodingException {
        BufferedReader reader=null;
        String text = "0";
        String data = "keycode=" + id;

        // Send data
        try
        {
            // Defined URL  where to send data
            Log.d("URL: ", urlweb+"/");
            URL url = new URL(urlweb+"/");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            Log.i("REQUEST: ",  "Odesilam: " + String.valueOf(id));

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            text = sb.toString().trim();
            Log.i("REQUEST: ", "Odpoved serveru: " + text);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch(Exception ex) {}
        }


    }
}
