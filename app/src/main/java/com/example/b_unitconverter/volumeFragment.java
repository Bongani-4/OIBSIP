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

public class volumeFragment extends Fragment {

    private EditText gal, litre, millitre;
    private boolean converting = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volume, container, false);

        gal= view.findViewById(R.id.uSgallons);
        litre = view.findViewById(R.id.litre);
        millitre = view.findViewById(R.id.millitre);


        //numerical calculator buttons


        ImageView back = view.findViewById(R.id.backspace);
        Button clear = view.findViewById(R.id.clearButtonV);

        //numerical calculator buttons colors



        gal.setInputType(InputType.TYPE_NULL);
        litre.setInputType(InputType.TYPE_NULL);
        millitre.setInputType(InputType.TYPE_NULL);


        setupTextWatchers();

        //click listeners for keyboard buttons
        setupNumericButtons(view, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.zero, R.id.comma);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackspace();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllEditTexts();
            }
        });



        return view;
    }


    private void setupNumericButtons(View view, int... buttonIds) {
        for (int buttonId : buttonIds) {
            Button button = view.findViewById(buttonId);
            setButtonBackgroundColor(view, buttonId);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNumericKeyPressed(v);
                }
            });
        }
    }




    private void setupTextWatchers() {
        gal.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromGallons();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        litre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromLiters();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });
        millitre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromMilliliters();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void setButtonBackgroundColor(View parentView, int buttonId) {
        Button button = parentView.findViewById(buttonId);
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));

    }
    private void clearAllEditTexts() {

        // Clear all EditText fields
        gal.setText(String.valueOf(0));
        litre.setText(String.valueOf(0));
        millitre.setText(String.valueOf(0));

    }
    private void handleBackspace() {
        View focusedView = requireActivity().getCurrentFocus();

        if (focusedView instanceof EditText) {
            EditText editText = (EditText) focusedView;
            Editable editable = editText.getText();
            int length = editable.length();

            if (length > 1) {
                editable.delete(length - 1, length);
            } else {
                clearAllEditTexts();
            }

        }


    }

    //calculation logic










    public void onNumericKeyPressed(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            String key = button.getText().toString();


            if (view.getId() == R.id.uSgallons) {
                gal.requestFocus();
            } else if (view.getId() == R.id.litre) {
                litre.requestFocus();
            }

            //set the numeric key to the focused EditText
            View focusedView = requireActivity().getCurrentFocus();
            if (focusedView instanceof EditText) {
                EditText focusedEditText = (EditText) focusedView;

                if (focusedEditText.getText().toString().equals("0.0") || focusedEditText.getText().toString().equals("0")   || focusedEditText.getText().toString().equals("0.00")) {
                    focusedEditText.setText(key);
                } else {
                    if (focusedEditText.getText().toString().length() < 15) {

                        focusedEditText.append(key);
                    }else{
                        Toast.makeText(getContext(), "Maximum character limit reached", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }
    }



    private void updateValuesFromGallons() {
        converting = true;
        String gallonsStr = gal.getText().toString();

        if (!gallonsStr.isEmpty()) {
            try {
                double gallonsValue = Double.parseDouble(gallonsStr);

                double liter = gallonsValue * 3.78541;
                double milliliter = liter * 1000;

                litre.setText(String.format(Locale.US, "%.2f", liter));
                millitre.setText(String.format(Locale.US, "%.2f", milliliter));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid gallons value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromLiters() {
        converting = true;
        String litersStr = litre.getText().toString();

        if (!litersStr.isEmpty()) {
            try {
                double litersValue = Double.parseDouble(litersStr);

                double gallon = litersValue / 3.78541;
                double milliliter = litersValue * 1000;

                gal.setText(String.format(Locale.US, "%.2f", gallon));
                millitre.setText(String.format(Locale.US, "%.2f", milliliter));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid liters value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromMilliliters() {
        converting = true;
        String millilitersStr = millitre.getText().toString();

        if (!millilitersStr.isEmpty()) {
            try {
                double millilitersValue = Double.parseDouble(millilitersStr);

                double gallon = millilitersValue / 3785.41;
                double liter = millilitersValue / 1000;

                gal.setText(String.format(Locale.US, "%.2f", gallon));
                litre.setText(String.format(Locale.US, "%.2f", liter));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid milliliters value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }







}