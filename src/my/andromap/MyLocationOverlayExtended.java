package my.andromap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyLocationOverlayExtended extends MyLocationOverlay {

	Context context;
	MapView mapView;
	
	public MyLocationOverlayExtended(Context context, MapView mapView) {
		super(context, mapView);
		this.context = context;
		this.mapView = mapView;
	}
	
	protected  boolean 	dispatchTap() {
		GeoPoint p = this.getMyLocation();
		String message = "My location is: " + 
						 Location.convert(p.getLongitudeE6() / 1E6, Location.FORMAT_DEGREES) + ", " +
		 				 Location.convert(p.getLatitudeE6() / 1E6, Location.FORMAT_DEGREES);
		Toast.makeText(this.context, message, 3).show();
		return true;
	}
}
