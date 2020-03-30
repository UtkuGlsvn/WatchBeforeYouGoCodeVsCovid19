package com.example.watchbeforeyougo.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.watchbeforeyougo.R;
import com.example.watchbeforeyougo.utils.GetNearbyPlacesData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

/*
 *Created by utkuglsvn
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private Marker currentLocationmMarker;
    String urlList[]={"http://109.168.45.205:84/mjpg/video.mjpg#.XoDYCXZu2F0.link","http://82.64.237.163:8083/mjpg/video.mjpg#.XoCNBhmigFk.link","http://109.190.32.217:81/axis-cgi/mjpg/video.cgi?camera=&resolution=640x480#.XoCID920xz4.link","http://212.154.245.179/jpg/image.jpg?COUNTER#.XoDxWd2_PH4.link","http://80.11.89.188:82/mjpg/video.mjpg#.XoDxr96uKS8.link"};
    public static FragmentMap newInstance() {
        FragmentMap fragment = new FragmentMap();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        return view;

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    Random r=new Random();


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkes(47.36667,8.55);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(getContext())
                        .setTitle("Info")
                        .setMessage("Do you want to watch the market camera\n" )
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int a=r.nextInt(4);
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlList[a]));
                                startActivity(browserIntent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                return false;
//            }
//        });

    }

    private void addMarkes(Double lat , double lng) {

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
        Object dataTransfer[] = new Object[2];

        String url = getUrl(47.36667, 8.55, "market");
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        getNearbyPlacesData.execute(dataTransfer);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.36667, 8.55), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(47.36667, 8.55))      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(47.36667, 8.55));
        markerOptions.title("market");
        markerOptions.icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_market_svg));
        currentLocationmMarker = mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+10000);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE");

        return googlePlaceUrl.toString();
    }

}
