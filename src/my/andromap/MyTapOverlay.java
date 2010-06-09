package my.andromap;

import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MyTapOverlay extends Overlay {

	public boolean onTap(GeoPoint p, MapView mapView) {
		String message = Location.convert(p.getLongitudeE6() / 1E6, Location.FORMAT_MINUTES) + ", " +
						 Location.convert(p.getLatitudeE6() / 1E6, Location.FORMAT_MINUTES);
		Toast.makeText(mapView.getContext(), message, 3).show();
		return false;
	}
}
