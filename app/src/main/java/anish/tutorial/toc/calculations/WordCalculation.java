package anish.tutorial.toc.calculations;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import anish.tutorial.toc.model.Productions;
import anish.tutorial.toc.ui.wordSearch.WordSearch;


public class WordCalculation implements SpellCheckerSession.SpellCheckerSessionListener {
    String msg = "";
    List<String> wordList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();
    public List<Character> all = new ArrayList<Character>();
    private SpellCheckerSession mScs;


   /* public void msgBox(String msg) {
        WordSearch.resulter.setTextIsSelectable(true);
        WordSearch.resulter.setText(msg);

    }*/


    public List<String> lengthEqual( final String variables, final int s) {
       /* Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    while(!isInterrupted()){
                        Thread.sleep(1000);*/
                                List<String> array1 = new ArrayList<>();
                                for (int i = 0, j = 1; i < variables.length(); i++, j++) {
                                    array1.add(variables.substring(i, j));
                                    // all.add(variables.charAt(i));
                                }
                                String temp = " ";

                                double slent = Math.pow(2, array1.size());

                                for (int i = 0; i < slent; i++) {
                                    temp = "";
                                    for (int j = 0; j < array1.size(); j++) {
                                        if ((i & ((int) Math.pow(2, j))) > 0) {
                                            temp += array1.get(j);
                                        }
                                    }
                                    if (temp != "") {
                                        int count = 0;
                                        // wordList.add(temp);
                                        // char[] check = temp.toCharArray();
                                        if (s!=0) {
                                            if (temp.length() == s)
                                                lengthEqual(temp, 0, temp.length() - 1);
                                        }
                                        else{
                                            if (temp.length() > 1 )
                                             lengthEqual(temp, 0, temp.length() - 1);
                                        }


                                    }
                                }
/*

                                if (s != 0) {
                                    for (int k = 0; k < wordList.size(); k++) {
                                        if (wordList.get(k).length() == s) {
                                            msg += "< "+ k + ". " + wordList.get(k) + " > ";
                                        }
                                    }
                                } else {
                                    for (int k = 0; k < wordList.size(); k++) {
                                        if (wordList.get(k).length() > 1) {
                                            msg += "< "+k +". " +wordList.get(k) + " > ";
                                        //    Log.e("Time to Check", msg.length() + " size");
                                        }
                                    }

                                }
*/

/*
                    }
                }catch (Exception e){
                    Log.e("Thread error ", e.toString());
                }
                super.run();
            }
        };
        t.start();*/


       /* Log.e("I am Done Here 2", "Are you still rotating 2 " + msg.length());
       //msgBox(msg);
        Log.e("Message size " , msg.length() + " ma ta xu ni");
        if (msg.length() ==0){
            msg+= " I am sorry! Too much msg to Display! Choose limited WordLength . Thankyou";
        }else {
            Log.e("Message size ", msg.length() + " ma ta xu ni");
        }*/
        return wordList;
    }

    public void lengthEqual(String variables, int l, int r) {

        if (l == r) {

            wordList.add(variables);
            //Log.e("Hello"," I am Here");
          //  mScs.getSuggestions(new TextInfo(variables),3);
         //   msg+="< " + variables + " >";
           // Log.e("msg" , msg.length() + " size");
        }
        else {
            for (int i = l; i <= r; i++) {
                StringBuilder st = new StringBuilder(variables);

                char c = variables.charAt(l);
                  st.setCharAt(l,variables.charAt(i));
                st.setCharAt(i,c);
                variables = st.toString();
                lengthEqual(variables, l + 1, r);
                c = variables.charAt(l);
                st.setCharAt(l,variables.charAt(i));
                st.setCharAt(i,c);
                variables = st.toString();
            }
        }

    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();

        for (int i=0;i<results.length;i++){
            Log.e("The Results ", results[i].toString());
            final int len = results[i].getSuggestionsCount();
           // sb.append('\n');

            for (int j=0; j<len; ++j){
                Log.e("THE STRING AT", results[i].getSuggestionAt(j));
                sb.append(results[i].getSuggestionAt(j));
            }
            //sb.append("("+len + ")");
        }
       // wordList.add(sb.toString());
        for (int k = 0 ; k < wordList.size() ; k++){
            Log.e("new WOrd List", wordList.get(k));
        }
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
}
