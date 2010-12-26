package org.cloudme.loclist.location;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cloudme.loclist.dao.CheckinDao;
import org.cloudme.loclist.dao.LocationDao;
import org.cloudme.loclist.model.Checkin;
import org.cloudme.loclist.model.Location;

import com.google.inject.Inject;

public class LocationService {
    private static final Log LOG = LogFactory.getLog(LocationService.class);
    @Inject
    private LocationDao locationDao;
    @Inject
    private CheckinDao checkinDao;
    /**
     * The radius in kilometers of tolerance to map a checkin to an existing
     * location within this radius.
     */
    private double radius = 0.05d;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Checks in at the given geo location. If a location with this coordinates
     * or in the distance no longer than the {@link #radius} already exists, the
     * existing location is returned.
     * 
     * @param latitude
     *            The latitude of the geo location.
     * @param longitude
     *            The longitude of the geo location.
     * @return A {@link Checkin} at the nearest existing location or a new
     *         location with the given coordinates.
     */
    public Checkin checkin(float latitude, float longitude) {
        Location location = null;
        double dMin = Double.MAX_VALUE;
        for (Location tmp : locationDao.findAll()) {
            double d = distance(latitude, longitude, tmp.getLatitude(), tmp.getLongitude());
            if (d < radius && d < dMin) {
                location = tmp;
                dMin = d;
            }
        }
        if (location == null) {
            location = new Location(latitude, longitude);
            locationDao.save(location);
        }
        Checkin checkin = new Checkin();
        checkin.setLocationId(location.getId());
        checkin.setTimestamp(System.currentTimeMillis());
        checkin.setLatitude(latitude);
        checkin.setLongitude(longitude);
        checkinDao.save(checkin);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Checkin %d at (%f, %f) location %d",
                    checkin.getId(),
                    checkin.getLongitude(),
                    checkin.getLatitude(),
                    checkin.getLocationId()));
        }
        return checkin;
    }

    /**
     * http://www.movable-type.co.uk/scripts/latlong.html
     * 
     * @param lat1
     *            Latitude of first location.
     * @param lon1
     *            Longitude of first location.
     * @param lat2
     *            Latitude of second location.
     * @param lon2
     *            Longitude of second location.
     * @return The distance between two locations in km.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double angle = Math.acos(Math.sin(lat1)
                * Math.sin(lat2)
                + Math.cos(lat1)
                * Math.cos(lat2)
                * Math.cos(lon1 - lon2));
        return 6371.0f * angle;
    }
}
