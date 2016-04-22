package shreyas.com.ifactorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by shreyas on 4/22/2016.
 */
public class PostListAdapter extends ArrayAdapter<Post> {

    private final Context context;
    private final Post[] posts;

    public PostListAdapter(Context context, Post[] posts) {
        super(context, -1, posts);
        this.posts = posts;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View postRow = inflater.inflate(R.layout.post_list_row, parent, false);
        TextView titleTextView = (TextView) postRow.findViewById(R.id.title);
        TextView bodyTextView = (TextView) postRow.findViewById(R.id.body);

        titleTextView.setText(posts[position].getTitle());
        bodyTextView.setText(posts[position].getBody());
        return postRow;
    }
}
