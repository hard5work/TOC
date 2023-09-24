package anish.tutorial.toc.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import anish.tutorial.toc.R;
import anish.tutorial.toc.adapter.PrdoctuctionAdpater;
import anish.tutorial.toc.calculations.CNFconversion;
import anish.tutorial.toc.database.SQLiteAdapter;
import anish.tutorial.toc.model.Productions;

public class HomeFragment extends Fragment {
    TextInputLayout symb, varb;
    EditText sym;
    //EditText var;
    AutoCompleteTextView var;
    Button calc, set, reset;
    RecyclerView question, answer;
    public static TextView textView;
    List<Productions> lists = new ArrayList<>();
    List<Productions> result = new ArrayList<>();
    String symbol, variables;

    private HomeViewModel homeViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        symb = root.findViewById(R.id.inputSymbol);
        varb = root.findViewById(R.id.inputVariable);
        sym = root.findViewById(R.id.enterSymbol);
        var = root.findViewById(R.id.enterVariable);
        set = root.findViewById(R.id.setProduction);
        calc = root.findViewById(R.id.calculate);
        reset = root.findViewById(R.id.reset);
        question = root.findViewById(R.id.grammarView);
     //   answer = root.findViewById(R.id.resultView);

        question.hasFixedSize();
        question.setLayoutManager(new LinearLayoutManager(getContext()));
      //  answer.hasFixedSize();
     //   answer.setLayoutManager(new LinearLayoutManager(getContext()));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ArrayAdapter<String> adapterVar =
                    new ArrayAdapter<String>(Objects.requireNonNull(getActivity())
                            ,android.R.layout.simple_expandable_list_item_1
                            ,new SQLiteAdapter(getActivity()).getVars());
            var.setThreshold(1);
            var.setAdapter(adapterVar);
        }

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symbol = sym.getText().toString();
                variables = var.getText().toString();

                if (sym.getText().toString().length() == 0 || var.getText().toString().length() == 0) {
                    if (sym.getText().toString().length() == 0)
                        symb.setErrorEnabled(true);
                        symb.setError("Enter Symbol");
                    if (var.getText().toString().length() == 0) {
                        varb.setErrorEnabled(true);
                        varb.setError("Enter Variable if Empty enter E");
                        Toast.makeText(getContext(), "Enter Variable if Empty enter E", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new SQLiteAdapter(getActivity()).insertVar(variables);
                    symb.setErrorEnabled(false);
                    varb.setErrorEnabled(false);
                    /*if (variables.contains("[A-Z]")) {
                        Toast.makeText(getContext(), variables + " FROM CONTAINS", Toast.LENGTH_SHORT).show();
                    }*/
                  /*  if (variables.matches(".*[A-Z].*")) {
                        Toast.makeText(getContext(), variables + " FROM matches", Toast.LENGTH_SHORT).show();
                    }*/
                  String[] variable = variables.split("/");
                    for (int i = 0 ;  i < variable.length ; i++) {
                    Productions p = new Productions();
                    p.setSymbol(symbol);
                    p.setVariable(variable[i]);
                        lists.add(p);
                        result.add(p);
                    }

                    sym.setText("");
                    var.setText("");
                    PrdoctuctionAdpater adpater = new PrdoctuctionAdpater(getContext(), lists);
                    question.setAdapter(adpater);
                    // calc.setClickable(true);
                }


            }
        });


        calc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {



                if (lists.size() > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert inputManager != null;
                        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    CNFconversion cnf = new CNFconversion(getContext());
                    cnf.result(result);
                 //   PrdoctuctionAdpater adpater = new PrdoctuctionAdpater(getContext(), cnf.result(lists));
                  //  answer.setAdapter(adpater);
                }
            }

        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.clear();
                result.clear();
                PrdoctuctionAdpater adpater = new PrdoctuctionAdpater(getContext(), lists);
                question.setAdapter(adpater);
                HomeFragment.textView.setText("");
               // answer.setAdapter(adpater);

            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}