package shreyas.com.ifactorapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private List<User> userList;
    private ListView userListView;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userListView = (ListView) findViewById(R.id.userList);
        try{
            Log.i(TAG,"requesting users");
            new RetrieveUsersTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class RetrieveUsersTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://jsonplaceholder.typicode.com/users");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);

            try {
                JSONArray responseArray = new JSONArray(response);
                userList = new ArrayList<User>();
                for(int i=0;i<responseArray.length();i++) {
                    JSONObject userJSON = responseArray.getJSONObject(i);
                    userList.add(getUserObject(userJSON));
                }

                userListAdapter = new UserListAdapter(getApplicationContext(),userList.toArray(new User[userList.size()]));
                userListView.setAdapter(userListAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private User getUserObject(JSONObject jsonObject){
            try {
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String username = jsonObject.getString("username");
                String email = jsonObject.getString("email");
                Address address = getAddressObject(jsonObject.getJSONObject("address"));
                return new User(id,name,username,email,address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Address getAddressObject(JSONObject jsonObject){
            try {
                String street = jsonObject.getString("street");
                String suite = jsonObject.getString("suite");
                String city = jsonObject.getString("city");
                String zipcode = jsonObject.getString("zipcode");
                return new Address(street,suite,city,zipcode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
