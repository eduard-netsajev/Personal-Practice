import java.util.*;

/**
 * Twitter search functionality. The implementation
 * of this interface should be able to search tweets
 * from Twitter API.
 * @author Ago
 *
 */
class TwitterSearch implements ITwitterSearch {
    /**
     * Given an object with query parameters send a query to Twitter API, reads
     * out tweet information and returns a list of ITweet objects.
     *
     * @param query Query parameters to be used for search
     * @return A list of ITweet objects
     */
    @Override
    public List<? extends ITweet> getTweets(ITwitterQuery query) {

        List<Tweet> tweets = new ArrayList<Tweet>();


        return tweets;
    }
}
