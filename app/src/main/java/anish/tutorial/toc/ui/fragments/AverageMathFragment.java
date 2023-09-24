package anish.tutorial.toc.ui.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anish.tutorial.toc.R;
import anish.tutorial.toc.activity.MainActivity;

import static java.lang.Integer.parseInt;

public class AverageMathFragment extends Fragment implements TextWatcher {
    EditText results, value1, value2;
    TextView resulter1, resulter2;
    TextInputLayout inRes, inVal1, inVal2;


    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vs = inflater.inflate(R.layout.fragment_average_math, container, false);

        //pointing the values with id
        resulter1 = vs.findViewById(R.id.resultText);
        resulter2 = vs.findViewById(R.id.resultText2);
        results = vs.findViewById(R.id.enterResult);
        value1 = vs.findViewById(R.id.entervalue);
        value2 = vs.findViewById(R.id.entervalue2);
        inRes = vs.findViewById(R.id.inputResult);
        inVal1 = vs.findViewById(R.id.inputValue);
        inVal2 = vs.findViewById(R.id.inputValue2);

        //for checking and inserting values
        value1.addTextChangedListener(this);
        results.addTextChangedListener(this);
        value2.addTextChangedListener(this);
        MainActivity.fab.setVisibility(View.INVISIBLE);

        return vs;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(value1.getText().toString()) ||
                !TextUtils.isEmpty(results.getText().toString())) {

            String[] num = value1.getText().toString().trim().split(",");

            List<String> vars = new ArrayList<>(Arrays.asList(num));
            int total = 0;
            try {
                for (int i = 0; i < vars.size(); i++) {
                    total += parseInt(vars.get(i));
                }
            } catch (Exception e) {
                Log.e("total error", e.toString());
            }
            int sums = (vars.size() + 1);

            int mainRes = TextUtils.isEmpty(results.getText().toString().trim()) ? 0 : parseInt(results.getText().toString());
            //  Log.e("result Value", " " + mainRes);
            //   int res = TextUtils.isEmpty(value1.getText().toString().trim()) ? 0 :
            int res = ((mainRes * sums) - total);
            String fis = " The value of X is " + res + " as \n (" +
                    total + " + " + res + ") / " + sums + " = " + mainRes;

            resulter1.setText(fis);
            resulter1.setTextIsSelectable(true);
        } else
            resulter1.setText(" ");

        if (!TextUtils.isEmpty(value2.getText().toString())) {
            String[] nums = value2.getText().toString().trim().split(",");
            List<String> vars = new ArrayList<>(Arrays.asList(nums));
            int totals = 0;
            try {
                for (int i = 0; i < vars.size(); i++) {
                    totals += parseInt(vars.get(i));
                }
            } catch (Exception e) {
                Log.e("The String", e.toString());
            }
            int aver = totals / vars.size();
            String av = "The average value is " + aver;
            resulter2.setText(av);
        } else
            resulter2.setText(" ");

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
