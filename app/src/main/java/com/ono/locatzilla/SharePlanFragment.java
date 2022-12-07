package com.ono.locatzilla;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePlanFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SharePlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharePlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SharePlanFragment newInstance(String param1, String param2) {
        SharePlanFragment fragment = new SharePlanFragment();
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

    private EditText etFrom, etTo, etTitle, etDetail;
    private Button btnShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_plan, container, false);
        context = requireContext();
        initComponents(view);
        initListeners();

        return view;
    }

    private void initComponents(View view) {
        etFrom = view.findViewById(R.id.etFrom);
        etTo = view.findViewById(R.id.etTo);
        etTitle = view.findViewById(R.id.etTitle);
        etDetail = view.findViewById(R.id.etDetail);
        btnShare = view.findViewById(R.id.btnShare);
    }

    private void initListeners() {
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnShare) {
            requestMessagePermissions();
        }
    }

    private void requestMessagePermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.SEND_SMS)) {
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String number = etTo.getText().toString().trim();
                    StringBuilder message = new StringBuilder().append("Title : \n").append(etTitle.getText().toString().trim()).append("\nMessage : \n").append(etDetail.getText().toString().trim());

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message.toString(), null, null);
                    Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}