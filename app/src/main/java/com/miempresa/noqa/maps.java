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
import android.widget.ImageButton;
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
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class maps extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    // Variables globales
    static boolean result_datos;

    // Botones de sitios
    private ImageButton btnRestaurantes;
    private ImageButton btnCafeterías;
    private ImageButton btnIglesias;
    private ImageButton btnTiendaRopa;
    private ImageButton btnFarmacias;
    private ImageButton btnGimnasios;
    private ImageButton btnCuidadoCabello;
    private ImageButton btnHospitales;
    private ImageButton btnLavanderia;
    private ImageButton btnLicorerias;
    private ImageButton btnHospedajes;

    // Estado de botones
    private boolean BoolRestaurantes;
    private boolean BoolCafeterías;
    private boolean BoolIglesias;
    private boolean BoolTiendaRopa;
    private boolean BoolFarmacias;
    private boolean BoolGimnasios;
    private boolean BoolCuidadoCabello;
    private boolean BoolHospitales;
    private boolean BoolLavanderia;
    private boolean BoolLicorerias;
    private boolean BoolHospedajes;



    // ------------------------------------------------------------
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        // Google Maps
        map = googleMap;

        // Estado de botones
        BoolRestaurantes = false;
        BoolCafeterías = false;
        BoolIglesias = false;
        BoolTiendaRopa = false;
        BoolFarmacias = false;
        BoolGimnasios = false;
        BoolLicorerias = false;
        BoolHospedajes = false;

        // Id de botones
        btnRestaurantes = getView().findViewById(R.id.btnRestaurantes);
        btnCafeterías = getView().findViewById(R.id.btnCafeterías);
        btnIglesias = getView().findViewById(R.id.btnIglesias);
        btnTiendaRopa = getView().findViewById(R.id.btnTiendaRopa);
        btnFarmacias = getView().findViewById(R.id.btnFarmacias);
        btnGimnasios = getView().findViewById(R.id.btnGimnasios);
        btnLicorerias = getView().findViewById(R.id.btnLicorerias);
        btnHospedajes = getView().findViewById(R.id.btnHospedajes);

        // Eventos para cada tipo de sitio
        btnRestaurantes.setOnClickListener(view -> {
            if (BoolRestaurantes == true) {
                map.clear();
                BoolRestaurantes = false;
                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolRestaurantes == false) {
                map.clear();
                Escoger_Ruta("restaurant");
                BoolRestaurantes = true;
                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolCafeterías = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolLicorerias = false;
                BoolHospedajes = false;

                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnCafeterías.setOnClickListener(view -> {
            if (BoolCafeterías == true) {
                map.clear();
                BoolCafeterías = false;
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolCafeterías == false) {
                map.clear();
                Escoger_Ruta("cafe");
                BoolCafeterías = true;
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolLicorerias = false;
                BoolHospedajes = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnIglesias.setOnClickListener(view -> {
            if (BoolIglesias == true) {
                map.clear();
                BoolIglesias = false;
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolIglesias == false) {
                map.clear();
                Escoger_Ruta("church");
                BoolIglesias = true;
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolLicorerias = false;
                BoolHospedajes = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnTiendaRopa.setOnClickListener(view -> {
            if (BoolTiendaRopa == true) {
                map.clear();
                BoolTiendaRopa = false;
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolTiendaRopa == false) {
                map.clear();
                Escoger_Ruta("clothing_store");
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolTiendaRopa = true;
                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolIglesias = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolLicorerias = false;
                BoolHospedajes = false;


                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnFarmacias.setOnClickListener(view -> {
            if (BoolFarmacias == true) {
                map.clear();
                BoolFarmacias = false;
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolFarmacias == false) {
                map.clear();
                Escoger_Ruta("drugstore");
                BoolFarmacias = true;
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolGimnasios = false;
                BoolLicorerias = false;
                BoolHospedajes = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnGimnasios.setOnClickListener(view -> {
            if (BoolGimnasios == true) {
                map.clear();
                BoolGimnasios = false;
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolGimnasios == false) {
                map.clear();
                Escoger_Ruta("gym");
                BoolGimnasios = true;
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolLicorerias = false;
                BoolHospedajes = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnLicorerias.setOnClickListener(view -> {
            if (BoolLicorerias == true) {
                map.clear();
                BoolLicorerias = false;
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolLicorerias == false) {
                map.clear();
                Escoger_Ruta("liquor_store");
                BoolLicorerias = true;
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolHospedajes = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        btnHospedajes.setOnClickListener(view -> {
            if (BoolHospedajes == true) {
                map.clear();
                BoolHospedajes = false;
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
            else if (BoolHospedajes == false) {
                map.clear();
                Escoger_Ruta("lodging");
                BoolHospedajes = true;
                btnHospedajes.setBackgroundResource(R.drawable.rounded_button_sites_enabled);

                BoolRestaurantes = false;
                BoolCafeterías = false;
                BoolIglesias = false;
                BoolTiendaRopa = false;
                BoolFarmacias = false;
                BoolGimnasios = false;
                BoolLicorerias = false;

                btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnIglesias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnTiendaRopa.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnFarmacias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnGimnasios.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
                btnLicorerias.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
            @Override
            public void onMyLocationChange(Location location){
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
            }
        });
    }

    private void Escoger_Ruta(String place_type) {
        int rango = 1000;
        boolean comprobando;
        comprobando = Comprobar_resultados(rango, place_type);
        while (comprobando == true && rango <= 2000) {
            if (comprobando == true) {
                rango = rango + 100;
                comprobando = Comprobar_resultados(rango, place_type);
            }
        }
        result_datos = false;

        // Original
        String urlSitios = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +latitudOrigen+ "," +longitudOrigen
                +"&radius=" + rango +
                "&type=" + place_type +
                "&key=AIzaSyC7U2vYoCP2V2YDncF5PiQnjWaBE0iAi_c";
        RequestQueue queueSitios = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestSitios = new StringRequest(Request.Method.GET, urlSitios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jso = new JSONObject(response);
                    Marcar_sitios(jso);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queueSitios.add(stringRequestSitios);
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
//            Toast.makeText(getActivity(), "Lat,Lng: "+ longitud + "," + latitud,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Comprobar resultados en el radio
    public boolean Comprobar_resultados(int range, String place_type) {
        String urlResultados = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +latitudOrigen+ "," +longitudOrigen
                +"&radius=" + range +
                "&type=" + place_type +
                "&key=AIzaSyC7U2vYoCP2V2YDncF5PiQnjWaBE0iAi_c";
        RequestQueue queueResultados = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestVenida = new StringRequest(Request.Method.GET, urlResultados,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("response: ",""+response);
                if (response.contains("ZERO_RESULTS")) {
                    result_datos = true;
                }
                else {
                    result_datos = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queueResultados.add(stringRequestVenida);
        return result_datos;
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

    @Override
    public void onClick(View view) {

        // Id de botones
//        btnRestaurantes = getView().findViewById(R.id.btnRestaurantes);
//        btnCafeterías = getView().findViewById(R.id.btnCafeterías);
//        btnIglesias = getView().findViewById(R.id.btnIglesias);
//        btnTiendaRopa = getView().findViewById(R.id.btnTiendaRopa);
//        btnFarmacias = getView().findViewById(R.id.btnFarmacias);
//        btnGimnasios = getView().findViewById(R.id.btnGimnasios);
//        btnCuidadoCabello = getView().findViewById(R.id.btnCuidadoCabello);
//        btnHospitales = getView().findViewById(R.id.btnHospitales);
//        btnLavanderia = getView().findViewById(R.id.btnLavanderia);
//        btnLicorerias = getView().findViewById(R.id.btnLicorerias);
//        btnHospedajes = getView().findViewById(R.id.btnHospedajes);




//        switch(view.getId()) {
//            case R.id.btnRestaurantes:
//                if (BoolRestaurantes == true) {
//                    map.clear();
//                    BoolRestaurantes = false;
//                    btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
//                }
//                else if (BoolRestaurantes == false) {
//                    Escoger_Ruta("restaurant");
//                    BoolRestaurantes = true;
//                    btnRestaurantes.setBackgroundResource(R.drawable.rounded_button_sites_enabled);
//                }
//                break;
//            case R.id.btnCafeterías:
//                if (BoolRestaurantes == true) {
//                    map.clear();
//                    BoolRestaurantes = false;
//                    btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_disabled);
//                }
//                else if (BoolRestaurantes == false) {
//                    Escoger_Ruta("cafe");
//                    BoolRestaurantes = true;
//                    btnCafeterías.setBackgroundResource(R.drawable.rounded_button_sites_enabled);
//                }
//                break;
//        }
    }

    public interface OnFragmentInteractionListener {
    }
}