package anish.tutorial.toc.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import anish.tutorial.toc.R;
import anish.tutorial.toc.activity.MainActivity;

import static java.lang.Integer.parseInt;

public class PercentageCalculator extends Fragment implements TextWatcher {
    EditText value1,value2,value3, value11,value22,value33;
    TextView results1,results2,results3;
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vs = inflater.inflate(R.layout.fragment_percentage_calculator,container,false);
        value1 = vs.findViewById(R.id.enterpercent1);
        value11 = vs.findViewById(R.id.enterNumber1);
        value2 = vs.findViewById(R.id.enterpercent2);
        value22 = vs.findViewById(R.id.enterNumber2);
        value3 = vs.findViewById(R.id.enterpercent3);
        value33 = vs.findViewById(R.id.enterNumber3);
        results1 = vs.findViewById(R.id.resultTexts1);
        results2 = vs.findViewById(R.id.resultTexts2);
        results3 = vs.findViewById(R.id.resultTexts3);
        value1.addTextChangedListener(this);
        value11.addTextChangedListener(this);
        value2.addTextChangedListener(this);
        value22.addTextChangedListener(this);
        value3.addTextChangedListener(this);
        value33.addTextChangedListener(this);

        MainActivity.fab.setVisibility(View.INVISIBLE);
        return vs;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(value1.getText().toString() )||
                !TextUtils.isEmpty(value11.getText().toString())){
            String precent = value1.getText().toString();
            String num = value11.getText().toString();
            if (precent.length() > 0 && num.length() > 0){
                double ans = (double) (parseInt(num) * parseInt(precent))/100;
                String comp = "THE ANSWER OF " + precent + " % of " + num + " is '" + ans +"'";
                results1.setText(comp);
            }
        }
        else{
            results1.setText("");
        }
        if (!TextUtils.isEmpty(value2.getText().toString() )||
                !TextUtils.isEmpty(value22.getText().toString())){
            String percent = value2.getText().toString();
            String num = value22.getText().toString();
            if (percent.length() > 0 && num.length() > 0){
                double ans = (double) (parseInt(percent) * 100)/parseInt(num);
                String comp = "THE value " +percent + "is " + ans + "% of " + num ;
                results2.setText(comp);
            }
        }
        else{
            results2.setText("");
        }
        if (!TextUtils.isEmpty(value3.getText().toString() )||
                !TextUtils.isEmpty(value33.getText().toString())){
            String percent = value3.getText().toString();
            String num = value33.getText().toString();
            if (percent.length() > 0 && num.length() > 0){
                int val2 = parseInt(num);
                int val1 = parseInt(percent);
                double ans = (double) ((val2-val1)*100)/val1;
                String comp = "";
                if (ans > 0)
                 comp= "The percent has increased by '" + ans + "' so  " + percent + " raised to " + num ;
                else
                    comp= "The percent has decreased by '" + ans + "' so  " + percent + " decreased to " + num ;
                results3.setText(comp);
            }
        }
        else{
            results3.setText("");
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
