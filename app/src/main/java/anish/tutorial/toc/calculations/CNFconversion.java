package anish.tutorial.toc.calculations;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anish.tutorial.toc.model.Productions;
import anish.tutorial.toc.ui.home.HomeFragment;

public class CNFconversion {

    Context context;
    List<Productions> pro, copy, emptySymbol, terminalList = new ArrayList<>();
    String empty = "";
    String msg1 = "";
    List<String> symbolList = new ArrayList<>();
    String startSymbol;
    List<Productions> newCNF = new ArrayList<>();
    Spanned finalText;

    List<String> emptySym = new ArrayList<>();
    List<Productions> removingSymbol = new ArrayList<>();
    List<Productions> unitProd = new ArrayList<>();
    List<Productions> removingNonReachable = new ArrayList<>();

    public CNFconversion(Context context) {
        this.context = context;
    }

    public List<Productions> result(List<Productions> question) {
        this.copy = question;
        startSymbol = question.get(0).getSymbol();
        symbolList.clear();
        for (int i = 0; i < question.size(); i++) {
            symbolList.add(question.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        for (int kl = 0; kl < question.size(); kl++) {
            if (kl ==-1) {
                kl = 0;
            }
            Log.e("check var" , question.get(kl).getVariable());
            if (question.get(kl).getVariable().matches(".*[A-Z].*")
                    && !question.get(kl).getVariable().matches("E")) {
                String oldvar = question.get(kl).getVariable();
                Log.e("checking inside ", oldvar);
                for (int ll = 0; ll < oldvar.length(); ll++) {
                    int count = 0;
                    if (String.valueOf(oldvar.charAt(ll)).matches(".*[A-Z].*")) {
                        String charVar = String.valueOf(oldvar.charAt(ll));
                        for (int lk = 0; lk < symbolList.size(); lk++) {

                            if (charVar.matches(symbolList.get(lk))) {
                                Log.e("checking " ,charVar + " is valid," + oldvar );
                                count++;
                                // msg1 +=   charVar + " COUNT "+ count +" \n";
                            }
                        }
                        if (count == 0) {
                            msg1 += charVar + " is Invalid, so Removing variable containing this terminal\n" +
                                    question.get(kl).getSymbol() + " -> " + question.get(kl).getVariable() + " \n";
                            Log.e("checking " ,charVar + " is Invalid, so Removing variable containing this terminal" +
                                    question.get(kl).getSymbol() + " -> " + question.get(kl).getVariable() );
                            question.remove(kl);
                            symbolList = symbols(question);
                            kl--;
                        }
                    }


                }

            }
        }
        int startCount = 0;
        for (int ck = 0; ck < question.size(); ck++) {
            //  Log.e("start symbol", startSymbol);
            if (!question.get(ck).getSymbol().matches(startSymbol)) {
                startCount++;
            } else {
                startCount = 0;
                break;
            }
            //  Log.e("COUNTS", startCount + " ck " + ck);
        }
        if (startCount !=0) {
            msg1 += " THERE IS NOT START SYMBOL SO PRODUCTION IS INVALID \n";
        } else {


            pro = emptyRemoving(question);
            pro = removeUnitProd(pro);
            pro = removeNonGeneratingSymbols(pro);
            pro = removeNotReachableSymbols(pro);
            if (pro.size() > 0)
                pro = theCNFform(pro);

            symbolList.clear();
            for (int i = 0; i < pro.size(); i++) {
                symbolList.add(pro.get(i).getSymbol());
            }

            for (int i = 0; i < symbolList.size(); i++) {
                int count = 0;
                for (int j = 0; j < symbolList.size(); j++) {
                    if (symbolList.get(i).matches(symbolList.get(j))) {
                        count++;
                        if (count > 1) {
                            symbolList.remove(j);
                            count--;
                            j--;
                        }
                    }
                }
            }
            List<Productions> newSym = new ArrayList<>();
            for (int ch = 0; ch < symbolList.size(); ch++) {
                if (ch == -1) {
                    ch = 0;
                }
                if (symbolList.get(ch).matches(".*[@#$%^&~].*")) {
                    String nS = checkRandomSymbol(randomSymbolReplace(), symbolList);
                    Productions p = new Productions();
                    p.setSymbol(nS);
                    p.setVariable(symbolList.get(ch));
                    newSym.add(p);
                    symbolList.set(ch, nS);
                    ch--;

                }
            }

            for (int ck = 0; ck < newSym.size(); ck++) {
                if (ck == 0) {
                    msg1 += "\nThe new Symbol replaced \n (Hint: removing special character to single symbol )\n";
                }
                msg1 += newSym.get(ck).getVariable() + " -> " + newSym.get(ck).getSymbol() + "\n";
            }
            for (int kl = 0; kl < pro.size(); kl++) {
                for (int ll = 0; ll < newSym.size(); ll++) {
                    if (pro.get(kl).getVariable().contains(newSym.get(ll).getVariable())) {
                        String newVs = pro.get(kl).getVariable().replace(newSym.get(ll).getVariable(), newSym.get(ll).getSymbol());
                        Productions p = new Productions();
                        p.setSymbol(pro.get(kl).getSymbol());
                        p.setVariable(newVs);
                        pro.set(kl, p);
                    }
                    if (pro.get(kl).getSymbol().matches(newSym.get(ll).getVariable())) {
                        String newVs = pro.get(kl).getSymbol().replace(newSym.get(ll).getVariable(), newSym.get(ll).getSymbol());
                        Productions p = new Productions();
                        p.setSymbol(newVs);
                        p.setVariable(pro.get(kl).getVariable());
                        pro.set(kl, p);

                    }
                }
            }
         /*  if (pro.size()>0) {
                if (!pro.get(0).getSymbol().matches(startSymbol)) {
                    msg1 += "Does match \n";
                    for (int lk = 0; lk < pro.size(); lk++) {
                        if (pro.get(lk).getSymbol().matches(startSymbol)) {
                            Collections.swap(pro, lk, 0);
                            msg1 += "swaped AT " + lk + "\n";
                        }
                        msg1 += pro.get(lk).getSymbol() + "->"+ pro.get(lk).getVariable() +"\n";
                    }
                }
            }*/

            for (int i = 0; i < symbolList.size(); i++) {
                if (i == 0) {
                    msg1 += "\n\n The Final Answer(Production) is \n\n";
                }
                String product = "";
                for (int j = 0; j < pro.size(); j++) {
                    if (symbolList.get(i).matches(pro.get(j).getSymbol())) {
                        if ((j + 1) < pro.size()) {
                            product += pro.get(j).getVariable() + " | ";
                        } else if ((j + 1) == pro.size()) {
                            product += pro.get(j).getVariable();
                        }
                    }
                }
                msg1 += symbolList.get(i) + "->" + product + " \n";

            }

        /*msg1+= " \n \n old style \n";
        for (int chk = 0; chk < pro.size() ;chk++){
            msg1 += pro.get(chk).getSymbol() + "->" + pro.get(chk).getVariable() + "\n";
        }
        msgBox(msg1);*/
            //  pro = finalDataset;
        }
        msgBox(msg1);
        return pro;

    }

    public List<Productions> emptyRemoving(List<Productions> question) {
        symbolList.clear();
        for (int i = 0; i < question.size(); i++) {
            symbolList.add(question.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\n \n The Question Production \n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < question.size(); j++) {
                if (symbolList.get(i).matches(question.get(j).getSymbol())) {
                    if ((j + 1) < question.size()) {
                        product += question.get(j).getVariable() + " | ";
                    } else if ((j + 1) == question.size()) {
                        product += question.get(j).getVariable();
                    }
                }
            }
            msg1 +=  symbolList.get(i) + "->" + product + "\n";

        }
      //  finalText = Html.fromHtml("<p Style='color:#ff0000; border:2px #ff0000' >" + msg1 + "</p>");
   //     HomeFragment.textView.setText(finalText);

        //Adding first set of empty production symbol
        for (int i = 0; i < question.size(); i++) {
            if (question.get(i).getVariable().matches("E")) {
                //emptySymbol.add(question.get(i));
                emptySym.add(question.get(i).getSymbol());
                //   symEmpty+=question.get(i).getSymbol();
                for (int j = 0; j < question.size(); j++) {
                    for (int jk = 0; jk < emptySym.size(); jk++) {
                        if (question.get(j).getVariable().contains(emptySym.get(jk))) {
                            String checker2 = question.get(j).getVariable();
                            String checker = checker2.replace(emptySym.get(jk), "");
                            if (checker.isEmpty()) {
                                for (int k = 0; k < emptySym.size(); k++) {
                                    if (question.get(j).getSymbol().matches(emptySym.get(k))) {
                                        Log.e("Already ", question.get(j).getSymbol() + " Already Entered");
                                    } else {
                                        emptySym.add(question.get(j).getSymbol());
                                    }
                                }
                            }
                        }
                    }
                }

            }

            for (int ii = 0; ii < emptySym.size(); ii++) {
                int count = 0;
                for (int j = 0; j < emptySym.size(); j++) {
                    if (emptySym.get(ii).matches(emptySym.get(j))) {
                        count++;
                        if (count > 1) {
                            emptySym.remove(j);
                            count--;
                            j--;
                        }
                    }

                }
            }
            for (int ii = 0; ii < emptySym.size(); ii++) {
                empty += emptySym.get(ii);
            }

            for (int ij = 0; ij < question.size(); ij++) {
                for (int jk = 0; jk < emptySym.size(); jk++) {
                    if (question.get(ij).getVariable().contains(emptySym.get(jk))) {
                        int counter = 0;
                        String temper = question.get(ij).getVariable();
                        temper = temper.replace(emptySym.get(jk), "");
                        if (!temper.isEmpty()) {
                            for (int kl = 0; kl < emptySym.size(); kl++) {
                                if (temper.contains(emptySym.get(kl))) {

                                    temper = temper.replace(emptySym.get(kl), "");
                                    Log.e("Empty set", empty + " AND VAR " + temper);

                                    if (temper.isEmpty()) {
                                        for (int k = 0; k < emptySym.size(); k++) {
                                            if (empty.contains(question.get(ij).getSymbol())) {
                                                Log.e("Already ", question.get(ij).getSymbol() + " Already Entered " + empty + " set ");

                                            } else {
                                                emptySym.add(question.get(ij).getSymbol());
                                                empty += question.get(ij).getSymbol();
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < emptySym.size(); k++) {
                                if (empty.contains(question.get(ij).getSymbol())) {
                                    Log.e("Already ", question.get(ij).getSymbol() + " Already Entered " + empty + " set ");

                                } else {
                                    emptySym.add(question.get(ij).getSymbol());
                                    empty += question.get(ij).getSymbol();
                                }
                            }
                        }
                    }
                }
            }
        }

        //secong time checking if symbols are avaiable or not
        for (int i = 0; i < emptySym.size(); i++) {
            int count = 0;
            for (int j = 0; j < emptySym.size(); j++) {
                if (emptySym.get(i).matches(emptySym.get(j))) {
                    count++;
                    if (count > 1) {
                        emptySym.remove(j);
                        count--;
                        j--;
                    }
                }

            }
        }
        //Result of empty symbols

        msg1 += "\n";
        for (int i = 0; i < emptySym.size(); i++) {
            if (i == 0) {
                msg1 += "The following symbols are nullable \n";
            }
            //  msg1 += " These are Nullable Symbols \n";
            msg1 += " " + emptySym.get(i) + "\n";

        }

        //Removing variable containing E -> empty variable
        // msg1 += " Now Adding new variables as There are Empty symbol present in the Production \n";
        for (int i = 0; i < question.size(); i++) {

            if (question.get(i).getVariable().matches("E")) {
                question.remove(i);
                i--;
            } else if (question.get(i).getVariable().matches(".*[A-Z].*")) {
                String variable = question.get(i).getVariable();
                String sym = question.get(i).getSymbol();
                int variableLength = variable.length();
                int count = 0;
                for (int aa = 0; aa < emptySym.size(); aa++) {
                    if (question.get(i).getVariable().contains(emptySym.get(aa))) {
                        count++;
                    }
                }
                if (variableLength == count) {
                    lengthEqual(variable, i, question);

                }
                if (variableLength != count) {
                    unEqualLength(variable, i, question);
                }
            }
        }

        for (int c = 0; c < question.size(); c++) {
            int counter = 0;
            for (int d = 0; d < question.size(); d++) {
                if (question.get(c).getSymbol().matches(question.get(d).getSymbol())
                        && question.get(c).getVariable().matches(question.get(d).getVariable())) {
                    counter++;
                    if (counter > 1) {
                        question.remove(d);
                        d--;
                        counter--;
                    }
                }

            }
        }


        //  msgBox(msg1);
        return question;
    }

    public List<Productions> removeNonGeneratingSymbols(List<Productions> questionList) {

        for (int c = 0; c < questionList.size(); c++) {
            int counter = 0;
            for (int d = 0; d < questionList.size(); d++) {
                if (questionList.get(c).getSymbol().matches(questionList.get(d).getSymbol())
                        && questionList.get(c).getVariable().matches(questionList.get(d).getVariable())) {
                    counter++;
                    if (counter > 1) {
                        questionList.remove(d);
                        d--;
                        counter--;
                    }
                }

            }
        }

        List<Productions> generatingSymbols = new ArrayList<>();

        for (int i = 1; i < questionList.size(); i++) {
            if (!questionList.get(i).getSymbol().matches(startSymbol)) {
                if (questionList.get(i).getVariable().matches(".*[A-Z].*")) {

                    if (questionList.get(i).getVariable().contains(questionList.get(i).getSymbol())) {
                        removingSymbol.add(questionList.get(i));

                    } else if (questionList.get(i).getVariable().matches(".*[A-Z].*")) {
                        for (int l = 0; l < questionList.size(); l++) {
                            if (questionList.get(i).getVariable().contains(questionList.get(l).getSymbol())) {
                                for (int p = 0; p < questionList.size(); p++) {
                                    //checking if symbol is starting or not, if not then need to store variable symbol
                                    if (!questionList.get(l).getSymbol().matches(startSymbol)) {
                                        //    for (int j = i; j < questionList.size(); j++) {
                                        if (questionList.get(l).getVariable().matches(".*[A-Z].*")) {
                                            if (questionList.get(l).getVariable().contains(questionList.get(i).getSymbol())) {
                                                removingSymbol.add(questionList.get(i));
                                                Log.e("contain own variable", p +
                                                        " Contain Non terminal with Non terminal part 2 "
                                                        + questionList.get(i).getSymbol() + "->"
                                                        + questionList.get(i).getVariable());
                                                break;

                                            }
                                        } else {
                                            terminalList.add(questionList.get(i));
                                            //Removing the generating symbol from removing symbol list
                                            for (int k = 0; k < removingSymbol.size(); k++) {
                                                if (questionList.get(i).getSymbol().matches(removingSymbol.get(k).getSymbol()) ||
                                                        questionList.get(i).getSymbol().matches(removingSymbol.get(k).getVariable())) {
                                                    removingSymbol.remove(k);
                                                    k--;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    //Possiblity Dont use this
                                    else {
                                        removingSymbol.add(questionList.get(i));
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {
                    terminalList.add(questionList.get(i));
                    //Removing the generating symbol from removing symbol list
                    int test = removingSymbol.size();
                    for (int l = 0; l < i; l++) {
                        for (int k = 0; k < removingSymbol.size(); k++) {
                            if ((removingSymbol.get(k).getSymbol().matches(questionList.get(i).getSymbol()))) {
                                removingSymbol.remove(k);
                                k--;
                            }
                        }
                    }
                }
            }
            generatingSymbols = questionList;
        }

        for (int i = 0; i < terminalList.size(); i++) {
            for (int j = 0; j < removingSymbol.size(); j++) {
                if (removingSymbol.get(j).getSymbol().contains(terminalList.get(i).getSymbol())
                        || removingSymbol.get(j).getVariable().contains(terminalList.get(i).getSymbol())) {
                    removingSymbol.remove(j);
                    j--;
                }
            }
        }
        symbolList.clear();
        for (int i = 0; i < questionList.size(); i++) {
            symbolList.add(questionList.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\n \n The Production after removing unit production \n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < questionList.size(); j++) {
                if (symbolList.get(i).matches(questionList.get(j).getSymbol())) {
                    if ((j + 1) < questionList.size()) {
                        product += questionList.get(j).getVariable() + " | ";
                    } else if ((j + 1) == questionList.size()) {
                        product += questionList.get(j).getVariable();
                    }
                }
            }
            msg1 += symbolList.get(i) + "->" + product + " \n";
        }

        for (int i = 0; i < removingSymbol.size(); i++) {
            int count = 0;
            for (int j = 0; j < removingSymbol.size(); j++) {
                if (removingSymbol.get(i).getSymbol().matches(removingSymbol.get(j).getSymbol())
                        && removingSymbol.get(i).getVariable().matches(removingSymbol.get(j).getVariable())) {
                    count++;
                    if (count > 1) {
                        removingSymbol.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        for (int i = 0; i < removingSymbol.size(); i++) {
            if (i == 0) {
                msg1 += "\n \n These symbol's Variable are non generating \n";
            }
            msg1 += removingSymbol.get(i).getSymbol() + "->"
                    + removingSymbol.get(i).getVariable() + " \n";

        }
        //For refreshing
        for (int k = 0; k < removingSymbol.size(); k++) {
            //for removing0.
            for (int i = 0; i < generatingSymbols.size(); i++) {
                //for process

                if (generatingSymbols.get(i).getVariable().contains(removingSymbol.get(k).getSymbol())
                        || generatingSymbols.get(i).getSymbol().contains(removingSymbol.get(k).getSymbol())) {
                    generatingSymbols.remove(i);
                    i--;
                }
            }
        }
        for (int kl = 0; kl < questionList.size(); kl++) {
            if (kl ==-1) {
                kl = 0;
            }
            Log.e("checking VAR" , questionList.get(kl).getVariable());
            if (questionList.get(kl).getVariable().matches(".*[A-Z].*")
                    && !questionList.get(kl).getVariable().matches("E")) {
                String oldvar = questionList.get(kl).getVariable();
                for (int ll = 0; ll < oldvar.length(); ll++) {
                    int count = 0;
                    Log.e("checking oldvar" ,oldvar);
                    if (String.valueOf(oldvar.charAt(ll)).matches(".*[A-Z].*")) {
                        String charVar = String.valueOf(oldvar.charAt(ll));
                        for (int lk = 0; lk < symbolList.size(); lk++) {

                            if (charVar.matches(symbolList.get(lk))) {
                                Log.e("checking " ,charVar + " is valid," + oldvar );
                                count++;
                                // msg1 +=   charVar + " COUNT "+ count +" \n";

                            }
                        }
                        if (count == 0) {
                          //  msg1 += charVar + " is Invalid, so Removing variable containing this terminal\n" +
                          //          questionList.get(kl).getSymbol() + " -> " + questionList.get(kl).getVariable() + " \n";
                            Log.e("removing" ,charVar + " is valid," + oldvar );
                            questionList.remove(kl);
                            kl--;
                            symbolList.clear();
                            for (int i = 0; i < questionList.size(); i++) {
                                symbolList.add(questionList.get(i).getSymbol());
                            }

                            for (int i = 0; i < symbolList.size(); i++) {
                                int counter = 0;
                                for (int j = 0; j < symbolList.size(); j++) {
                                    if (symbolList.get(i).matches(symbolList.get(j))) {
                                        counter++;
                                        if (counter > 1) {
                                            symbolList.remove(j);
                                            counter--;
                                            j--;
                                        }
                                    }
                                }
                            }
                        }
                    }


                }

            }
        }

        return generatingSymbols;
    }

    public List<Productions> removeNotReachableSymbols(List<Productions> questionList) {
        symbolList.clear();
        for (int i = 0; i < questionList.size(); i++) {
            symbolList.add(questionList.get(i).getSymbol());
        }
        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\n \n The Production after removing non generating production\n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < questionList.size(); j++) {
                if (symbolList.get(i).matches(questionList.get(j).getSymbol())) {
                    if ((j + 1) < questionList.size()) {
                        product += questionList.get(j).getVariable() + " | ";
                    } else if ((j + 1) == questionList.size()) {
                        product += questionList.get(j).getVariable();
                    }
                }
            }
            msg1 += symbolList.get(i) + "->" + product + " \n";
        }
        int startCount = 0;
        for (int ck = 0; ck < questionList.size(); ck++) {
          //  Log.e("start symbol", startSymbol);
            if (!questionList.get(ck).getSymbol().matches(startSymbol)) {
                startCount++;
            } else {
                startCount = 0;
                break;
            }
          //  Log.e("COUNTS", startCount + " ck " + ck);
        }
        if (startCount == 0) {
            List<Productions> finalResult = new ArrayList<>();
            if (questionList.size() < 1) {
                msg1 += "empty set \n";
                //  msgBox(msg1);
            } else {
                removingNonReachable.add(questionList.get(0));
                for (int i = 1; i < questionList.size(); i++) {
                    for (int j = 0; j < removingNonReachable.size(); j++) {
                        if (questionList.get(i).getSymbol().matches(startSymbol)) {
                            removingNonReachable.add(questionList.get(i));
                            break;
                        }
                    }
                }
                for (int i = 1; i < questionList.size(); i++) {
                    if (!questionList.get(i).getSymbol().matches(startSymbol)) {
                        for (int j = 0; j < removingNonReachable.size(); j++) {
                            if (removingNonReachable.get(j).getVariable().contains(questionList.get(i).getSymbol())) {
                                removingNonReachable.add(questionList.get(i));
                                break;
                            } else if (questionList.get(i).getSymbol().matches(removingNonReachable.get(j).getSymbol())) {
                                removingNonReachable.add(questionList.get(i));
                                break;
                            }
                        }
                    }

                }
            }
        }
        else{
            msg1 += "\n THERE IS NOT START SYMBOL SO PRODUCTION IS INVALID \n";
        }

        return removingNonReachable;
    }


    public void lengthEqual(String variables, int s, List<Productions> question) {

        List<String> array1 = new ArrayList<>();
        for (int i = 0, j = 1; i < variables.length(); i++, j++) {
            array1.add(variables.substring(i, j));
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
                for (int m = 0; m < question.size(); m++) {
                    if (temp.matches(question.get(m).getVariable()) && question.get(s).getSymbol().matches(question.get(m).getSymbol())) {
                        count++;
                    }
                }

                if (count == 0) {
                    Productions p = new Productions();
                    p.setSymbol(question.get(s).getSymbol());
                    p.setVariable(temp);
                    question.add(p);
                }
            }
        }
    }

    public void unEqualLength(String variables, int s, List<Productions> question) {

        for (int i = 0; i < emptySym.size(); i++) {
            if (variables.contains(emptySym.get(i))) {
                for (int k = -1; (k = variables.indexOf(emptySym.get(i), k + 1)) != -1; k++) {

                    StringBuilder newVar = new StringBuilder(variables);
                    newVar.setCharAt(k, '!');
                    int count = 0;
                    for (int m = 0; m < question.size(); m++) {
                        if (newVar.toString().replace("!", "").matches(question.get(m).getVariable())
                                && question.get(s).getSymbol().matches(question.get(m).getSymbol())) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        if (!newVar.toString().matches(" ") || !newVar.toString().isEmpty()) {
                            Productions p = new Productions();
                            p.setSymbol(question.get(s).getSymbol());
                            p.setVariable(newVar.toString().replace("!", ""));
                            question.add(p);
                        }
                    }
                }
            }
        }

    }

    public List<Productions> removeUnitProd(List<Productions> question) {

        for (int c = 0; c < question.size(); c++) {
            int counter = 0;
            for (int d = 0; d < question.size(); d++) {
                if (question.get(c).getSymbol().matches(question.get(d).getSymbol())
                        && question.get(c).getVariable().matches(question.get(d).getVariable())) {
                    counter++;
                    if (counter > 1) {
                        question.remove(d);
                        d--;
                        counter--;
                    }
                }

            }
        }
        symbolList.clear();
        for (int i = 0; i < question.size(); i++) {
            symbolList.add(question.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\nThe Production after removing null Production \n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < question.size(); j++) {
                if (symbolList.get(i).matches(question.get(j).getSymbol())) {
                    if ((j + 1) < question.size()) {
                        product += question.get(j).getVariable() + " | ";
                    } else if ((j + 1) == question.size()) {
                        product += question.get(j).getVariable();
                    }
                }
            }
            msg1 += symbolList.get(i) + "->" + product + " \n";
        }
        for (int i = 0; i < question.size(); i++) {
            for (int j = 0; j < question.size(); j++) {
                if (question.get(i).getVariable().matches(question.get(j).getSymbol())) {
                    unitProd.add(question.get(j));
                }
            }
        }
        for (int i = 0; i < unitProd.size(); i++) {
            int count = 0;
            for (int j = 0; j < unitProd.size(); j++) {
                if (unitProd.get(i).getSymbol().matches(unitProd.get(j).getSymbol())
                        && unitProd.get(i).getVariable().matches(unitProd.get(j).getVariable())) {
                    count++;
                    if (count > 1) {
                        unitProd.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        for (int i = 0; i < unitProd.size(); i++) {
            if (i == 0) {
                msg1 += "\nReplacing Unit Production with following Production \n";
            }
            msg1 += unitProd.get(i).getSymbol() + "->" + unitProd.get(i).getVariable() + "\n";
        }

        for (int i = 0; i < question.size(); i++) {
            for (int j = 0; j < unitProd.size(); j++) {
                if (question.get(i).getVariable().matches(unitProd.get(j).getSymbol())
                ) {
                    for (int rr = 0; rr < question.size(); rr++) {
                        int nextCounter = 0;
                        if (question.get(i).getVariable().matches(question.get(rr).getSymbol())) {

                            for (int cr = 0; cr < question.size(); cr++) {
                                if (question.get(i).getSymbol().matches(question.get(cr).getSymbol())
                                        && question.get(rr).getVariable().matches(question.get(cr).getVariable())) {
                                    nextCounter++;
                                }
                            }
                            if (nextCounter == 0) {
                                Productions p = new Productions();
                                p.setSymbol(question.get(i).getSymbol());
                                p.setVariable(question.get(rr).getVariable());
                                question.add(p);
                            }
                        }
                    }
                    question.remove(i);
                    i--;
                }
            }
        }

        return question;
    }

    public List<Productions> theCNFform(List<Productions> question) {
        /*
         *Rules for CNF conversion
         * 1>> start symbol must contain empty production
         * 2>> start symbol must contain two non terminals only
         * 3>> start symbol must contain only one terminal
         *
         *
         * Above three case must be fullfilled for CNF by variable
         */

        symbolList.clear();
        for (int i = 0; i < question.size(); i++) {
            symbolList.add(question.get(i).getSymbol());
        }
        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\n \n The Production after removing non reachable production\n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < question.size(); j++) {
                if (symbolList.get(i).matches(question.get(j).getSymbol())) {
                    if ((j + 1) < question.size()) {
                        product += question.get(j).getVariable() + " | ";
                    } else if ((j + 1) == question.size()) {
                        product += question.get(j).getVariable();
                    }
                }
            }
            msg1 += symbolList.get(i) + "->" + product + " \n";
           // msgBox(msg1);
        }
        msg1 += "\nNow converting production to CNF\n";
        for (int i = 0; i < question.size(); i++) {
            if (question.get(i).getVariable().matches(".*[a-z0-9].*")
                   // || question.get(i).getVariable().matches(".*[0-9].*")
                    && question.get(i).getVariable().length()>1 ) {
                String vary = question.get(i).getVariable();
                for (int j = 0; j < vary.length(); j++) {
                    if (String.valueOf(vary.charAt(j)).matches(".*[a-z].*")
                            || String.valueOf(vary.charAt(j)).matches(".*[0-9].*")) {
                        String newVAR = String.valueOf(vary.charAt(j));
                        String sym = checkRandom(randomSymbol(), newCNF);
                        if (newCNF.isEmpty()) {
                            Productions p = new Productions();
                            p.setSymbol(sym);
                            p.setVariable(newVAR);
                            newCNF.add(p);
                            Productions q = new Productions();
                            q.setSymbol(sym);
                            q.setVariable(newVAR);
                            question.add(q);
                        } else {
                            int count = 0;
                            for (int m = 0; m < newCNF.size(); m++) {
                                StringBuilder sb = new StringBuilder(vary);
                                sb.setCharAt(j, '!');
                                vary = sb.toString().replace("!", sym);
                                if (newCNF.get(m).getVariable().contains(newVAR)) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(sym);
                                p.setVariable(newVAR);
                                newCNF.add(p);
                                Productions q = new Productions();
                                q.setSymbol(sym);
                                q.setVariable(newVAR);
                                question.add(q);
                            }
                        }
                    }
                }
            }
        }

        msg1 += "\nFirst replace the terminal by a variable and introduce new productions for which are not as the productions in CNF \n " +
                "The terminals to be replaced are following: \n (Hint: Here Special character after Symbol are counter like A1,A2,B1,B2)\n";
        for (int k = 0; k < newCNF.size(); k++) {
            msg1 += newCNF.get(k).getSymbol() + " -> " + newCNF.get(k).getVariable() + "\n";
        }

        for (int l = 0; l < question.size(); l++) {
            for (int n = 0; n < newCNF.size(); n++) {
                if (question.get(l).getVariable().contains(newCNF.get(n).getVariable())
                        && question.get(l).getVariable().length() > 1) {
                    Productions p = new Productions();
                    p.setSymbol(question.get(l).getSymbol());
                    String newV = question.get(l).getVariable().replace(newCNF.get(n).getVariable(), newCNF.get(n).getSymbol());
                    p.setVariable(newV);
                    question.set(l, p);
                }
            }
        }
        symbolList.clear();
        for (int i = 0; i < question.size(); i++) {
            symbolList.add(question.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }

        msg1 += "\n \n The Production after replacing terminal:  \n";
        for (int i = 0; i < symbolList.size(); i++) {
            String product = "";
            for (int j = 0; j < question.size(); j++) {
                if (symbolList.get(i).matches(question.get(j).getSymbol())) {
                    if ((j + 1) < question.size()) {
                        product += question.get(j).getVariable() + " | ";
                    } else if ((j + 1) == question.size()) {
                        product += question.get(j).getVariable();
                    }
                }
            }
            msg1 += symbolList.get(i) + " -> " + product + " \n";
        }

        for (int pq = 0; pq < question.size(); pq++) {
            if (pq == -1) {
                pq = 0;
            }
            if (question.get(pq).getVariable().length() > 2) {
                String oldVar = question.get(pq).getVariable();
                String charZero = String.valueOf(oldVar.charAt(0));
                String charOne1 = String.valueOf(oldVar.charAt(1));
                String char01 = charZero + charOne1;
                String charTwo = String.valueOf(oldVar.charAt(2));
                String chare12 = charOne1 + charTwo;
                if (oldVar.matches(".*[@#$%^&~].*") && oldVar.length() > 3) {
                    Boolean charOne = (String.valueOf(oldVar.charAt(1)).matches(".*[@#$%^&~].*"));
                    String charThree3 = String.valueOf(oldVar.charAt(3));
                    Boolean charThree = (charThree3.matches(".*[@#$%^&~].*"));
                    Boolean charTwos = (charTwo.matches(".*[@#$%^&~].*"));
                    String char23 = charTwo + charThree3;
                    String char012 = char01 + charTwo;
                    String newSym = checkRandom(randomSymbol(), newCNF);
                    if (oldVar.length() == 4) {
                        if (charOne && charThree) {
                            Log.e("BALANCE", "no need to change " + oldVar);
                        } else if (charOne) {
                            String newVar = charTwo + charThree3;
                            Log.e("at char one", "second pos spl " + oldVar);
                            int count = 0;

                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && newVar.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(newVar);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(newVar) && question.get(pq).getVariable().length() > 3) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(newVar, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }
                            }
                            pq--;
                        } else if (charThree) {
                            int count = 0;
                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && char01.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(char01);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(char01) && question.get(pq).getVariable().length() > 3) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(char01, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }

                            }
                            pq--;
                        } else if (charTwos) {
                            int count = 0;
                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && char012.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(char012);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(char012) && question.get(pq).getVariable().length() > 3) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(char012, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }
                            }
                            pq--;
                        }
                    } else {
                        String varsNew = "";
                        if (charOne && charThree) {
                            varsNew = char01 + char23;
                            int count = 0;
                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && varsNew.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(varsNew);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(varsNew) && question.get(pq).getVariable().length() > 4) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(varsNew, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }
                            }
                            pq--;
                        } else if (charOne) {
                            int count = 0;
                            varsNew = char01 + charTwo;
                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && varsNew.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(varsNew);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(varsNew) && question.get(pq).getVariable().length() > 3) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(varsNew, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }
                            }
                            pq--;
                        } else if (charThree) {
                            int count = 0;
                            varsNew = charZero + chare12;
                            for (int ml = 0; ml < newCNF.size(); ml++) {
                                if (newSym.matches(newCNF.get(ml).getSymbol()) && varsNew.matches(newCNF.get(ml).getVariable())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                Productions p = new Productions();
                                p.setSymbol(newSym);
                                p.setVariable(varsNew);
                                newCNF.add(p);
                                question.add(p);
                                if (question.get(pq).getVariable().contains(varsNew) && question.get(pq).getVariable().length() > 3) {
                                    Productions ps = new Productions();
                                    ps.setSymbol(question.get(pq).getSymbol());
                                    String newV = question.get(pq).getVariable().replace(varsNew, newSym);
                                    ps.setVariable(newV);
                                    question.set(pq, ps);
                                }
                            }
                            pq--;
                        }
                    }
                } else if (!oldVar.matches(".*[@#$%^&~].*")) {
                    String newSym = checkRandom(randomSymbol(), newCNF);
                    if (String.valueOf(oldVar.charAt(0)).matches(String.valueOf(oldVar.charAt(1)))) {
                        int count = 0;
                        for (int ml = 0; ml < newCNF.size(); ml++) {
                            if (newSym.matches(newCNF.get(ml).getSymbol()) && chare12.matches(newCNF.get(ml).getVariable())) {
                                count++;
                            }
                        }
                        if (count == 0) {
                            Productions p = new Productions();
                            p.setSymbol(newSym);
                            p.setVariable(chare12);
                            newCNF.add(p);
                            Productions q = new Productions();
                            q.setSymbol(newSym);
                            q.setVariable(chare12);
                            question.add(q);
                            if (question.get(pq).getVariable().contains(chare12) && question.get(pq).getVariable().length() > 2) {
                                Productions ps = new Productions();
                                ps.setSymbol(question.get(pq).getSymbol());
                                String newV = question.get(pq).getVariable().replace(chare12, newSym);
                                ps.setVariable(newV);
                                question.set(pq, ps);
                            }
                        }
                    } else {
                        int count = 0;
                        for (int ml = 0; ml < newCNF.size(); ml++) {
                            if (newSym.matches(newCNF.get(ml).getSymbol()) && char01.matches(newCNF.get(ml).getVariable())) {
                                count++;
                            }
                        }
                        if (count == 0) {
                            Productions p = new Productions();
                            p.setSymbol(newSym);
                            p.setVariable(char01);
                            newCNF.add(p);
                            Productions q = new Productions();
                            q.setSymbol(newSym);
                            q.setVariable(char01);
                            question.add(q);
                            if (question.get(pq).getVariable().contains(char01) && question.get(pq).getVariable().length() > 2) {
                                Productions ps = new Productions();
                                ps.setSymbol(question.get(pq).getSymbol());
                                String newV = question.get(pq).getVariable().replace(char01, newSym);
                                ps.setVariable(newV);
                                question.set(pq, ps);
                            }
                        }
                    }
                    pq--;
                }
            }
        }

        msg1 += "Now, Replacing the sequence of non terminals by a variable and introduce new productions: \n" +
                "Here, are Replaced Symbols and variables :\n";
        for (int k = 0; k < newCNF.size(); k++) {
            msg1 += newCNF.get(k).getSymbol() + " -> " + newCNF.get(k).getVariable() + "\n";
        }
        return question;
    }


    public void msgBox(String msger) {
        assert msger != null;
        HomeFragment.textView.setText(msger);
        HomeFragment.textView.setTextIsSelectable(true);
    }

    public String randomSymbol() {
        Random generator = new Random();
        String randomness = "@#%&~";
        String randomnes = "ABCDFGHIJKLMNOPQRTUVWZYZ";
        StringBuilder randomString = new StringBuilder();
        StringBuilder randomLeter = new StringBuilder();
        int rLength = (int) (Math.random() * randomnes.length());
        int sLength = (int) (Math.random() * randomness.length());
        int randomLength = generator.nextInt(2);
        char tempChar;
        tempChar = (char) (randomness.charAt(sLength));
        randomString.append(tempChar);

        tempChar = (char) randomnes.charAt(rLength);
        randomLeter.append(tempChar);


        return randomLeter.toString() + randomString.toString();


    }

    public String randomSymbolReplace() {
        Random generator = new Random();
        String randomness = "@#%&~";
        String randomnes = "ABCDFGHIJKLMNOPQRTUVWZYZ";
        StringBuilder randomString = new StringBuilder();
        StringBuilder randomLeter = new StringBuilder();
        int rLength = (int) (Math.random() * randomnes.length());
        int sLength = (int) (Math.random() * randomness.length());
        int randomLength = generator.nextInt(2);
        char tempChar;

        tempChar = (char) (randomness.charAt(sLength));
        randomString.append(tempChar);

        tempChar = (char) randomnes.charAt(rLength);
        randomLeter.append(tempChar);
        return randomLeter.toString();


    }

    public String checkRandom(String randoms, List<Productions> newCNF) {
        int count = 0;
        String ran = "";
        Log.e("check for sym", randoms);
        for (int i = 0; i < newCNF.size(); i++) {
            if (randoms.matches(newCNF.get(i).getSymbol())) {
                count++;
                Log.e("found sym", randoms + " count " + count);
            }
        }
        if (count > 0) {
            Log.e("found sym counter > 0", randoms + " count " + count);
            randoms = checkRandom(randomSymbol(), newCNF);
        } else {
            Log.e("not found sym", randoms + " count " + count);
            return randoms;
        }
        return randoms;
    }

    public String checkRandomSymbol(String randoms, List<String> newCNF) {
        int count = 0;
        String ran = "";
        Log.e("check for sym", randoms);
        for (int i = 0; i < newCNF.size(); i++) {
            if (randoms.matches(newCNF.get(i))) {
                count++;
                Log.e("found sym", randoms + " count " + count);
            }
        }
        if (count == 1 || count > 1) {
            Log.e("found sym counter == 1", randoms + " count " + count);
            ran = checkRandomSymbol(randomSymbolReplace(), newCNF);

        } else if (count == 0) {
            Log.e("not found sym", randoms + " count " + count);
            ran = randoms;
        }
        return ran;
    }

    public List<String> symbols(List<Productions> questionList){
        symbolList.clear();
        for (int i = 0; i < questionList.size(); i++) {
            symbolList.add(questionList.get(i).getSymbol());
        }

        for (int i = 0; i < symbolList.size(); i++) {
            int count = 0;
            for (int j = 0; j < symbolList.size(); j++) {
                if (symbolList.get(i).matches(symbolList.get(j))) {
                    count++;
                    if (count > 1) {
                        symbolList.remove(j);
                        count--;
                        j--;
                    }
                }
            }
        }
        return  symbolList;
    }
}
