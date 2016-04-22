package shreyas.com.ifactorapp;

import java.io.Serializable;

/**
 * Created by shreyas on 4/22/2016.
 */
public class Post implements Serializable{

    private int userId;
    private int postId;
    private String title;
    private String body;

    public Post(int postId, int userId, String title, String body){
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
