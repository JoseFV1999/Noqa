package com.miempresa.noqa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class maps extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String ruta_recibida;
    private String urlIda;
    private String urlVuelta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ruta_recibida = String.valueOf(getActivity().getIntent().getStringExtra("ruta_escogida"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    // Implementando Rutas
    GoogleMap map;
    Boolean actualPosition = true;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
            @Override
            public void onMyLocationChange(Location location){

                if (actualPosition){
                    latitudOrigen = location.getLatitude();
                    longitudOrigen = location.getLongitude();
                    actualPosition = false;

                    LatLng miPosicion = new LatLng(latitudOrigen, longitudOrigen);

                    map.addMarker(new MarkerOptions().position(miPosicion).title("USTED ESTA AQUI"));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitudOrigen, longitudOrigen))
                            .zoom(12)
                            .bearing(30)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    String urlRestaurants = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                            +latitudOrigen+ "," +longitudOrigen
                            +"&radius=1000" +
                            "&type=restaurant" +
                            "&key=AIzaSyC7U2vYoCP2V2YDncF5PiQnjWaBE0iAi_c";

                    RequestQueue queueRestaurantes = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequestVenida = new StringRequest(Request.Method.GET, urlRestaurants, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                jso = new JSONObject(response);
                                Marcar_sitios(jso);
//                                Log.i("jsonRuta: ", ""+response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queueRestaurantes.add(stringRequestVenida);
                }
            }
        });
    }

    private void Marcar_sitios(JSONObject jso) {

        JSONArray jResults;
        JSONObject jGeometry;
        JSONObject jLocation;
        String jName = "";
        String latitud = null, longitud = null;
        
        try {
            jResults = jso.getJSONArray("results");
            for (int i=0; i<jResults.length();i++){
                jName = ((JSONObject) jResults.get(i)).getString("name");
                jGeometry = ((JSONObject) jResults.get(i)).getJSONObject("geometry");
                jLocation = jGeometry.getJSONObject("location");

                latitud = jLocation.getString("lat");
                longitud = jLocation.getString("lng");

                map.addMarker(new MarkerOptions().position(
                        new LatLng(
                                Double.parseDouble(latitud),
                                Double.parseDouble(longitud)
                        )
                    ).title(jName)
                );
            }
            Toast.makeText(getActivity(), "Lat,Lng: "+ longitud + "," + latitud,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    public interface OnFragmentInteractionListener {
    }
}