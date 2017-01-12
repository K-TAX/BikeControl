package com.jrivera.bikecontrol.fragments;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jrivera.bikecontrol.R;


public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final String ARG_POSITION = "position";

    private int position;
    GoogleMap mMap;
    private int zoom = 17;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return root;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      miUbicacion(googleMap);

    }
    public void  actualizarUbicacionActual(Location location) {
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (location != null){
            databaseReference= FirebaseDatabase.getInstance().getReference();

            double lat = location.getLatitude();
            double lon = location.getLongitude();
            System.out.println(lat+" "+lon);
            databaseReference.child("users").child(user.getUid()).child("positionActual").child("lat").setValue(lat);
            databaseReference.child("users").child(user.getUid()).child("positionActual").child("lon").setValue(lon);

        }
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            actualizarUbicacionActual(location);
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike)).draggable(false));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion(GoogleMap googleMap){
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(false);
        String locationProvider = LocationManager.GPS_PROVIDER;
// Or use LocationManager.GPS_PROVIDER
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
// Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(locationProvider,2000,0,locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        actualizarUbicacionActual(lastKnownLocation);


        //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
        //        new LatLng(location.getLatitude(),location.getLongitude()), 18));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()), zoom));



// Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()))      // Sets the center of the map to Mountain View
                .zoom(zoom)                   // Sets the zoom
                .bearing(120)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}