package shreyas.com.ifactorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class PostActivity extends AppCompatActivity {

    private ListView postListView;
    private PostListAdapter postListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Post[] posts = (Post[]) i.getSerializableExtra("posts");

        postListView = (ListView) findViewById(R.id.postList);
        postListAdapter = new PostListAdapter(getApplicationContext(),posts);
        postListView.setAdapter(postListAdapter);
    }

}
