package anish.tutorial.toc.ui.wordSearch;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import anish.tutorial.toc.R;
import anish.tutorial.toc.activity.MainActivity;
import anish.tutorial.toc.calculations.WordCalculation;

public class WordSearch extends Fragment {
    EditText len;
    AutoCompleteTextView words;
    TextInputLayout worder, lener;
    Button searcher, reseter;
   // public static TextView resulter;
    ProgressDialog pd;
    public static GridView resultView;
    String letter, lenes, results;
    int lens;
    List<String> resultList = new ArrayList<>();


    @SuppressLint({"ResourceAsColor", "RestrictedApi"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word_search, container, false);

        len = v.findViewById(R.id.enterlen);
        words = v.findViewById(R.id.enterWord);
        worder = v.findViewById(R.id.inputWord);
        lener = v.findViewById(R.id.inputLen);
        searcher = v.findViewById(R.id.searchWord);
        reseter = v.findViewById(R.id.reset);
    //    resulter = v.findViewById(R.id.resultText);
        resultView = v.findViewById(R.id.resultGrid);
        MainActivity.fab.setVisibility(View.VISIBLE);


        searcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // resulter.setText("");
                resultList.clear();
                letter = words.getText().toString().replace(" ", "");
                lenes = len.getText().toString();
                // Integer.parseInt(lenes);
                if (lenes.matches("")) {
                    lens = 0;
                } else
                    lens = Integer.parseInt(lenes);

                if (letter.length() < 1) {
                    //    pd.dismiss();
                    worder.setErrorEnabled(true);
                    worder.setError("Please Enter Letters");
                } else {
                    if (letter.length() < lens) {
                        //   pd.dismiss();
                        lener.setErrorEnabled(true);
                        String errorText = "Error::Number is greater than words";
                        lener.setError(errorText);
                        Spanned htmlText = Html.fromHtml("<p Style='color:#ff0000' >" + errorText + "</p>");
                    //    resulter.setText(htmlText);
                    } else {

                        worder.setErrorEnabled(false);
                        lener.setErrorEnabled(false);
                        AsyncTaskSearch asyncTaskSearch = new AsyncTaskSearch();
                        asyncTaskSearch.execute(letter);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert inputManager != null;
                            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }


                    }
                }
            }
        });

        reseter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resulter.setText("");
                resultList.clear();
                ArrayAdapter<String> adapter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, resultList);
                }
                //resulter.setText(results);
                resultView.setAdapter(adapter);
            }
        });

        MainActivity.fab.setImageResource(R.drawable.fab_delete);
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                   // resulter.setText("");
                                                    resultList.clear();
                                                    ArrayAdapter<String> adapter = null;
                                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                                        adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, resultList);
                                                    }
                                                    //resulter.setText(results);
                                                    resultView.setAdapter(adapter);
                                                    Log.e("Reset", "Done");
                                                }
                                            }
        );
        return v;
    }

    private class AsyncTaskSearch extends AsyncTask<String,  String , List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Searching!! Please wait");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();

        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try {

                resultList = new WordCalculation().lengthEqual( letter, lens);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultList;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            if (resultList != null) {

                ArrayAdapter<String> adapter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, resultList);
                }
                //resulter.setText(results);
                resultView.setAdapter(adapter);
                pd.hide();
            } else {
                pd.show();
            }
        }
    }
}
