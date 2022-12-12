package com.w9530581.letsplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.w9530581.letsplan.Models.PersonalityModel;
import com.w9530581.letsplan.Models.Result;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalityFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "PersonalityFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonalityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalityFragment newInstance(String param1, String param2) {
        PersonalityFragment fragment = new PersonalityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personality, container, false);
        initComponents(view);
        initListeners();
        findYourPersonality();
        return view;
    }

    Button btnPersonality, btnSaverPersonality, btnSharePersonality;
    TextView tvPersonName, tvPersonGender, tvPersonEmail, tvPersonDob, tvPersonPhone, tvPersonLocation;

    private void initComponents(View view) {
        tvPersonName = view.findViewById(R.id.tvPersonName);
        tvPersonGender = view.findViewById(R.id.tvPersonGender);
        tvPersonEmail = view.findViewById(R.id.tvPersonEmail);
        tvPersonDob = view.findViewById(R.id.tvPersonDob);
        tvPersonPhone = view.findViewById(R.id.tvPersonPhone);
        tvPersonLocation = view.findViewById(R.id.tvPersonLocation);

        btnPersonality = view.findViewById(R.id.btnSearchPersonality);
        btnSaverPersonality = view.findViewById(R.id.btnSaverPersonality);
        btnSharePersonality = view.findViewById(R.id.btnSharePersonality);
    }

    private void initListeners() {
        btnPersonality.setOnClickListener(this);
        btnSaverPersonality.setOnClickListener(this);
        btnSharePersonality.setOnClickListener(this);
    }

    private void displayPersonalityTraits(Result personality) {
        tvPersonName.setText(new StringBuilder().append(personality.getName().getFirst()).append(" ").append(personality.getName().getLast()));
        tvPersonGender.setText(personality.getGender());
        tvPersonEmail.setText(personality.getEmail());
        tvPersonDob.setText(personality.getDob().getDate());
        tvPersonPhone.setText(personality.getPhone());
        tvPersonLocation.setText(new StringBuilder().append(personality.getLocation().getCity()).append(" ").append(personality.getLocation().getState()).append(" ").append(personality.getLocation().getCountry()));
    }


    private RequestQueue mRequestQueue;
    private Result result;

    private void findYourPersonality() {
        String url = "https://randomuser.me/api/";
        ProgressDialog pDialog = new ProgressDialog(requireContext());
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        mRequestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            Gson gson = new Gson();
            PersonalityModel model = gson.fromJson(response.toString(), PersonalityModel.class);
            result = model.getResults().get(0);
            displayPersonalityTraits(result);
            Log.i(TAG, "Response :" + response.toString());

            pDialog.dismiss();
        }, error -> {
            if (error == null || error.networkResponse == null) {
                return;
            }

            String body = "";
            final String statusCode = String.valueOf(error.networkResponse.statusCode);
            //get response body and parse with appropriate encoding
            try {
                body = new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // exception
            }

            Log.i(TAG, "Error :" + body);


            pDialog.dismiss();
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchPersonality) {
            findYourPersonality();
        } else if (v.getId() == R.id.btnSaverPersonality) {

        } else if (v.getId() == R.id.btnSharePersonality) {
            sharePersonality();
        }
    }

    private void sharePersonality() {
        StringBuilder traits = new StringBuilder().append("Name : \t").append(result.getName().getFirst()).append(" ").append(result.getName().getLast()).append("\n")
                .append("Gender : \t").append(result.getGender()).append("\n")
                .append("Email : \t").append(result.getEmail()).append("\n")
                .append("DOB : \t").append(result.getDob().getDate()).append("\n")
                .append("Phone : \t").append(result.getPhone()).append("\n")
                .append("Location : \t").append(result.getLocation().getCity()).append(" ").append(result.getLocation().getState()).append(" ").append(result.getLocation().getCountry());

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, traits.toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Personality Traits");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }
}