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

public class massFragment extends Fragment {

    private EditText tons, pounds, ounces,kilograms, grams;
    private boolean converting = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mass, container, false);

         tons= view.findViewById(R.id.tons);
        pounds = view.findViewById(R.id.pounds);
        ounces= view.findViewById(R.id.ounces);
        kilograms = view.findViewById(R.id.kilogram);
        grams = view.findViewById(R.id.grams);






        //numerical calculator buttons

        ImageView back = view.findViewById(R.id.backspace);
        Button clear = view.findViewById(R.id.clearButtonM);

        //numerical calculator buttons colors





        tons.setInputType(InputType.TYPE_NULL);
        pounds.setInputType(InputType.TYPE_NULL);
        ounces.setInputType(InputType.TYPE_NULL);
        kilograms.setInputType(InputType.TYPE_NULL);
        grams.setInputType(InputType.TYPE_NULL);


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
        tons.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromTons();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        pounds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromPounds();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });
        ounces.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromOunces();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        kilograms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromKilograms();}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        grams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromGrams();
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
        tons.setText(String.valueOf(0));
        pounds.setText(String.valueOf(0));
        ounces.setText(String.valueOf(0));
        kilograms.setText(String.valueOf(0));
        grams.setText(String.valueOf(0));


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


            if (view.getId() == R.id.bit) {
                tons.requestFocus();
            } else if (view.getId() == R.id.megabyte) {
                pounds.requestFocus();
            }

            //set the numeric key to the focused EditText
            View focusedView = requireActivity().getCurrentFocus();
            if (focusedView instanceof EditText) {
                EditText focusedEditText = (EditText) focusedView;

                if (focusedEditText.getText().toString().equals("0.0") || focusedEditText.getText().toString().equals("0") || focusedEditText.getText().toString().equals("0.00")) {
                    focusedEditText.setText(key);
                } else {
                    if (focusedEditText.getText().toString().length() < 15) {

                        focusedEditText.append(key);
                    } else {
                        Toast.makeText(getContext(), "Maximum character limit reached", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }
    }
    private void updateValuesFromTons() {
        converting = true;
        String tonsStr = tons.getText().toString();

        if (!tonsStr.isEmpty()) {
            try {
                double tonsValue = Double.parseDouble(tonsStr);

                double pound = tonsValue * 2000;
                double ounce = pound * 16;
                double kilogram = tonsValue * 907.185;
                double gram = kilogram * 1000;

                pounds.setText(String.format(Locale.US, "%.2f", pound));
                ounces.setText(String.format(Locale.US, "%.2f", ounce));
                kilograms.setText(String.format(Locale.US, "%.2f", kilogram));
                grams.setText(String.format(Locale.US, "%.2f", gram));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid tons value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromPounds() {
        converting = true;
        String poundsStr = pounds.getText().toString();

        if (!poundsStr.isEmpty()) {
            try {
                double poundsValue = Double.parseDouble(poundsStr);

                double ton = poundsValue / 2000;
                double ounce = poundsValue * 16;
                double kilogram = poundsValue / 2.20462;
                double gram = kilogram * 1000;

                tons.setText(String.format(Locale.US, "%.2f", ton));
                ounces.setText(String.format(Locale.US, "%.2f", ounce));
                kilograms.setText(String.format(Locale.US, "%.2f", kilogram));
                grams.setText(String.format(Locale.US, "%.2f", gram));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid pounds value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromOunces() {
        converting = true;
        String ouncesStr = ounces.getText().toString();

        if (!ouncesStr.isEmpty()) {
            try {
                double ouncesValue = Double.parseDouble(ouncesStr);

                double ton = ouncesValue / 32000;
                double pound = ouncesValue / 16;
                double kilogram = pound / 2.20462;
                double gram = kilogram * 1000;

                tons.setText(String.format(Locale.US, "%.2f", ton));
                pounds.setText(String.format(Locale.US, "%.2f", pound));
                kilograms.setText(String.format(Locale.US, "%.2f", kilogram));
                grams.setText(String.format(Locale.US, "%.2f", gram));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid ounces value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromKilograms() {
        converting = true;
        String kilogramsStr = kilograms.getText().toString();

        if (!kilogramsStr.isEmpty()) {
            try {
                double kilogramsValue = Double.parseDouble(kilogramsStr);

                double ton = kilogramsValue / 1000;
                double pound = kilogramsValue * 2.20462;
                double ounce = pound * 16;
                double gram = kilogramsValue * 1000;

                tons.setText(String.format(Locale.US, "%.2f", ton));
                pounds.setText(String.format(Locale.US, "%.2f", pound));
                ounces.setText(String.format(Locale.US, "%.2f", ounce));
                grams.setText(String.format(Locale.US, "%.2f", gram));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid kilograms value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromGrams() {
        converting = true;
        String gramsStr = grams.getText().toString();

        if (!gramsStr.isEmpty()) {
            try {
                double gramsValue = Double.parseDouble(gramsStr);

                double ton = gramsValue / 1_000_000;
                double pound = gramsValue * 0.00220462;
                double ounce = pound * 16;
                double kilogram = gramsValue / 1000;

                tons.setText(String.format(Locale.US, "%.2f", ton));
                pounds.setText(String.format(Locale.US, "%.2f", pound));
                ounces.setText(String.format(Locale.US, "%.2f", ounce));
                kilograms.setText(String.format(Locale.US, "%.2f", kilogram));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid grams value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }






}