import java.util.Date;

/**
 * Data object for a tweet. Holds the text,
 * the author and the timestamp.
 */
class Tweet implements ITweet {

    /**
     * Text of the tweet.
     */
    private String text;

    /**
     * Author's username.
     */
    private String user;

    /**
     * Date when the tweet was posted.
     */
    private Date timestamp;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String tweetText) {
        text = tweetText;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String author) {
        user = author;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date tweetTimestamp) {
        timestamp = tweetTimestamp;
    }

    @Override
    public String toString() {
        return user + "\n" + timestamp.toString() + "\n" + text + "\n";
    }
}