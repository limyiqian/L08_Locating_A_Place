package sg.edu.rp.c346.id19020125.l08_locating_a_place;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn1, btn2, btn3;
    private GoogleMap map;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        List<String> locations = new ArrayList<String>();
        locations.add("North");
        locations.add("East");
        locations.add("Central");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        spinner.setAdapter(adapter);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);
                ui.setMapToolbarEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if(permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
                else {
                    Log.e("Gmap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }

                LatLng poi_Singapore = new LatLng(1.3521, 103.8198);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore, 11));

                LatLng poi_North = new LatLng(1.44920, 103.81601);
                Marker northMarker = map.addMarker(new MarkerOptions().position(poi_North).title("North - HQ").snippet("Block 333, Admiralty Ave 3, 765654\nOperating hours: 10am-5pm\nTel: 65433456").icon(BitmapDescriptorFactory.fromResource(R.drawable.star)));

                LatLng poi_East = new LatLng(1.35459, 103.93373);
                Marker eastMarker = map.addMarker(new MarkerOptions().position(poi_East).title("East").snippet("Block 555, Tampines Ave 3, 287788\nOperating hours: 9am-5pm\nTel:66776677").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                LatLng poi_central = new LatLng(1.31040, 103.84117);
                Marker centralMarker = map.addMarker(new MarkerOptions().position(poi_central).title("Central").snippet("Block 3A, Orchard Ave 3, 134542\nOperating hours: 11am-8pm\nTel:67788652").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(MainActivity.this,marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null) {
                    LatLng poi_North = new LatLng(1.44920, 103.81601);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,17));
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null){
                    LatLng poi_central = new LatLng(1.31040, 103.84117);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,17));
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null) {
                    LatLng poi_East = new LatLng(1.35459, 103.93373);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,17));
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String location = parent.getItemAtPosition(position).toString();
//        Toast.makeText(MainActivity.this,location, Toast.LENGTH_SHORT).show();
        if(map != null) {
            if(location == "North"){
                LatLng poi_North = new LatLng(1.44920, 103.81601);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,17));
            }
            else if(location == "East") {
                LatLng poi_East = new LatLng(1.35459, 103.93373);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,17));
            }
            else if(location == "Central") {
                LatLng poi_central = new LatLng(1.31040, 103.84117);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,17));
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}