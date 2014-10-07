import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Location search functionality. The implementation
 * of this interface should be able to find query parameters
 * (see ITwitterQuery) for the given location.
 * @author Ago
 *
 */
class LocationSearch implements ILocationSearch {

    /**
     * Find query parameters for the given location.
     *
     * @param location Location to be searched for
     * @return Query parameters
     */
    @Override
    public ITwitterQuery getQueryFromLocation(String location) {
        ITwitterQuery query = new TwitterQuery();

        try {
            String s;
            StringBuilder response = new StringBuilder();
            URLEncoder.encode(location, "UTF-8");
            URL url = new URL("http://nominatim.openstreetmap.org/search/" +
                    URLEncoder.encode(location, "UTF-8") +
                    "?format=json&addressdetails=1&limit=1");
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
            // debugging purposes only System.out.println(res.toString());

            JSONArray boundingBox = res.getJSONArray("boundingbox");
            LatLon p1 = new LatLon(Double.parseDouble(boundingBox.getString(0)),
                    Double.parseDouble(boundingBox.getString(2)));

            LatLon p2 = new LatLon(Double.parseDouble(boundingBox.getString(1)),
                    Double.parseDouble(boundingBox.getString(3)));

            LatLon midpoint = LatLon.midpoint(p1, p2);
            double radius = LatLon.distance(p1, p2) / 2.5;
            // debugging purposes only System.out.println(radius);

            query.setLatitude(midpoint.latitude);
            query.setLongitude(midpoint.longitude);
            query.setLocation(location);
            query.setRadius(radius);

            return query;
        } catch (MalformedURLException e) {
            e.printStackTrace(System.out);
            return null;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
