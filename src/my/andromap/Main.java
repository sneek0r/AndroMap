package my.andromap;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class Main extends MapActivity implements LocationListener {
	
	public MapView mapView;
	
	public boolean UPDATE_LOCATION = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView)findViewById(R.id.mapview);
        
        // add zoom support (buttons)
        mapView.setBuiltInZoomControls(true);
        // show traffic
        mapView.setTraffic(true);
        mapView.preLoad();
        MyOverlay adrOv = new MyOverlay();
        mapView.getOverlays().add(adrOv);
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
	    	final CharSequence[] items = {"Traffic", "Satellite", "Street"};

	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Select a map view");
	    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	switch (item) {
					case 0:
						mapView = (MapView)findViewById(R.id.mapview);
						mapView.setTraffic(true);
						mapView.setSatellite(false);
						mapView.setStreetView(false);
						mapView.postInvalidate();
						mapView.preLoad();
						break;
					case 1:
						mapView = (MapView)findViewById(R.id.mapview);
						mapView.setSatellite(true);
						mapView.setTraffic(false);
						mapView.setStreetView(false);
						mapView.postInvalidate();
						mapView.preLoad();
						break;
					case 2:
						mapView = (MapView)findViewById(R.id.mapview);
						mapView.setStreetView(true);
						mapView.setTraffic(false);
						mapView.setSatellite(false);
						mapView.postInvalidate();
						mapView.preLoad();
						break;
					default:
						break;
					}
	    	    }
	    	});
	    	AlertDialog alert = builder.create();
	    	alert.show();
	    	return true;
	    }
	    return false;
	}
	
	public boolean locationUpdates(String provider) {
		
		try {
			LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
			CharSequence message;
			if (!UPDATE_LOCATION && provider != null) {
				// ENABLE
				// manually draw your location
				manager.requestLocationUpdates(provider, 10000, 10, this);
				
				// MyLocationOverlay example
				MyLocationOverlayExtended myOverlay = new MyLocationOverlayExtended(getApplicationContext(), mapView);
		        myOverlay.enableMyLocation();
		        myOverlay.enableCompass();
		        mapView.getOverlays().add(myOverlay);;
		        
		        message = "Location updates requested!";
			} else { 
				// DISABLE
				manager.removeUpdates(this);
				
				// MyOverlay example
				for (Overlay ov : mapView.getOverlays()) {
					if (ov instanceof MyLocationOverlayExtended) {
						((MyLocationOverlay) ov).disableMyLocation();
						((MyLocationOverlay) ov).disableCompass();
					}
				}
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