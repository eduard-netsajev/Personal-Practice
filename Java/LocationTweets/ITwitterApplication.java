

import java.util.List;

/**
 * The core interface for your application.
 * @author Ago
 *
 */
public interface ITwitterApplication {
	/**
	 * Given a command as a String (in interactive mode), 
	 * this method should parse the input and return
	 * a list of IAction instances.
	 * Usually one command should create one action, this 
	 * method gives the opportunity to have combined actions
	 * for one command line ("query tallinn search kala").
	 * If you don't use combined actions, just return a list
	 * with one element in it - the IAction instance to be 
	 * executed. 
	 * @param action Command string from interactive mode
	 * @return List of actions to be executed
	 */
	public List<IAction> getActionsFromInput(String action);
	
	/**
	 * Given command line arguments this method parses
	 * the arguments and returns a list of IAction instances.
	 * As the command line can accept several different actions
	 * (for example query, sort and search), this method
	 * return a list of all the actions.
	 * @param args Command line arguments (from main method)
	 * @return List of actions to be executed
	 */
	public List<IAction> getActionsFromArguments(String[] args);
	
	/**
	 * Given an instance of IAction, it will be executed.
	 * @param action
	 */
	public void executeAction(IAction action);
	
	/**
	 * Executes all the actions given as a list.
	 * The default implementation just iterates over
	 * all the actions and calls executeAction.
	 * @param actions A list of actions
	 */
	default public void executeActions(List<IAction> actions) {
		for (IAction action : actions) {
			executeAction(action);
		}
	}
	
	/**
	 * Executes a location search using location search set with
	 * setLocationSearch().
	 * Returns a query object which holds all the values for Twitter search.
	 * Note that this method has a default implementation which
	 * just executes a method from local location search and 
	 * returns its return value.
	 * Use this default implementation if you don't have caching implemented.
	 * If you need caching, you need to override this method.
	 * @param location The location which is to be searched for
	 * @return Query object which holds all the necessary information
	 * about Twitter query
	 * @see ITwitterApplication#setLocationSearch(ILocationSearch)
	 */
	default public ITwitterQuery getQueryFromLocation(String location) {
		ILocationSearch locationSearch = getLocationSearch();
		return locationSearch.getQueryFromLocation(location);
	}
	
	/**
	 * Executes a search of tweets on TwitterSearch object which
	 * is stored via setTwitterSearch(). 
	 * Returns a list of received tweets. 
	 * @param query Query object which holds all the necessary values
	 * @return List of ITweet objects received from Twitter search.
	 * <code>null</code> if nothing received.
	 * @see ITwitterApplication#setTwitterSearch(ITwitterSearch)
	 */
	default public List<? extends ITweet> getTweets(ITwitterQuery query) {
		ITwitterSearch twitterSearch = getTwitterSearch();
		return twitterSearch.getTweets(query);
	}
	
	/**
	 * Stores location search object which will
	 * be used to make queries to location search API.
	 * @param locationSearch Implementation of ILocationSearch, which
	 * can find information about location (city, country etc.).
	 */
	public void setLocationSearch(ILocationSearch locationSearch);
	
	/**
	 * Returns currently stored location search object.
	 * @return Implementation of ILocationSeach
	 * which will be used for location search.
	 */
	public ILocationSearch getLocationSearch();
	
	/**
	 * Stores Twitter search object which will be used
	 * to query tweets from Twitter API.
	 * @param twitterSearch Implementation of ITwitterSearch
	 */
	public void setTwitterSearch(ITwitterSearch twitterSearch);
	
	/**
	 * Returns currently stored Twitter search object.
	 * @return Implementation of ITwitterSearch
	 * which will be used for queries.
	 */
	public ITwitterSearch getTwitterSearch();
	
	/**
	 * Stores cache object which will be used
	 * to cache locations in the file.
	 * @param cache Implementation of ICache
	 */
	public void setCache(ICache cache);
	
	/**
	 * Returns currently stored cache object.
	 * @return Implementation of ICache
	 * which will be used for location caching.
	 */
	
	public ICache getCache();
	
	/**
	 * Stores the latest state of tweets list.
	 * You should store your tweets using this
	 * method after querying, sorting, searching.
	 * @param tweets A list of tweets
	 */
	public void setTweets(List<? extends ITweet> tweets);
	
	/**
	 * Get the latest state of tweets list.
	 * This method should be used for printing
	 * and when applying sorting or searching.
	 * @return A list of tweets
	 */
	public List<? extends ITweet> getTweets();
	
}
