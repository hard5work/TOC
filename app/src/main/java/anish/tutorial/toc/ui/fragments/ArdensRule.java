package anish.tutorial.toc.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import anish.tutorial.toc.R;
import anish.tutorial.toc.adapter.EquationAdapter;
import anish.tutorial.toc.adapter.PrdoctuctionAdpater;
import anish.tutorial.toc.calculations.ArdensCalculation;
import anish.tutorial.toc.model.Productions;

public class ArdensRule extends Fragment {
    @SuppressLint("StaticFieldLeak")
    public static TextView results;
    EditText rhs,lhs,fstate;
    TextInputLayout rhss,lhss,finalState;
    Button seteq, calculate,reset;
    List<Productions> lists = new ArrayList<>();
    List<Productions> resultsList = new ArrayList<>();
    RecyclerView eqviews;
    String slhs,srhs, finals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vs = inflater.inflate(R.layout.fragment_ardensrule, container, false);
        results = vs.findViewById(R.id.text_home);
        rhs = vs.findViewById(R.id.enterrhs);
        lhs = vs.findViewById(R.id.enterlhs);
        rhss = vs.findViewById(R.id.inputrhs);
        lhss = vs.findViewById(R.id.inputlhs);
        eqviews = vs.findViewById(R.id.equationView);
        seteq = vs.findViewById(R.id.setequation);
        calculate = vs.findViewById(R.id.calculate);
        reset = vs.findViewById(R.id.reset);
        finalState = vs.findViewById(R.id.inputFINAL);
        fstate = vs.findViewById(R.id.enterFINAL);

        eqviews.hasFixedSize();
        eqviews.setLayoutManager(new LinearLayoutManager(getContext()));

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.clear();
                resultsList.clear();
                EquationAdapter adapter = new EquationAdapter(getContext(),lists);
                eqviews.setAdapter(adapter);
                results.setText("");
            }
        });
        seteq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // results.setText("\u002B");
               // String plus = "\u002B";
               // results.setText(plus);
                srhs = rhs.getText().toString();
                slhs = lhs.getText().toString();
                 if (srhs.length() == 0 || slhs.length() ==0){
                     if (srhs.length() ==0){
                         rhss.setErrorEnabled(true);
                         rhss.setError("Enter rhs expression");
                     }
                     if (slhs.length() == 0){
                         lhss.setErrorEnabled(true);
                         lhss.setError("Enter lhs expression");
                     }
                 }
                 else{
                     rhss.setErrorEnabled(false);
                     lhss.setErrorEnabled(false);

                     String[] rhsExpression = srhs.split("\\+");

                     for(int i = 0 ; i< rhsExpression.length;i++){
                         Productions p = new Productions();
                         p.setLhsv(slhs);
                        // p.setRhsv(srhs);
                         p.setRhsv(rhsExpression[i]);
                         lists.add(p);
                         resultsList.add(p);
                     }
                     rhs.setText("");
                     lhs.setText("");
                     EquationAdapter adapter = new EquationAdapter(getContext(),lists);
                     eqviews.setAdapter(adapter);

                 }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finals = fstate.getText().toString();
                if(lists.size() >0 && finals.length() > 1  ){

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert inputManager != null;
                        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    finalState.setErrorEnabled(false);
                    new ArdensCalculation().calculate(lists, finals);
                }
                else if (finals.length() < 1){
                    finalState.setErrorEnabled(true);
                    finalState.setError("Please Enter final State");

                }
            }
        });


        return vs;
    }

}


