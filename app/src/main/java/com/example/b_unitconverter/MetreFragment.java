package com.example.b_unitconverter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import android.view.View;

public class MetreFragment extends Fragment {

    private EditText inputMeters;
    private TextView resultMillimeters, resultCentimeters, resultKilometers,resultmetre,
            resultInches,  resultFeet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_metre, container, false);

           inputMeters = view.findViewById(R.id.editmetre);
        resultMillimeters = view.findViewById(R.id.millimetre);
        resultCentimeters = view.findViewById(R.id.centmetre);
        resultKilometers = view.findViewById(R.id.kilometre);
        resultmetre = view.findViewById(R.id.metre);
        resultInches = view.findViewById(R.id.inches);
        resultFeet = view.findViewById(R.id.feet);

        Button buttonOne = view.findViewById(R.id.one);
        Button buttonTwo = view.findViewById(R.id.two);
        Button button3 = view.findViewById(R.id.three);
        Button button4 = view.findViewById(R.id.four);
        Button button5= view.findViewById(R.id.five);
        Button button6 = view.findViewById(R.id.six);
        Button button7 = view.findViewById(R.id.seven);
        Button button8 = view.findViewById(R.id.eight);
        Button button9 = view.findViewById(R.id.nine);
        Button button0 = view.findViewById(R.id.zero);
        Button buttoncomma = view.findViewById(R.id.comma);
        ImageView back = view.findViewById(R.id.backspace);


        setButtonBackgroundColor(view, R.id.one);
        setButtonBackgroundColor(view, R.id.two);
        setButtonBackgroundColor(view, R.id.three);
        setButtonBackgroundColor(view, R.id.four);
        setButtonBackgroundColor(view, R.id.five);
        setButtonBackgroundColor(view, R.id.six);
        setButtonBackgroundColor(view, R.id.seven);
        setButtonBackgroundColor(view, R.id.eight);
        setButtonBackgroundColor(view, R.id.nine);
        setButtonBackgroundColor(view, R.id.zero);
        setButtonBackgroundColor(view, R.id.comma);

        inputMeters.setInputType(InputType.TYPE_NULL);


        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }

        });button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });buttoncomma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackspace();
            }
        });












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

            double millimeters = Math.round(inputMetersValue * 1000 * 100.0) / 100.0;
            double centimeters = Math.round(inputMetersValue * 100 * 100.0) / 100.0;
            double kilometers = Math.round(inputMetersValue / 1000 * 100.0) / 100.0;
            double inches = Math.round(inputMetersValue * 39.3701 * 100.0) / 100.0;
            double feet = Math.round(inputMetersValue * 3.28084 * 100.0) / 100.0;
            double same = Math.round(inputMetersValue * 1 * 100.0) / 100.0;




            resultMillimeters.setText(String.valueOf(millimeters));
            resultCentimeters.setText(String.valueOf(centimeters));
            resultKilometers.setText(String.valueOf(kilometers));
            resultInches.setText(String.valueOf(inches));
            resultFeet.setText(String.valueOf(feet));
            resultmetre.setText(String.valueOf(same));



        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Enter a numeric value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void onNumericKeyPressed(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            String key = button.getText().toString();
            inputMeters.append(key);
        }
    }
    private void handleBackspace() {
        Editable editable = inputMeters.getText();
        int length = editable.length();

        if (length > 0) {

            editable.delete(length - 1, length);
        }
    }
    private void setButtonBackgroundColor(View parentView, int buttonId) {
        Button button = parentView.findViewById(buttonId);
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white));
    }
}
