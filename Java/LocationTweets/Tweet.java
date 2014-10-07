import java.util.Date;
/**
 * Data object for a tweet. Holds the text,
 * the author and the timestamp.
 * The methods are regular getters/setters.
 * @author Ago
 *
 */
class Tweet implements ITweet {

    private String text;
    private String user;
    private Date timestamp;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}