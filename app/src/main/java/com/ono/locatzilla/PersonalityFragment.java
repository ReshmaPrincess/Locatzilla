package com.ono.locatzilla;

import android.app.ProgressDialog;
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
import com.ono.locatzilla.Models.PersonalityModel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
        return view;
    }

    Button btnPersonality;
    TextView tvPersonality;

    private void initComponents(View view) {
        btnPersonality = view.findViewById(R.id.btnSearchPersonality);
        tvPersonality = view.findViewById(R.id.tvPersonalityTraits);
    }

    private void initListeners() {
        btnPersonality.setOnClickListener(this);
    }


    private RequestQueue mRequestQueue;

    private void findYourPersonality() {
        String url = "https://randomuser.me/api/";
        ProgressDialog pDialog = new ProgressDialog(requireContext());
        pDialog.setMessage("Loading...PLease wait");
        pDialog.show();

        mRequestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            Gson gson = new Gson();
            PersonalityModel model = gson.fromJson(response.toString(), PersonalityModel.class);
            Log.i(TAG, "Response :" + model.toString());

            pDialog.dismiss();
        }, error -> {
            if (error == null || error.networkResponse == null) {
                return;
            }

            String body = "";
            //get status code here
            final String statusCode = String.valueOf(error.networkResponse.statusCode);
            //get response body and parse with appropriate encoding
            try {
                body = new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // exception
            }

            Log.i(TAG, "Error :" + body);


            pDialog.dismiss();
        }) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("X-RapidAPI-Key", "27eb204886msh7788a5c81dac783p10b70bjsn81a33d56dc0b");
//                headers.put("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com");
                return headers;
            }
        };

        mRequestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchPersonality) {
            findYourPersonality();
        }
    }
}