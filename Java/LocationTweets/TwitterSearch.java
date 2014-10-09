import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Twitter search functionality. The implementation
 * of this interface should be able to search tweets
 * from Twitter API.
 * @author Ago
 *
 */
class TwitterSearch implements ITwitterSearch {

    Twitter twitter;

    private static final String TWITTER_CUSTOMER_KEY =
            "zpNDfFQlI8sfAvA0e5dIwypU9";
    private static final String TWITTER_CUSTOMER_SECRET =
            "SvSHa19OT1BfHD95DaQI2eJs8zGMMXh1c3XWkgXqEzLLUzszVC";

    TwitterSearch(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(TWITTER_CUSTOMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CUSTOMER_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        try {
            twitter.getOAuth2Token();
        } catch (TwitterException e1) {
            LocationTweets.out.println("Couldn't connect to Twitter");
            System.exit(-1);
        }
    }

    /**
     * Given an object with query parameters send a query to Twitter API, reads
     * out tweet information and returns a list of ITweet objects.
     *
     * @param iQuery Query parameters to be used for search
     * @return A list of ITweet objects
     */
    @Override
    public List<? extends ITweet> getTweets(ITwitterQuery iQuery) {

        List<Tweet> tweets = new ArrayList<Tweet>();

        try {
            Query query = new Query();
            query.setResultType(Query.ResultType.recent);
            GeoLocation location = new GeoLocation(iQuery.getLatitude(),
                    iQuery.getLongitude());
            query.setGeoCode(location, iQuery.getRadius(), Query.KILOMETERS);

            int i = 0;
            QueryResult result;

            search:
            do {
                result = twitter.search(query);
                List<Status> resultTweets = result.getTweets();
                for (Status tweet : resultTweets) {

                    Tweet myTweet = new Tweet();
                    myTweet.setText(tweet.getText());
                    myTweet.setTimestamp(tweet.getCreatedAt());
                    myTweet.setUser(tweet.getUser().getScreenName());
                    tweets.add(myTweet);
                    i++;

                    if (i == iQuery.getCount()) {
                        break search;
                    }
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            LocationTweets.out.println("Failed to search tweets: " + te.getMessage());
            return null;
        }

        return tweets;
    }
}
