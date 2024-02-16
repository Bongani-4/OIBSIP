package com.example.b_unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class temperatureFragment extends Fragment {

    private EditText celcious, fahranheit, kelvin;
    private boolean converting = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        celcious = view.findViewById(R.id.celious);
        fahranheit = view.findViewById(R.id.fahrenheit);
        kelvin = view.findViewById(R.id.kelvin);


        //numerical calculator buttons

        Button clear = view.findViewById(R.id.clearButtonTMP);

        clear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));


        setupTextWatchers();


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllEditTexts();
            }
        });


        return view;
    }



    private void setupTextWatchers() {
        celcious.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromCelsius();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        fahranheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromFahrenheit();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });
        kelvin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromKelvin();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    private void clearAllEditTexts() {

        // Clear all EditText fields


        celcious.setText(String.valueOf(0));
        fahranheit.setText(String.valueOf(0));
        kelvin.setText(String.valueOf(0));

    }



    //calculation logic









    private void updateValuesFromCelsius() {
        converting = true;
        String celsiusStr = celcious.getText().toString();

        if (!celsiusStr.isEmpty()) {
            try {
                double celsiusValue = Double.parseDouble(celsiusStr);

                double fahrenheits = (celsiusValue * 9 / 5) + 32;
                double kelvins = celsiusValue + 273.15;

                fahranheit.setText(String.format(Locale.US, "%.2f", fahrenheits));
                kelvin.setText(String.format(Locale.US, "%.2f", kelvins));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid Celsius value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromKelvin() {
        converting = true;
        String kelvinStr = kelvin.getText().toString();

        if (!kelvinStr.isEmpty()) {
            try {
                double kelvinValue = Double.parseDouble(kelvinStr);

                double celsius = kelvinValue - 273.15;


                double fahrenheit = (kelvinValue - 273.15) * 9/5 + 32;


                celcious.setText(String.format(Locale.US, "%.2f", celsius));
                fahranheit.setText(String.format(Locale.US, "%.2f", fahrenheit));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid Kelvin value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromFahrenheit() {
        converting = true;
        String fahrenheitStr = fahranheit.getText().toString();

        if (!fahrenheitStr.isEmpty()) {
            try {
                double fahrenheitValue = Double.parseDouble(fahrenheitStr);

                double celsius = (fahrenheitValue - 32) * 5/9;
                double kelvins = (fahrenheitValue + 459.67) * 5/9;

                celcious.setText(String.format(Locale.US, "%.2f", celsius));
                kelvin.setText(String.format(Locale.US, "%.2f", kelvins));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid Fahrenheit value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }










}