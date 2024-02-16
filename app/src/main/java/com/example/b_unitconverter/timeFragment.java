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

public class timeFragment extends Fragment {

    private EditText sec, millisec,min , hour, day;
    private boolean converting = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        sec = view.findViewById(R.id.sec);
        millisec= view.findViewById(R.id.millisec);
        min = view.findViewById(R.id.min);
        hour = view.findViewById(R.id.hour);
        day = view.findViewById(R.id.day);

             //numerical calculator buttons


        ImageView back = view.findViewById(R.id.backspace);
        Button clear = view.findViewById(R.id.clearButtonT);
        clear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey));

        //numerical calculator buttons colors




        sec.setInputType(InputType.TYPE_NULL);
        millisec.setInputType(InputType.TYPE_NULL);
        min.setInputType(InputType.TYPE_NULL);
        hour.setInputType(InputType.TYPE_NULL);
        day.setInputType(InputType.TYPE_NULL);


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
        millisec.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromMilliseconds();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });

        sec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No implementation yet

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!converting) {
                    updateValuesFromSeconds();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No implementation yet
            }
        });
        min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromMinutes();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromHours();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!converting) {
                    updateValuesFromDays();
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
        millisec.setText(String.valueOf(0));
        sec.setText(String.valueOf(0));
        min.setText(String.valueOf(0));
        hour.setText(String.valueOf(0));
        day.setText(String.valueOf(0));

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


            if (view.getId() == R.id.millisec) {
                millisec.requestFocus();
            } else if (view.getId() == R.id.sec) {
                sec.requestFocus();
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

    private void updateValuesFromMilliseconds() {
        converting = true;
        String millisecondsStr = millisec.getText().toString();
        if (!millisecondsStr.isEmpty()) {
            try {
                double millisecondsValue = Double.parseDouble(millisecondsStr);

                double seconds = millisecondsValue / 1000;
                double minutes = millisecondsValue / (1000 * 60);
                double hours = millisecondsValue / (1000 * 60 * 60);
                double days = millisecondsValue / (1000 * 60 * 60 * 24);

                sec.setText(String.format(Locale.US, "%.2f", seconds));
                min.setText(String.format(Locale.US, "%.2f", minutes));
                hour.setText(String.format(Locale.US, "%.2f", hours));
                day.setText(String.format(Locale.US, "%.2f", days));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid milliseconds value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }

    private void updateValuesFromSeconds() {
        converting = true;
        String secondsStr = sec.getText().toString();

        if (!secondsStr.isEmpty()) {
            try {
                double secondsValue = Double.parseDouble(secondsStr);

                double minutes = secondsValue / 60;
                double hours = minutes / 60;
                double days = hours / 24;
                double milliseconds = secondsValue * 1000;

                min.setText(String.format(Locale.US, "%.2f", minutes));
                hour.setText(String.format(Locale.US, "%.2f", hours));
                day.setText(String.format(Locale.US, "%.2f", days));
                millisec.setText(String.format(Locale.US, "%.2f", milliseconds));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid seconds value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromHours() {
        converting = true;
        String hoursStr = hour.getText().toString();
        if (!hoursStr.isEmpty()) {
            try {
                double hoursValue = Double.parseDouble(hoursStr);

                double milliseconds = hoursValue * 60 * 60 * 1000;
                double seconds = hoursValue * 60 * 60;
                double minutes = hoursValue * 60;
                double days = hoursValue / 24;

                millisec.setText(String.format(Locale.US, "%.2f", milliseconds));
                sec.setText(String.format(Locale.US, "%.2f", seconds));
                min.setText(String.format(Locale.US, "%.2f", minutes));
                day.setText(String.format(Locale.US, "%.2f", days));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid hours value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromMinutes() {
        converting = true;
        String minutesStr = min.getText().toString();
        if (!minutesStr.isEmpty()) {
            try {
                double minutesValue = Double.parseDouble(minutesStr);

                double milliseconds = minutesValue * 60 * 1000;
                double seconds = minutesValue * 60;
                double hours = minutesValue / 60;
                double days = minutesValue / (24 * 60);

                millisec.setText(String.format(Locale.US, "%.2f", milliseconds));
                sec.setText(String.format(Locale.US, "%.2f", seconds));
                hour.setText(String.format(Locale.US, "%.2f", hours));
                day.setText(String.format(Locale.US, "%.2f", days));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid minutes value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }
    private void updateValuesFromDays() {
        converting = true;
        String daysStr = day.getText().toString();
        if (!daysStr.isEmpty()) {
            try {
                double daysValue = Double.parseDouble(daysStr);

                double milliseconds = daysValue * 24 * 60 * 60 * 1000;
                double seconds = daysValue * 24 * 60 * 60;
                double minutes = daysValue * 24 * 60;
                double hours = daysValue * 24;
                millisec.setText(String.format(Locale.US, "%.2f", milliseconds));
                sec.setText(String.format(Locale.US, "%.2f", seconds));
                min.setText(String.format(Locale.US, "%.2f", minutes));
                hour.setText(String.format(Locale.US, "%.2f", hours));

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid days value", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                converting = false;
            }
        }
    }









}