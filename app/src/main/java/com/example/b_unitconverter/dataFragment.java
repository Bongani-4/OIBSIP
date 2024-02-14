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

public class dataFragment extends Fragment {

    private EditText bit, kilobyte, megabyte, gigabyte, terabyte, tebibyte;
    private boolean converting = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        bit = view.findViewById(R.id.bit);
        kilobyte= view.findViewById(R.id.kilobyte);
        megabyte = view.findViewById(R.id.megabyte);
        gigabyte = view.findViewById(R.id.gigabyte);
        terabyte = view.findViewById(R.id.terabyte);
        tebibyte  = view.findViewById(R.id.tebibyte);






        //numerical calculator buttons

        Button buttonOne = view.findViewById(R.id.one);
        Button buttonTwo = view.findViewById(R.id.two);
        Button button3 = view.findViewById(R.id.three);
        Button button4 = view.findViewById(R.id.four);
        Button button5 = view.findViewById(R.id.five);
        Button button6 = view.findViewById(R.id.six);
        Button button7 = view.findViewById(R.id.seven);
        Button button8 = view.findViewById(R.id.eight);
        Button button9 = view.findViewById(R.id.nine);
        Button button0 = view.findViewById(R.id.zero);
        Button buttoncomma = view.findViewById(R.id.comma);
        ImageView back = view.findViewById(R.id.backspace);
        Button clear = view.findViewById(R.id.clearButtonD);

        //numerical calculator buttons colors

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
        setButtonBackgroundColor(view,R.id.clearButtonD);


        bit.setInputType(InputType.TYPE_NULL);
        kilobyte.setInputType(InputType.TYPE_NULL);
        megabyte.setInputType(InputType.TYPE_NULL);
        gigabyte.setInputType(InputType.TYPE_NULL);
        terabyte.setInputType(InputType.TYPE_NULL);
        tebibyte.setInputType(InputType.TYPE_NULL);

        setupTextWatchers();

