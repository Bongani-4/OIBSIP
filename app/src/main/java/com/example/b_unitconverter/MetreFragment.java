package com.example.b_unitconverter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MetreFragment extends Fragment {

    private EditText inputMeters;
    private TextView resultMillimeters, resultCentimeters, resultKilometers,
            resultInches, resultMiles, resultFeet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_metre, container, false);

        inputMeters = view.findViewById(R.id.editmetre);
        resultMillimeters = view.findViewById(R.id.millimetre);
        resultCentimeters = view.findViewById(R.id.centmetre);
        resultKilometers = view.findViewById(R.id.kilometre);
        resultInches = view.findViewById(R.id.inches);
        resultFeet = view.findViewById(R.id.feet);

        inputMeters.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void afterTextChanged(Editable editable) {
                               convert();
            }
        });

        return view;
    }



    private void convert() {
        String inputStr = inputMeters.getText().toString();
        if (inputStr.isEmpty()) {
            // No implementation yet
            return;
        }

        try {
            double inputMetersValue = Double.parseDouble(inputStr);

            double millimeters = inputMetersValue * 1000;
            double centimeters = inputMetersValue * 100;
            double kilometers = inputMetersValue / 1000;
            double inches = inputMetersValue * 39.3701;
            double feet = inputMetersValue * 3.28084;

            resultMillimeters.setText(String.valueOf(millimeters));
            resultCentimeters.setText(String.valueOf(centimeters));
            resultKilometers.setText(String.valueOf(kilometers));
            resultInches.setText(String.valueOf(inches));
            resultFeet.setText(String.valueOf(feet));



        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Enter a numeric value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public  void onNumericKeyPressed(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();
        inputMeters.append(key);
    }
}
