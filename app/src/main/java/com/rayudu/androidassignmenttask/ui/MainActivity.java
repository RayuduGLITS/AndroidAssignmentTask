package com.rayudu.androidassignmenttask.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rayudu.androidassignmenttask.R;
import com.rayudu.androidassignmenttask.adapters.UserRvAdapter;
import com.rayudu.androidassignmenttask.databinding.ActivityMainBinding;
import com.rayudu.androidassignmenttask.db.AppDatabase;
import com.rayudu.androidassignmenttask.model.UserModel;
import com.rayudu.androidassignmenttask.utils.AppController;
import com.rayudu.androidassignmenttask.utils.BitmapUtils;
import com.rayudu.androidassignmenttask.utils.Constants;
import com.rayudu.androidassignmenttask.utils.OnCardClick;
import com.rayudu.androidassignmenttask.viewmodel.AssignmentViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AssignmentViewModel viewmodel;
    ArrayList<UserModel> userModelList;

    private UserRvAdapter userRvAdapter;

    private FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    List<Address> addresses = null;
    String address = null;
    Geocoder geocoder;



    private int currentPosition = -1;  // Holds the clicked item's position

    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        userModelList = new ArrayList<>();
        viewmodel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        requestPermissionLauncher.launch(permissions);

        fetchUserData();
    }


    private void fetchUserData() {

        viewmodel.getUsersData().observe(MainActivity.this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {

                Log.d("TAG","user data "+jsonObject);

                if (jsonObject != null){

                    userModelList.clear();

                    JsonArray jsonArray = jsonObject.getAsJsonArray("data");

                    if (!jsonArray.isEmpty()){

                        for (int i=0;i<jsonArray.size();i++){
                            UserModel userModel = new Gson().fromJson(jsonArray.get(i).toString(), new TypeToken<UserModel>() {
                            }.getType());
                            userModelList.add(userModel);
                            Log.d("TAG","user data list "+userModel);
                        }
                        new Thread(() -> {
                            AppDatabase.getInstance(MainActivity.this).userDao().insertUserList(userModelList);
                        }).start();

                        userRvAdapter = new UserRvAdapter(MainActivity.this, userModelList, new OnCardClick() {
                            @Override
                            public void OnItemClick(int position) {
                                currentPosition = position;
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryLauncher.launch(intent);

                            }
                        });
                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        binding.rvUserList.setLayoutManager(manager);
                        binding.rvUserList.setAdapter(userRvAdapter);
                        userRvAdapter.notifyDataSetChanged();

                    }

                }


            }
        });

    }


    private void getCurrentLatLongCoordinates() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLocationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .build();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            try {
                                binding.tvLatitude.setText("User Latitude : "+location.getLatitude());
                                binding.tvLongitude.setText("User Longitude : "+location.getLatitude());
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                if (addresses != null && !addresses.isEmpty()){
                                    address = addresses.get(0).getAddressLine(0).replace("\"","").replace("'","");
                                    binding.tvAddress.setText("User Address : "+address);
                                }
//                                Constant.custom_snackbar(MainActivity.this, findViewById(android.R.id.content), "location : Lat " + currentLat + " Lang " + currentLang,false);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Constants.custom_snackbar(MainActivity.this, findViewById(android.R.id.content), "connection is too low check once",false);
                            }
                        } else {
                            Constants.custom_snackbar(MainActivity.this, findViewById(android.R.id.content), "Unable to location",false);
                        }
                    }
                } else {
                    Constants.custom_snackbar(MainActivity.this, findViewById(android.R.id.content), "Unable to location",false);
                }

            }
        };

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }


    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted->{
                if (Boolean.TRUE.equals(isGranted.get(permissions[0]))){
                    getCurrentLatLongCoordinates();
                }else {
                    Constants.custom_snackbar(this, findViewById(android.R.id.content),"Must Enable location permissions",true);
                }

            });


    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImageUri = result.getData().getData();

            // Save the selected image to the Room database
            if (selectedImageUri != null) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                    int id = userModelList.get(currentPosition).getId();
                    UserModel user = userModelList.get(currentPosition);
                    user.setId(id);
                    user.setAvatar(BitmapUtils.bitmapToString(bitmap));
                    user.setImageUploaded(true);

                    new Thread(() -> {
                        AppDatabase.getInstance(MainActivity.this).userDao().updateUser(user);
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                // Notify the adapter
                userRvAdapter.notifyItemChanged(currentPosition);
            }
        }
    });


}