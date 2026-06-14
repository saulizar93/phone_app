package com.example.rottenbanana;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch switchDarkMode = view.findViewById(R.id.switchDarkMode);
        SharedPreferences prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);


        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });


        Switch switchNotifications = view.findViewById(R.id.switchNotifications);

        List<TextView> textViews = Arrays.asList(
                view.findViewById(R.id.textViewTitle),
                view.findViewById(R.id.textViewLanguage),
                view.findViewById(R.id.textViewFontSize),
                view.findViewById(R.id.textViewClearAllData),
                view.findViewById(R.id.textViewPrivacyPolicy),
                view.findViewById(R.id.textViewAppVersion),
                switchDarkMode,
                switchNotifications
        );

        Spinner spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
        String[] languages = {"English", "Spanish", "French", "German", "Japanese"};
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                languages
        );
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(languageAdapter);


        Button btnClearData = view.findViewById(R.id.btnClearData);
        btnClearData.setOnClickListener(v ->
            Toast.makeText(requireContext(), "Data cleared", Toast.LENGTH_SHORT).show()
        );


        Button btnPrivacyPolicy = view.findViewById(R.id.btnPrivacyPolicy);
        btnPrivacyPolicy.setOnClickListener(v ->
            new AlertDialog.Builder(requireContext())
                .setTitle("Privacy Policy")
                .setMessage("Rotten Banana does not collect or share any personal data. All movie data is stored locally on your device and is never transmitted to any server. This app was created for educational purposes only.")
                .setPositiveButton("OK", null)
                .show()
        );

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstLoad = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                if (firstLoad) { firstLoad = false; return; }
                Toast.makeText(requireContext(), "Language set to " + languages[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spinnerFontSize = view.findViewById(R.id.spinnerFontSize);
        String[] fontSizes = {"Small", "Medium", "Large"};
        ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                fontSizes
        );
        fontSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFontSize.setAdapter(fontSizeAdapter);

        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                float size;
                if (position == 0) size = 12f;
                else if (position == 1) size = 16f;
                else size = 20f;

                for (TextView tv : textViews) {
                    tv.setTextSize(size);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }
}
