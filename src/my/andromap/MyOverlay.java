package my.andromap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MyOverlay extends Overlay {
	
	public boolean onTap(GeoPoint p, MapView mapView) {
		Geocoder geocoder = new Geocoder(mapView.getContext(), Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocation(
					p.getLatitudeE6() / 1E6, p.getLongitudeE6() / 1E6, 1);
		
		String message = "";
        if (addresses.size() > 0) 
        {
            for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); i++)
               message += addresses.get(0).getAddressLine(i) + "\n";
        }

		Toast.makeText(mapView.getContext(), message, 3).show();
		return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
