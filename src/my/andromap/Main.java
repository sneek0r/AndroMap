package my.andromap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class Main extends MapActivity implements LocationListener {
	
	public LocationManager manager;
	public MapView mapView;
	
	public boolean UPDATE_LOCATION = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapView = (MapView)findViewById(R.id.mapview);
        
        // add zoom support (buttons)
        mapView.setBuiltInZoomControls(true);
        // show traffic
        mapView.setTraffic(true);
        mapView.preLoad();
    }
    

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	public void onLocationChanged(Location loc) {
		GeoPoint point = new GeoPoint(
				(int)(loc.getLatitude() * 1000000), 
				(int)(loc.getLongitude() * 1000000));
		
		mapView.getController().animateTo(point);
	}


	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onDestroy() {
		this.locationUpdates(null);
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_follow_me:
	    	return this.locationUpdates(LocationManager.GPS_PROVIDER);
	    case R.id.menu_map_mode:
	    	break;
	    default:
	    	break;
	    }
	    return false;
	}
	
	public boolean locationUpdates(String provider) {
		
		try {
			CharSequence message;
			if (!UPDATE_LOCATION && provider != null) {
				this.manager.requestLocationUpdates(provider, 10000, 10, this);
				message = "Location updates requested!";
			} else { 
				this.manager.removeUpdates(this);
				message = "Location updates removed!";
			}
			Toast.makeText(getApplicationContext(), message, 3).show();
			this.UPDATE_LOCATION = !this.UPDATE_LOCATION;
        	return true;
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        	return false;
        }
	}
}