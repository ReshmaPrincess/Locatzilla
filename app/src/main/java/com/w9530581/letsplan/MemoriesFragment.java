package com.w9530581.letsplan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MemoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoriesFragment newInstance(String param1, String param2) {
        MemoriesFragment fragment = new MemoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<DtoMemory> memoryList = new ArrayList<>();
    private MemoriesAdapter memoriesAdapter;
    private Handler handler = new Handler();
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        executor.execute(() -> {
            memoryList = (ArrayList<DtoMemory>) DatabaseEngine.getInstance().getDBInstance().memoriesDao().getAllImages();
            if (memoryList == null || memoryList.isEmpty()) {
                memoryList = new ArrayList<>();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memories, container, false);
        initComponents(view);
        initListeners();
        initAdapter();
        return view;
    }

    private void initComponents(View view) {
        recyclerView = view.findViewById(R.id.rvMemories);
        floatingActionButton = view.findViewById(R.id.fbCamera);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {
        floatingActionButton.setOnClickListener(v -> cameraPermissions());
    }

    private void initAdapter() {
        memoriesAdapter = new MemoriesAdapter(memoryList);
        recyclerView.setAdapter(memoriesAdapter);
    }

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String photo;
    Bitmap theImage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void cameraPermissions() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            theImage = (Bitmap) data.getExtras().get("data");
            photo = getEncodedString(theImage);
            DtoMemory dtoMemory = new DtoMemory(0, String.valueOf(System.currentTimeMillis()), photo);
            executor.execute(() -> {
                DatabaseEngine.getInstance().getDBInstance().memoriesDao().addNewMemory(dtoMemory);
            });
            memoryList.add(dtoMemory);
            memoriesAdapter.notifyItemInserted(memoryList.size() - 1);
        }
    }

    private String getEncodedString(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

       /* or use below if you want 32 bit images
        bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }

}