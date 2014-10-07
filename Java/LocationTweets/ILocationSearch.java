

/**
 * Location search functionality. The implementation
 * of this interface should be able to find query parameters
 * (see ITwitterQuery) for the given location.
 * @author Ago
 *
 */
public interface ILocationSearch {
	
	/**
	 * Find query parameters for the given location.
	 * @param location Location to be searched for
	 * @return Query parameters
	 */
	public ITwitterQuery getQueryFromLocation(String location);
}