        //click listeners for keyboard buttons

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
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }

        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
        buttoncomma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumericKeyPressed(v);
            }
        });
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




    private void setupTextWatchers() {
        bit.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromBits();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        kilobyte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromKilobytes();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });
        megabyte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromMegabytes();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        gigabyte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromGigabytes();}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        terabyte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromTerabytes();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tebibyte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromTebibytes();
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
        bit.setText(String.valueOf(0));
        kilobyte.setText(String.valueOf(0));
        megabyte.setText(String.valueOf(0));
        gigabyte.setText(String.valueOf(0));
        terabyte.setText(String.valueOf(0));
        tebibyte.setText(String.valueOf(0));

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


    private void updateValuesFromBits() {
        converting = true;
        String bitsStr = bit.getText().toString();

        if (!bitsStr.isEmpty()) {
            try {
                double bitsValue = Double.parseDouble(bitsStr);

                double kilobytes = bitsValue / 8 / 1024;
                double megabytes = kilobytes / 1024;
                double gigabytes = megabytes / 1024;
                double terabytes = gigabytes / 1024;
                double tebibytes = bitsValue / 8 / 1024 / 1024 / 1024 / 1024;

                kilobyte.setText(String.format(Locale.US, "%.2f", kilobytes));
                megabyte.setText(String.format(Locale.US, "%.2f", megabytes));
                gigabyte.setText(String.format(Locale.US, "%.2f", gigabytes));
                terabyte.setText(String.format(Locale.US, "%.2f", terabytes));
                tebibyte.setText(String.format(Locale.US, "%.2f", tebibytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid bit value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromMegabytes() {
        converting = true;
        String megabytesStr = megabyte.getText().toString();

        if (!megabytesStr.isEmpty()) {
            try {
                double megabytesValue = Double.parseDouble(megabytesStr);

                double kilobytes = megabytesValue * 1024;
                double bits = kilobytes * 8 * 1024;
                double gigabytes = megabytesValue / 1024;
                double terabytes = gigabytes / 1024;
                double tebibytes = megabytesValue / 1024 / 1024 / 1024;

                kilobyte.setText(String.format(Locale.US, "%.2f", kilobytes));
                bit.setText(String.format(Locale.US, "%.2f", bits));
                gigabyte.setText(String.format(Locale.US, "%.2f", gigabytes));
                terabyte.setText(String.format(Locale.US, "%.2f", terabytes));
                tebibyte.setText(String.format(Locale.US, "%.2f", tebibytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid megabyte value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromKilobytes() {
        converting = true;
        String kilobytesStr = kilobyte.getText().toString();

        if (!kilobytesStr.isEmpty()) {
            try {
                double kilobytesValue = Double.parseDouble(kilobytesStr);

                double bytes = kilobytesValue * 1024;
                double bits = bytes * 8;
                double megabytes = kilobytesValue / 1024;
                double gigabytes = megabytes / 1024;
                double terabytes = gigabytes / 1024;
                double tebibytes = terabytes / 1024;

                bit.setText(String.format(Locale.US, "%.2f", bits));
                megabyte.setText(String.format(Locale.US, "%.2f", megabytes));
                gigabyte.setText(String.format(Locale.US, "%.2f", gigabytes));
                terabyte.setText(String.format(Locale.US, "%.2f", terabytes));
                tebibyte.setText(String.format(Locale.US, "%.2f", tebibytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid kilobyte value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromGigabytes() {
        converting = true;
        String gigabytesStr = gigabyte.getText().toString();

        if (!gigabytesStr.isEmpty()) {
            try {
                double gigabytesValue = Double.parseDouble(gigabytesStr);

                double bytes = gigabytesValue * 1024 * 1024 * 1024;
                double bits = bytes * 8;
                double kilobytes = gigabytesValue * 1024 * 1024;
                double megabytes = gigabytesValue * 1024;
                double terabytes = gigabytesValue / 1024;
                double tebibytes = terabytes / 1024;

                bit.setText(String.format(Locale.US, "%.2f", bits));
                kilobyte.setText(String.format(Locale.US, "%.2f", kilobytes));
                megabyte.setText(String.format(Locale.US, "%.2f", megabytes));
                terabyte.setText(String.format(Locale.US, "%.2f", terabytes));
                tebibyte.setText(String.format(Locale.US, "%.2f", tebibytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid gigabyte value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromTerabytes() {
        converting = true;
        String terabytesStr = terabyte.getText().toString();

        if (!terabytesStr.isEmpty()) {
            try {
                double terabytesValue = Double.parseDouble(terabytesStr);

                double bytes = terabytesValue * 1024 * 1024 * 1024 * 1024;
                double bits = bytes * 8;
                double kilobytes = terabytesValue * 1024 * 1024 * 1024;
                double megabytes = terabytesValue * 1024 * 1024;
                double gigabytes = terabytesValue * 1024;
                double tebibytes = terabytesValue / 1024;

                bit.setText(String.format(Locale.US, "%.2f", bits));
                kilobyte.setText(String.format(Locale.US, "%.2f", kilobytes));
                megabyte.setText(String.format(Locale.US, "%.2f", megabytes));
                gigabyte.setText(String.format(Locale.US, "%.2f", gigabytes));
                tebibyte.setText(String.format(Locale.US, "%.2f", tebibytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid terabyte value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }



    public void onNumericKeyPressed(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            String key = button.getText().toString();


            if (view.getId() == R.id.bit) {
                bit.requestFocus();
            } else if (view.getId() == R.id.megabyte) {
                megabyte.requestFocus();
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
    private void updateValuesFromTebibytes() {
        converting = true;
        String tebibytesStr = tebibyte.getText().toString();

        if (!tebibytesStr.isEmpty()) {
            try {
                float tebibytesValue = Float.parseFloat(tebibytesStr);

                long bytes = (long) (tebibytesValue * 1_099_511_627_776L); // 2^40
                double bits = bytes * 8;
                float kilobytes = (tebibytesValue * 1_073_741_824L); // 2^30
                float megabytes = (tebibytesValue * 1_048_576L); // 2^20
                float gigabytes = (tebibytesValue * 1024L); // 2^10
                float terabytes = tebibytesValue / 1024; // 2^(-10)

                bit.setText(String.format(Locale.US, "%.2f", bits));
                kilobyte.setText(String.format(Locale.US, "%.2f", kilobytes));
                megabyte.setText(String.format(Locale.US, "%.2f", megabytes));
                gigabyte.setText(String.format(Locale.US, "%.2f", gigabytes));
                terabyte.setText(String.format(Locale.US, "%.2f", terabytes));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid tebibyte value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }





}