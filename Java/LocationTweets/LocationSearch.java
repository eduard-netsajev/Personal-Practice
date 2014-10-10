import twitter4j.JSONArray;
import twitter4j.JSONObject;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Location search functionality. Finds query parameters
 * {@link TwitterQuery} for the given location.
 */
class LocationSearch implements ILocationSearch {

    /**
     * Find query parameters for the given location.
     *
     * @param location Location to be searched for
     * @return ITwitterQuery object with set parameters
     */
    @Override
    public ITwitterQuery getQueryFromLocation(String location) {
        ITwitterQuery query = new TwitterQuery();
        try {
            String s;
            StringBuilder response = new StringBuilder();
            URL url = new URL("http://nominatim.openstreetmap.org/search.php?q="
                    + URLEncoder.encode(location, "UTF-8")
                    + "&format=json&addressdetails=1&limit=1");
            Reader.init(url.openStream());
            while (true) {
                s = Reader.nextLine();
                if (s == null) {
                    break;
                } else {
                    response.append(s);
                }
            }

            JSONObject jO = new JSONArray(response.toString()).getJSONObject(0);

            JSONArray boundingBox = jO.getJSONArray("boundingbox");
            LatLon p1 = new LatLon(Double.parseDouble(boundingBox.getString(0)),
                    Double.parseDouble(boundingBox.getString(2)));

            LatLon p2 = new LatLon(Double.parseDouble(boundingBox.getString(1)),
                    Double.parseDouble(boundingBox.getString(3)));

            LatLon midpoint = LatLon.midpoint(p1, p2);
            double radius = LatLon.distance(p1, p2) / 2;
            if (radius < 1) {
                radius = 1.0; // for small objects
            }

            query.setLatitude(midpoint.latitude);
            query.setLongitude(midpoint.longitude);
            query.setLocation(location);
            query.setRadius(radius);

            return query;
        } catch (Exception e) {
            LocationTweets.out.println("Failed to search location " + location);
            return null;
        }
    }
}