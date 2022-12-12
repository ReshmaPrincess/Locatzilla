package com.w9530581.letsplan;

import static com.w9530581.letsplan.TempDatabaseController.PERSONALITY;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.w9530581.letsplan.Models.Result;

import java.util.ArrayList;

public class SavedPersonalitiesActivity extends AppCompatActivity {

    SavedActivitiesAdapter savedActivitiesAdapter;
    RecyclerView rvSavedPersonalities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_personalities);
        rvSavedPersonalities = findViewById(R.id.rvSavedPersonalities);
        setAdapter();
    }

    private void setAdapter() {
        ArrayList<Result> personalities = (ArrayList<Result>) TempDatabaseController.getValue(PERSONALITY);
        if (personalities == null || personalities.isEmpty()) {
            personalities = new ArrayList<>();
        }
        savedActivitiesAdapter = new SavedActivitiesAdapter(this, personalities);
        rvSavedPersonalities.setAdapter(savedActivitiesAdapter);
    }
}