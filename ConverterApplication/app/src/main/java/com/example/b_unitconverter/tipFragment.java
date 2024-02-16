 package com.example.b_unitconverter;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class tipFragment extends Fragment {

    private EditText editTextBillAmount;
    private SeekBar seekBarTipPercentage;
    private TextView textViewTipPercentage;
    private Button buttonCalculate;
    private TextView textViewTotalAmount;

    private double billAmount = 0;
    private int tipPercentage = 15;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip, container, false);

        editTextBillAmount = view.findViewById(R.id.editTextBillAmount);
        seekBarTipPercentage = view.findViewById(R.id.seekBarTipPercentage);
        textViewTipPercentage = view.findViewById(R.id.textViewTipPercentage);
        buttonCalculate = view.findViewById(R.id.buttonCalculate);
        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);

        setupListeners();

        setButtonBackgroundColor(R.id.buttonCalculate);

        return view;
    }
    private void setButtonBackgroundColor( int buttonId) {

        buttonCalculate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));

    }

    private void setupListeners() {
        editTextBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    billAmount = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    billAmount = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        seekBarTipPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentage = progress;
                updateTipPercentageText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No implementation yet
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No implementation yet
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTip();
            }
        });
    }

    private void updateTipPercentageText() {
        textViewTipPercentage.setText("Tip Percentage: " + tipPercentage + "%");
    }

    private void calculateTip() {
        double tipAmount = (billAmount * tipPercentage) / 100;
        double totalAmount = billAmount + tipAmount;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        textViewTotalAmount.setText("Total Amount: R" + decimalFormat.format(totalAmount));
    }
}
