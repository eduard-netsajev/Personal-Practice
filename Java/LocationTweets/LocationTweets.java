import twitter4j.*;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URL;
import java.util.List;

public class LocationTweets implements ITwitterApplication {

    private static final String TWITTER_CUSTOMER_KEY =
            "zpNDfFQlI8sfAvA0e5dIwypU9";
    private static final String TWITTER_CUSTOMER_SECRET =
            "SvSHa19OT1BfHD95DaQI2eJs8zGMMXh1c3XWkgXqEzLLUzszVC";

    public static void main(String[] args){


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(TWITTER_CUSTOMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CUSTOMER_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();

        OAuth2Token token;
        try {
            token = twitter.getOAuth2Token();
            System.out.println(token.toString());
        } catch (TwitterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(-1);
        }

        String s;
        StringBuilder response = new StringBuilder();
        try {

            URL url = new URL("http://nominatim.openstreetmap" +
                    ".org/search/Tallinn?format=json&addressdetails" +
                    "=1" +
                    "&limit=1");
            Reader.init(url.openStream());
            while (true) {
                s = Reader.nextLine();
                if (s == null) {
                    break;
                } else {
                    response.append(s);
                }
            }

            JSONObject res = new JSONArray(response.toString()).getJSONObject(0);
            System.out.println(res.toString());

            JSONArray boundingBox  = res.getJSONArray("boundingbox");
            //http://www.movable-type.co.uk/scripts/latlong.html
            LatLon p1 = new LatLon(Double.parseDouble(boundingBox.getString(0)),
                    Double.parseDouble(boundingBox.getString(2)));

            LatLon p2 = new LatLon(Double.parseDouble(boundingBox.getString(1)),
                    Double.parseDouble(boundingBox.getString(3)));

            LatLon midpoint = LatLon.midpoint(p1, p2);
            double radius = LatLon.distance(p1, p2) / 3.0;
            System.out.println(radius);

            try {
                Query query = new Query();
                query.setCount(10);
                GeoLocation location = new GeoLocation(midpoint.latitude,
                        midpoint.longitude);
                query.setGeoCode(location, radius, Query.KILOMETERS);
                query.setResultType(Query.ResultType.recent);

                QueryResult result;
                do {
                    result = twitter.search(query);
                    List<Status> tweets = result.getTweets();
                    for (Status tweet : tweets) {
                        try {
                            GeoLocation ths = tweet.getGeoLocation();

                            try {
                                System.out.println(ths.getLatitude());
                                System.out.println(ths.getLongitude());
                            } catch (Exception e) {
                                System.out.println("problem2");
                            }


                        } catch (Exception e) {
                            System.out.println("problem1");
                        }
                        System.out.println(tweet.getCreatedAt().toString());
                        System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    }
                    break; // TODO next page
                } while ((query = result.nextQuery()) != null);
                System.exit(0);
            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
                System.exit(-1);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * Given a command as a String (in interactive mode), this method should
     * parse the input and return a list of IAction instances. Usually one
     * command should create one action, this method gives the opportunity to
     * have combined actions for one command line ("query tallinn search kala").
     * If you don't use combined actions, just return a list with one element in
     * it - the IAction instance to be executed.
     *
     * @param action Command string from interactive mode
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromInput(String action) {
        return null;
    }

    /**
     * Given command line arguments this method parses the arguments and returns
     * a list of IAction instances. As the command line can accept several
     * different actions (for example query, sort and search), this method
     * return a list of all the actions.
     *
     * @param args Command line arguments (from main method)
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromArguments(String[] args) {
        return null;
    }

    /**
     * Given an instance of IAction, it will be executed.
     *
     * @param action
     */
    @Override
    public void executeAction(IAction action) {

    }

    /**
     * Executes all the actions given as a list. The default implementation just
     * iterates over all the actions and calls executeAction.
     *
     * @param actions A list of actions
     */
    @Override
    public void executeActions(List<IAction> actions) {

    }

    /**
     * Executes a location search using location search set with
     * setLocationSearch(). Returns a query object which holds all the values
     * for Twitter search. Note that this method has a default implementation
     * which just executes a method from local location search and returns its
     * return value. Use this default implementation if you don't have caching
     * implemented. If you need caching, you need to override this method.
     *
     * @param location The location which is to be searched for
     * @return Query object which holds all the necessary information about
     * Twitter query
     * @see ITwitterApplication#setLocationSearch(ILocationSearch)
     */
    @Override
    public ITwitterQuery getQueryFromLocation(String location) {
        return null;
    }

    /**
     * Executes a search of tweets on TwitterSearch object which is stored via
     * setTwitterSearch(). Returns a list of received tweets.
     *
     * @param query Query object which holds all the necessary values
     * @return List of ITweet objects received from Twitter search.
     * <code>null</code> if nothing received.
     * @see ITwitterApplication#setTwitterSearch(ITwitterSearch)
     */
    @Override
    public List<? extends ITweet> getTweets(ITwitterQuery query) {
        return null;
    }

    /**
     * Stores location search object which will be used to make queries to
     * location search API.
     *
     * @param locationSearch Implementation of ILocationSearch, which can find
     *                       information about location (city, country etc.).
     */
    @Override
    public void setLocationSearch(ILocationSearch locationSearch) {

    }

    /**
     * Returns currently stored location search object.
     *
     * @return Implementation of ILocationSeach which will be used for location
     * search.
     */
    @Override
    public ILocationSearch getLocationSearch() {
        return null;
    }

    /**
     * Stores Twitter search object which will be used to query tweets from
     * Twitter API.
     *
     * @param twitterSearch Implementation of ITwitterSearch
     */
    @Override
    public void setTwitterSearch(ITwitterSearch twitterSearch) {

    }

    /**
     * Returns currently stored Twitter search object.
     *
     * @return Implementation of ITwitterSearch which will be used for queries.
     */
    @Override
    public ITwitterSearch getTwitterSearch() {
        return null;
    }

    /**
     * Stores cache object which will be used to cache locations in the file.
     *
     * @param cache Implementation of ICache
     */
    @Override
    public void setCache(ICache cache) {

    }

    /**
     * Returns currently stored cache object.
     *
     * @return Implementation of ICache which will be used for location caching.
     */
    @Override
    public ICache getCache() {
        return null;
    }

    /**
     * Stores the latest state of tweets list. You should store your tweets
     * using this method after querying, sorting, searching.
     *
     * @param tweets A list of tweets
     */
    @Override
    public void setTweets(List<? extends ITweet> tweets) {

    }

    /**
     * Get the latest state of tweets list. This method should be used for
     * printing and when applying sorting or searching.
     *
     * @return A list of tweets
     */
    @Override
    public List<? extends ITweet> getTweets() {
        return null;
    }
}


