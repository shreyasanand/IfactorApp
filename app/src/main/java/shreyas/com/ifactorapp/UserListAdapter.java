package shreyas.com.ifactorapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shreyas on 4/22/2016.
 */
public class UserListAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final User[] users;

    public UserListAdapter(Context context, User[] users) {
        super(context, -1, users);
        this.users = users;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userRow = inflater.inflate(R.layout.user_list_row, parent, false);
        TextView userNameTextView = (TextView) userRow.findViewById(R.id.username);
        TextView addressTextView = (TextView) userRow.findViewById(R.id.address);
        Button viewUserPosts = (Button) userRow.findViewById(R.id.button);

        userNameTextView.setText(users[position].getUsername());
        addressTextView.setText(users[position].getAddress().toString());

        viewUserPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveUserPostsTask(context).execute(users[position].getId());
            }
        });

        return userRow;
    }

    class RetrieveUserPostsTask extends AsyncTask<Integer, Void, String> {

        private int userId;
        private Context context;

        public RetrieveUserPostsTask(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(Integer... params) {
            userId = params[0];
            URL url = null;
            try {
                url = new URL("http://jsonplaceholder.typicode.com/posts?userId="+userId);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);

            try {
                JSONArray responseArray = new JSONArray(response);
                Post[] posts = new Post[responseArray.length()];
                for(int i=0;i<posts.length;i++){
                    posts[i] = getUserPost(userId, responseArray.getJSONObject(i));
                }
                Intent i = new Intent(getContext(), PostActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("posts",posts);
                context.startActivity(i);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        private Post getUserPost(int userId, JSONObject jsonObject) {
            try {
                int postId = jsonObject.getInt("id");
                String title = jsonObject.getString("title");
                String body = jsonObject.getString("body");
                return new Post(postId,userId,title,body);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
