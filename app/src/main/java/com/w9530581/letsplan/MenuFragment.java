package com.w9530581.letsplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(view);
        initListeners();
        return view;
    }

    private Button btnPersonality, btnRecollection, btnPlaces, btnSharePlan;

    private void initComponents(View view) {
        btnPersonality = view.findViewById(R.id.btnPresonality);
        btnRecollection = view.findViewById(R.id.btnRecollection);
        btnPlaces = view.findViewById(R.id.btnMaps);
        btnSharePlan = view.findViewById(R.id.btnSharePlan);
    }

    private void initListeners() {
        btnPersonality.setOnClickListener(this);
        btnRecollection.setOnClickListener(this);
        btnPlaces.setOnClickListener(this);
        btnSharePlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPresonality) {
            navigateFragment(ActionType.MOVIE);
        } else if (v.getId() == R.id.btnRecollection) {
            navigateFragment(ActionType.CAMERA);
        } else if (v.getId() == R.id.btnMaps) {
//            navigateFragment(ActionType.MOVIE);
        } else if (v.getId() == R.id.btnSharePlan) {
            navigateFragment(ActionType.PLAN);
        }
    }

    private void navigateFragment(ActionType actionType) {
        NavController navController = NavHostFragment.findNavController(this);
        if (actionType == ActionType.MOVIE) {
            navController.navigate(
                    R.id.action_menuFragment_to_personalityFragment,
                    null,
                    new NavOptions.Builder()
                            .setEnterAnim(android.R.animator.fade_in)
                            .setExitAnim(android.R.animator.fade_out)
                            .build()
            );
        } else if (actionType == ActionType.CAMERA) {
            navController.navigate(
                    R.id.action_menuFragment_to_memoriesFragment,
                    null,
                    new NavOptions.Builder()
                            .setEnterAnim(android.R.animator.fade_in)
                            .setExitAnim(android.R.animator.fade_out)
                            .build()
            );
        } else if (actionType == ActionType.PLAN) {
            navController.navigate(
                    R.id.action_menuFragment_to_sharePlanFragment,
                    null,
                    new NavOptions.Builder()
                            .setEnterAnim(android.R.animator.fade_in)
                            .setExitAnim(android.R.animator.fade_out)
                            .build()
            );
        }
    }
}