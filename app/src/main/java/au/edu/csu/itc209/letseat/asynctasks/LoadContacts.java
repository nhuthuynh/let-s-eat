package au.edu.csu.itc209.letseat.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.adapters.ContactListAdapter;

public class LoadContacts extends AsyncTask<String, String, String> {
    private Context ctx;
    private ListView list;
    private ProgressDialog progressDialog;

    public LoadContacts(Context ctx, ListView list) {
        this.ctx = ctx;
        this.list = list;
        this.progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject resultJO = new JSONObject(result);
            JSONArray contactsJS = resultJO.getJSONArray("contacts");
            ContactListAdapter adapter = new ContactListAdapter(this.ctx, contactsJS);
            list.setAdapter(adapter);
            JSONObject johnnyJO = (JSONObject) contactsJS.get(3);
            Log.d("EMAIL!!!!", johnnyJO.getString("email"));
            progressDialog.hide();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        URLConnection urlConnection;
        String response = "";
        try {
            url = new URL(params[0]);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            urlConnection = url.openConnection();
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Content from Url", response);
        return response;
    }

    public String convertStreamToString(InputStream inputStr) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStr));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while((line = r.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
