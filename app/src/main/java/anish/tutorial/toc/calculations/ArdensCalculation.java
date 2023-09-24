package anish.tutorial.toc.calculations;

import android.content.Context;

import com.github.mikephil.charting.formatter.IFillFormatter;

import java.util.ArrayList;
import java.util.List;

import anish.tutorial.toc.adapter.PrdoctuctionAdpater;
import anish.tutorial.toc.model.Productions;
import anish.tutorial.toc.ui.fragments.ArdensRule;

public class ArdensCalculation {
    Context context;
    List<Productions> eqlist = new ArrayList<>();
    List<Productions> subs2 = new ArrayList<>();
    List<Productions> subs3 = new ArrayList<>();
    List<String> subs = new ArrayList<>();
    List<Productions>  finalsub = new ArrayList<>();
    List<String> lhsList = new ArrayList<>();
    List<Productions> resultList = new ArrayList<>();
    String initalQ,finals;
    String msg = "";
    int count, countrhs, countlhs;

    public void calculate(List<Productions> eqlist, String finals) {
        this.initalQ = finals;
        this.eqlist = eqlist;
     //   initalQ = eqlist.get(0).getLhsv();
        finals = eqlist.get(0).getLhsv();
        count = 0;
        countlhs = 0;
        countrhs = 0;
        for (int i = 0; i < eqlist.size(); i++) {
            if (eqlist.get(i).getLhsv().matches(finals)) {
                if (eqlist.get(i).getRhsv().matches("E")) {
                    count = 1;
                } else
                    count = 0;
            }
        }


        if (count == 1) {
            msg+= "\n" + eqlist.get(1).getLhsv() + " -> " +eqlist.get(1).getRhsv() + "\n";
            if (eqlist.get(1).getLhsv().contains(eqlist.get(0).getLhsv()) && eqlist.get(1).getRhsv().matches("E")){
                eqlist.remove(1);
                String news = eqlist.get(0).getRhsv().replace(eqlist.get(0).getLhsv(),"");
                news = news + "*";
                Productions ps = new Productions();
                ps.setLhsv(eqlist.get(0).getLhsv());
                ps.setRhsv(news);
                eqlist.set(0,ps);
                //firstSubsList(eqlist);
            }

            msg+= "\n" + eqlist.get(1).getLhsv() + " -> " +eqlist.get(1).getRhsv() + "\n";
            for (int i = 0; i < eqlist.size(); i++) {
                msg += eqlist.get(i).getLhsv() + " = " + eqlist.get(i).getRhsv() + "\n";
            }
            firstSubsList(eqlist);
            //Steps to carry out
            /*
             * 1> check initial ma sabai subs hunxa ki hudaina
             * 2> check inital ma aaune subs ko value substitution vayo ki nai
             * 3> if all inital ma aaune ko sub vayo vane substitute in initial state
             * */

            //for 1st step i.e initial substitute if possible

            //for substitution
            for (int i = 0; i < subs2.size(); i++) {
                for (int j = 0; j < eqlist.size(); j++) {
                    if (eqlist.get(j).getLhsv().matches(initalQ)) {
                        if (eqlist.get(j).getRhsv().contains(subs2.get(i).getLhsv())) {
                            String mains = eqlist.get(j).getRhsv();
                            String replacer = subs2.get(i).getLhsv();
                            String news = subs2.get(i).getRhsv();
                            String replace = mains.replace(replacer, news);
                            Productions ps = new Productions();
                            ps.setLhsv(eqlist.get(j).getLhsv());
                            ps.setRhsv(replace);
                            eqlist.set(j, ps);

                        }
                    }

                }
            }

//            msg += "\n New Equation \n";
//            for (int i = 0; i < eqlist.size(); i++) {
//                msg += eqlist.get(i).getLhsv() + " = " + eqlist.get(i).getRhsv() + "\n";
//            }
            // for using formula that is taking common

            //firstly concatination the strings;
            for (int i = 0; i < eqlist.size(); i++) {

                if (i != eqlist.size() - 1){
                    if (eqlist.get(i).getLhsv().matches(initalQ) && eqlist.get(i + 1).getLhsv().matches(initalQ)) {
                        if (eqlist.get(i).getRhsv().contains(initalQ) && eqlist.get(i + 1).getRhsv().contains(initalQ)) {
                            Productions p = new Productions();
                            p.setLhsv(eqlist.get(i).getLhsv());
                            String concat2 = eqlist.get(i + 1).getRhsv() + ")";
                            p.setRhsv(concat2);
                            eqlist.set(i + 1, p);
                            //msg += "\n concated " + concat + "\n";
                        }
                        // subs3.add(i,p);
                    }
            }

            }
            String concat = "";
            for (int i = 0; i < eqlist.size(); i++) {
                if (eqlist.get(i).getLhsv().matches(initalQ)) {

//                    if (i == 0) {
//                        concat += eqlist.get(i).getRhsv();
//                    } else {
//                        concat += "+" + eqlist.get(i).getRhsv();
//                    }
                    if (i == 0) {
                        concat += eqlist.get(i).getRhsv();
                    } else if (!eqlist.get(i).getLhsv().matches(eqlist.get(i - 1).getLhsv())) {
                        concat += eqlist.get(i).getRhsv();
                    } else {
                        concat += "+" + eqlist.get(i).getRhsv();
                    }

                }
            }
            Productions ps = new Productions();
            ps.setLhsv(initalQ);
            ps.setRhsv(concat);
            subs3.add(ps);


            msg += "\n New concated Equation 1 \n";
            for (int i = 0; i < subs3.size(); i++) {
                msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";
            }
            for (int i = 0; i < eqlist.size(); i++) {
                msg += eqlist.get(i).getLhsv() + " = " + eqlist.get(i).getRhsv() + "\n";
            }

            //substituting and using Arden's formula


            String replacer = "";
            for (int i = 0; i < subs3.size(); i++) {
                String[] check = subs3.get(i).getRhsv().split("\\+");
                for (int ck = 0; ck < check.length; ck++) {
                    int cks = check.length-1;
                    if (ck != check.length-1) {
                        if (check[ck].contains(initalQ) && check[ck + 1].contains(initalQ)) {
                            replacer = subs3.get(i).getRhsv().replace(initalQ, "A");
                            replacer = replacer.replace("+A", "+");
                            replacer = replacer.replace("A", "A(");
                            replacer = replacer.replace("A", initalQ);
                            replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
                            replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
                            replacer = replacer.replace("+E", "E+");
                            // replacer = replacer.charAt(1)+replacer.substring(0,replacer.length()-1);
                            Productions ps2 = new Productions();
                            ps2.setLhsv(initalQ);
                            ps2.setRhsv(replacer);
                            subs3.set(i, ps2);
                        }

                    }
                    else{
                        if (check[cks].contains(initalQ) && check[cks - 1].contains(initalQ)) {
                            replacer = subs3.get(i).getRhsv().replace(initalQ, "A");
                            replacer = replacer.replace("+A", "+");
                            replacer = replacer.replace("A", "A(");
                            replacer = replacer.replace("A", initalQ);
                            replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
                            replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
                            replacer = replacer.replace("+E", "E+");
                            // replacer = replacer.charAt(1)+replacer.substring(0,replacer.length()-1);
                            Productions ps2 = new Productions();
                            ps2.setLhsv(initalQ);
                            ps2.setRhsv(replacer);
                            subs3.set(i, ps2);
                        }
                    }
                }


            }


            msg += "\n New concated Equation 2 \n";
            for (int i = 0; i < subs3.size(); i++) {
                msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";

            }
            msg += "\n New Arden's Equation \n";
            for (int i = 0; i < subs3.size(); i++) {
                String ard = "E+" + subs3.get(i).getLhsv() + "(";
                for (int j = 0; j < subs.size(); j++) {
                    if (subs3.get(i).getRhsv().contains(ard) && !subs3.get(i).getRhsv().contains(subs.get(j))) {
                        Productions p = new Productions();
                        p.setLhsv(subs3.get(i).getLhsv());
                        String nvar = subs3.get(i).getRhsv();
                        nvar = nvar.replace("E+" + subs3.get(i).getLhsv(), "E");
                        nvar = nvar.replace(")", ")*");
                        nvar = nvar.replace("E", "");
                        p.setRhsv(nvar);
                        subs3.set(i, p);

                    } else {
                        for (int k = 0; k < lhsList.size(); k++) {
                            if (subs3.get(i).getRhsv().contains(lhsList.get(k)) && !lhsList.get(k).contains(initalQ)) {
                                //    for (int m = 0; m < eqlist.size(); m++) {
                                //  if (subs3.get(i).getRhsv().contains(lhsList.get(k))) {
                                msg+="\n we need to talk, if this doesnot work \n";
                                String check = substitutionVar(lhsList.get(k));
                                String reference = check.replace("+","/");
                                //String replacers = subs3.get(i).getRhsv().replace(eqlist.get(i).getLhsv(),check);
                                String replacers = subs3.get(i).getRhsv().replace(lhsList.get(k), reference);
                                Productions p = new Productions();
                                p.setLhsv(subs3.get(i).getLhsv());
                                p.setRhsv(replacers);
                                subs3.set(i, p);
                                //  }
                                //    }


                                /*
                                 * For sustitution from another equation is carried out here
                                 *
                                 * */
                            }
                        }
                    }
                    /*
                     *
                     *
                     * */
                }
            }

                // if rhs contain only one lhs value
            msg+="\n Checking for the rhs containing single lhs \n";
            /*for (int i = 0; i < subs3.size(); i++) {
                String[] check = subs3.get(i).getRhsv().split("\\+");
                for (int ck = 0; ck < check.length; ck++) {
                    int cks = check.length - 1;
                    if (ck != check.length - 1) {
                          if (subs3.get(i).getLhsv().matches(initalQ) && check[ck].contains(initalQ)){
                            if (ck!=0 && (ck+1)!= check.length){
                                if (!check[ck-1].contains(initalQ) || !check[ck+1].contains(initalQ)){
                                    String replac = subs3.get(i).getLhsv();
                                    String rec = subs3.get(i).getRhsv().replace(replac,"");
                                    rec = rec + "*";
                                    Productions psr = new Productions();
                                    psr.setLhsv(initalQ);
                                    psr.setRhsv(rec);
                                    subs3.set(i,psr);
                                }
                            }
                            else if(ck!=0 && (ck+1) == check.length){
                                if (!check[ck-1].contains(initalQ)){
                                    String replac = subs3.get(i).getLhsv();
                                    String rec = subs3.get(i).getRhsv().replace(replac,"");
                                    rec = rec + "*";
                                    Productions psr = new Productions();
                                    psr.setLhsv(initalQ);
                                    psr.setRhsv(rec);
                                    subs3.set(i,psr);
                                }
                            }

                        }
                    }
                }

                    }*/

            msg+= "\n Before the final Step original \n ";
            for (int i = 0; i < subs3.size(); i++) {
                msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";

            }

            if (subs3.get(0).getRhsv().contains(subs3.get(0).getLhsv())){
                finalsub.clear();
                finalsub(subs3.get(0).getLhsv());
//                String replacers = finalsub(subs3.get(0).getLhsv());
//                Productions psr = new Productions();
//                psr.setLhsv(subs3.get(0).getLhsv());
//                psr.setRhsv(replacers);
//                subs3.set(0,psr);


            }
            msg+= "\n The final Step original \n ";
            for (int i = 0; i < subs3.size(); i++) {
                msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";

            }


            msgs(msg);
        } else {
            msgs("There is no E on initial state. Please try again.");
        }
    }

    public void msgs(String msger) {

        ArdensRule.results.setText(msger);
        ArdensRule.results.setTextIsSelectable(true);
    }

    public void finalsub( String finalLhs){
        String frhs = "";
       // finalsub.clear();
        for (int i = 0 ; i <subs3.size();i++) {
            if (subs3.get(i).getLhsv().contains(finalLhs) && subs3.get(i).getRhsv().contains(finalLhs)) {

                    String[] finalsplit = subs3.get(i).getRhsv().split("\\+");
                    for (int j = 0; j < finalsplit.length; j++) {
                        Productions p = new Productions();
                        p.setLhsv(subs3.get(i).getLhsv());
                        p.setRhsv(finalsplit[j]);
                        finalsub.add(p);
                    }


            }
        }

        msg+= "\n The final sub function \n ";
        for (int i = 0; i < finalsub.size(); i++) {
            msg += finalsub.get(i).getLhsv() + " = " + finalsub.get(i).getRhsv() + "\n";

        }

        if (finalsub.size() > 0) {
            //the first step q1ab -> ab)

            int counters = 0;
            int looper = 0;
            int uniq = 0;
            for (int k = 0; k < finalsub.size(); k++) {
                int si = finalsub.size() - 1;
                if (k < finalsub.size() - 1) {
                    if (finalsub.get(k).getLhsv().matches(finalLhs) && finalsub.get(k + 1).getLhsv().matches(finalLhs)) {
                        if (finalsub.get(k).getRhsv().contains(finalLhs) && finalsub.get(k+1).getRhsv().contains(finalLhs)) {
                            looper = k;
                            counters++;
                        }

                    }
                } else {
                    if (finalsub.get(si).getLhsv().matches(finalLhs) && finalsub.get(si - 1).getLhsv().matches(finalLhs)) {
                        if (finalsub.get(si).getRhsv().contains(finalLhs) && finalsub.get(si - 1).getRhsv().contains(finalLhs)) {
                            looper = k;
                            counters++;
                        }
                    }
                }
            }

            if (counters != 0) {
                String replacer = finalsub.get(looper + 1).getRhsv() + ")";
              //  msg+="\n replacers = " + replacer;
                Productions ps = new Productions();
                ps.setLhsv(finalsub.get(looper + 1).getLhsv());
                ps.setRhsv(replacer);
                finalsub.set(looper + 1, ps);
            }


            //The second step  q1 - > q1(
            int suCount = 0;
            int ocount = 1 ;

            for (int k = 0; k < finalsub.size(); k++) {
                int fs = finalsub.size() - 1;
                if (k != finalsub.size() - 1) {
                    if (finalsub.get(k).getLhsv().contains(finalLhs) && finalsub.get(k + 1).getLhsv().contains(finalLhs)) {
                        if (finalsub.get(k).getRhsv().contains(finalLhs) && finalsub.get(k+1).getRhsv().contains(finalLhs))
                        suCount++;
                    } else if (finalsub.get(k).getLhsv().contains(finalLhs) && finalsub.get(k - 1).getLhsv().contains(finalLhs) &&
                            !finalsub.get(k + 1).getLhsv().contains(finalLhs)) {
                        if (finalsub.get(k).getRhsv().contains(finalLhs) && finalsub.get(k-1).getRhsv().contains(finalLhs))
                            suCount++;
                        else if (!finalsub.get(k).getRhsv().contains(finalLhs) && finalsub.get(k-1).getRhsv().contains(finalLhs))
                            ocount=0;
                        else if (finalsub.get(k).getRhsv().contains(finalLhs) && !finalsub.get(k-1).getRhsv().contains(finalLhs))
                            ocount=0;
                        //suCount++;
                    } else if (finalsub.get(k).getLhsv().contains(finalLhs) &&
                            !finalsub.get(k + 1).getLhsv().contains(finalLhs)) {
                        suCount = 0;
                    }
                } else {
                    if (finalsub.get(fs).getLhsv().contains(finalLhs) && finalsub.get(fs - 1).getLhsv().contains(finalLhs)) {
                        if (finalsub.get(fs).getRhsv().contains(finalLhs) && finalsub.get(fs-1).getRhsv().contains(finalLhs))
                            suCount++;
                        else if (finalsub.get(k).getRhsv().contains(finalLhs) && !finalsub.get(k+1).getRhsv().contains(finalLhs))
                            ocount=0;
                        else if (!finalsub.get(k).getRhsv().contains(finalLhs) && finalsub.get(k+1).getRhsv().contains(finalLhs))
                           ocount=0;
                       // suCount++;
                    } else {
                        suCount = 0;
                    }
                }

                if (suCount == 1) {
                    String replacer = finalsub.get(k).getRhsv().replace(finalLhs, finalLhs + '(');
                    Productions ps = new Productions();
                    ps.setLhsv(finalsub.get(k).getLhsv());
                    ps.setRhsv(replacer);
                    finalsub.set(k, ps);
                } else if (suCount > 1) {
                    String replacer = finalsub.get(k).getRhsv().replace(finalLhs, "");
                    Productions ps = new Productions();
                    ps.setLhsv(finalsub.get(k).getLhsv());
                    ps.setRhsv(replacer);
                    finalsub.set(k, ps);
                }
                if (ocount==0){
                    String replacer = finalsub.get(k).getRhsv().replace(finalLhs, "");
                    replacer = replacer + "*";
                    Productions ps = new Productions();
                    ps.setLhsv(finalsub.get(k).getLhsv());
                    ps.setRhsv(replacer);
                    finalsub.set(k, ps);
                }
            }

            String concat = "";
            for (int i = 0; i < finalsub.size(); i++) {
                if (finalsub.get(i).getLhsv().matches(finalLhs)) {

                    if (i == 0) {
                        concat += finalsub.get(i).getRhsv();
                    } else if (!finalsub.get(i).getLhsv().matches(finalsub.get(i - 1).getLhsv())) {
                        concat += finalsub.get(i).getRhsv();
                    } else {
                        concat += "+" + finalsub.get(i).getRhsv();
                    }


                }
            }

            for (int ks = 0; ks < subs3.size(); ks++) {
                if (subs3.get(ks).getLhsv().matches(finalLhs)) {
                    Productions ps = new Productions();
                    ps.setLhsv(finalLhs);
                    ps.setRhsv(concat);
                    subs3.set(ks, ps);
                }
            }

            //final conversion or repeat
            msg+= "\n The Before replacer function \n ";
            for (int i = 0; i < subs3.size(); i++) {
                msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";

            }

            String replacer = "";
         //   for (int i = 0; i < subs3.size(); i++) {
                        //replacer = subs3.get(i).getRhsv().replace(initalQ, "A");
                       // replacer = replacer.replace("+A", "+");
                     //   replacer = replacer.replace("A", "A(");
                      //  replacer = replacer.replace("A", initalQ);
                        replacer = subs3.get(0).getRhsv();
              //          msg+="\n " +  replacer + "\n";
                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
            //    msg+="\n " +  replacer + "\n";
                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
            //    msg+="\n " +  replacer + "\n";
                        replacer = replacer.replace("+E", "E+");

                        String checks = String.valueOf(replacer.charAt(0));
                        if(checks.matches("\\+")){
                            replacer = replacer.replace(String.valueOf(replacer.charAt(0)),"");
                        }
                        String checker = "+" + subs3.get(0).getLhsv();
                        if (!replacer.contains(checker)){
                            replacer = replacer.replace(subs3.get(0).getLhsv(),checker);

                        }

              //  msg+="\n " +  replacer + "\n";
                        // replacer = replacer.charAt(1)+replacer.substring(0,replacer.length()-1);
                        Productions ps2 = new Productions();
                        ps2.setLhsv(initalQ);
                        ps2.setRhsv(replacer);
                        subs3.set(0, ps2);




        //    }
            for (int i = 0; i < subs3.size(); i++) {
//            String checks = subs3.get(i).getLhsv() + "(";
//
//
                String ard = "E+" + subs3.get(i).getLhsv() + "(";
                for (int j = 0; j < subs.size(); j++) {
                    if (subs3.get(i).getRhsv().contains(ard) && !subs3.get(i).getRhsv().contains(subs.get(j))) {
                        Productions p = new Productions();
                        p.setLhsv(subs3.get(i).getLhsv());
                        String nvar = subs3.get(i).getRhsv();
                       // msg+="\n " +  nvar + "\n";
                        nvar = nvar.replace("E+" + subs3.get(i).getLhsv(), "E");
                     //   msg+="\n " +  nvar + "\n";
                        nvar = nvar.replace(")*", ")");
                        nvar = nvar.replace(")", ")*");
                    //    msg+="\n " +  nvar + "\n";
                        nvar = nvar.replace("E", "");
                    //    msg+="\n " +  nvar + "\n";
                        p.setRhsv(nvar);
                        subs3.set(i, p);
                        frhs = nvar;
                   //     return frhs;

                    } else {
                        for (int k = 0; k < lhsList.size(); k++) {
                            if (subs3.get(i).getRhsv().contains(lhsList.get(k)) && !lhsList.get(k).contains(initalQ)) {
                                //    for (int m = 0; m < eqlist.size(); m++) {
                                //  if (subs3.get(i).getRhsv().contains(lhsList.get(k))) {
                                msg+="\n calling from substitution bro \n";
                                String check = substitutionVar(lhsList.get(k));
                                String helper= check.replace("+" , "/");
                                //String replacers = subs3.get(i).getRhsv().replace(eqlist.get(i).getLhsv(),check);
                                String replacers = subs3.get(i).getRhsv().replace(lhsList.get(k), helper);
                                Productions p = new Productions();
                                p.setLhsv(subs3.get(i).getLhsv());
                                p.setRhsv(replacers);
                                subs3.set(i, p);
                                //  }
                                //    }


                                /*
                                 * For sustitution from another equation is carried out here
                                 *
                                 * */
                            }
                        }
                    }
                    /*
                     *
                     *
                     * */
                }


            }


            //return frhs;
        }
        for (int m = 0 ; m < subs3.size() ; m++){
            Productions ps = new Productions();
            ps.setLhsv(subs3.get(m).getLhsv());
            String check = subs3.get(m).getRhsv().replace("/","+");
            ps.setRhsv(check);
            subs3.set(m,ps);
        }

      //  return frhs;
    }



    public String substitutionVar(String lhsExpressions) {
        String subs = "";
        // if equation got second variable E. need to use Ardens theorem and substitute





        for (int i = 0; i < subs2.size(); i++) {
            for (int j = 0; j < eqlist.size(); j++) {
                if (eqlist.get(j).getLhsv().matches(lhsExpressions)) {
                    if (eqlist.get(j).getRhsv().contains(subs2.get(i).getLhsv())) {
                        String mains = eqlist.get(j).getRhsv();
                        String replacer = subs2.get(i).getLhsv();
                        String news = subs2.get(i).getRhsv();
                        String replace = mains.replace(replacer, news);
                        Productions ps = new Productions();
                        ps.setLhsv(eqlist.get(j).getLhsv());
                        ps.setRhsv(replace);
                        eqlist.set(j, ps);

                    }
                }

            }
        }

        //firstly concatination the strings;
        int bcount = 0;
        int bbcounter = 0;
        for (int i = 0; i < eqlist.size(); i++) {
            int len = eqlist.size() - 1;
            if (i != eqlist.size() - 1) {
                if (eqlist.get(i).getLhsv().matches(lhsExpressions) && eqlist.get(i + 1).getLhsv().matches(lhsExpressions)) {
                    if (eqlist.get(i).getRhsv().contains(lhsExpressions) && eqlist.get(i + 1).getRhsv().contains(lhsExpressions)) {
                        bcount = i;
                        bbcounter++;
//                    Productions p = new Productions();
//                    p.setLhsv(eqlist.get(i).getLhsv());
//                    String concat2 = eqlist.get(i + 1).getRhsv() + ")";
//                    p.setRhsv(concat2);
//                    eqlist.set(i + 1, p);
                        //msg += "\n concated " + concat + "\n";
                    }
                }
                // subs3.add(i,p);
            } else {
                if (eqlist.get(len - 1).getLhsv().matches(lhsExpressions) && eqlist.get(len).getLhsv().matches(lhsExpressions)) {
                    if (eqlist.get(len - 1).getRhsv().contains(lhsExpressions) && eqlist.get(len).getRhsv().contains(lhsExpressions)) {
                        bcount = i;
                        bbcounter++;
//                        Productions p = new Productions();
//                        p.setLhsv(eqlist.get(i).getLhsv());
//                        String concat2 = eqlist.get(i + 1).getRhsv() + ")";
//                        p.setRhsv(concat2);
//                        eqlist.set(i + 1, p);
                        //msg += "\n concated " + concat + "\n";
                    }
                }

            }

        }
        if ( bcount != 0)  {
            Productions p = new Productions();
            p.setLhsv(eqlist.get(bcount).getLhsv());
            String concat2 = eqlist.get(bcount + 1).getRhsv() + ")";
            p.setRhsv(concat2);
            eqlist.set(bcount + 1, p);
        }

        int rhsCount = 0;
        for (int i = 0; i < eqlist.size(); i++) {
            if (eqlist.get(i).getLhsv().matches(lhsExpressions)) {
                if (i != eqlist.size() - 1) {
                    if (eqlist.get(i).getRhsv().contains(lhsExpressions) && eqlist.get(i + 1).getRhsv().contains(lhsExpressions)) {
                        rhsCount++;

                    }
                    else if(eqlist.get(i).getRhsv().contains(lhsExpressions) && !eqlist.get(i + 1).getRhsv().contains(lhsExpressions) && eqlist.get(i-1).getRhsv().contains(lhsExpressions) ){
                        rhsCount++;
                    }
                    else if (eqlist.get(i).getRhsv().contains(lhsExpressions) && !eqlist.get(i + 1).getRhsv().contains(lhsExpressions)) {
                        rhsCount=0;
                    }
                    if (rhsCount == 1) {
                        String lhse = eqlist.get(i).getLhsv();

                        String checking = eqlist.get(i).getRhsv().replace(eqlist.get(i).getLhsv(), lhse + "(");

                        Productions p = new Productions();
                        p.setLhsv(lhse);
                        p.setRhsv(checking);
                        eqlist.set(i, p);

                    } else if (rhsCount > 1) {
                        String lhse = eqlist.get(i).getLhsv();

                        String checking = eqlist.get(i).getRhsv().replace(eqlist.get(i).getLhsv(), "");

                        Productions p = new Productions();
                        p.setLhsv(lhse);
                        p.setRhsv(checking);
                        eqlist.set(i, p);
                    }
                }

            }
        }

        String concat = "";
        for (int i = 0; i < eqlist.size(); i++) {
            if (eqlist.get(i).getLhsv().matches(lhsExpressions)) {

                if (i == 0) {
                    concat += eqlist.get(i).getRhsv();
                } else if (!eqlist.get(i).getLhsv().matches(eqlist.get(i - 1).getLhsv())) {
                    concat += eqlist.get(i).getRhsv();
                } else {
                    concat += "+" + eqlist.get(i).getRhsv();
                }


            }
        }
        Productions ps = new Productions();
        ps.setLhsv(lhsExpressions);
        ps.setRhsv(concat);
        subs3.add(ps);


        msg += "\n New concated Equation 1 inside subtitution \n";
        for (int i = 0; i < subs3.size(); i++) {
            msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";
        }

        //substituting and using Arden's formula

//
//        String replacer = "";
//        for (int i = 0; i < subs3.size(); i++) {
//            String[] check = subs3.get(i).getRhsv().split("\\+");
//            for (int ck = 0; ck < check.length; ck++) {
//                if (ck != check.length - 1) {
//                    if (check[ck].contains(lhsExpressions) && check[ck + 1].contains(lhsExpressions)) {
//                        replacer = subs3.get(i).getRhsv().replace(lhsExpressions, "A");
//                        replacer = replacer.replace("+A", "+");
//                        replacer = replacer.replace("A", "A(");
//                        replacer = replacer.replace("A", lhsExpressions);
//                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
//                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
//                        replacer = replacer.replace("+E", "E+");
//                        // replacer = replacer.charAt(1)+replacer.substring(0,replacer.length()-1);
//                        Productions ps2 = new Productions();
//                        ps2.setLhsv(lhsExpressions);
//                        ps2.setRhsv(replacer);
//                        subs3.set(i, ps2);
//                    }
//                } else {
//                    if (check[check.length - 2].contains(lhsExpressions) && check[check.length - 1].contains(lhsExpressions)) {
//                        replacer = subs3.get(i).getRhsv().replace(lhsExpressions, "A");
//                        replacer = replacer.replace("+A", "+");
//                        replacer = replacer.replace("A", "A(");
//                        replacer = replacer.replace("A", lhsExpressions);
//                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
//                        replacer = replacer.charAt(replacer.length() - 1) + replacer.substring(0, replacer.length() - 1);
//                        replacer = replacer.replace("+E", "E+");
//                        // replacer = replacer.charAt(1)+replacer.substring(0,replacer.length()-1);
//                        Productions ps2 = new Productions();
//                        ps2.setLhsv(lhsExpressions);
//                        ps2.setRhsv(replacer);
//                        subs3.set(i, ps2);
//                    }
//                }
//            }
//
//
//        }


        msg += "\n New concated Equation 2  inside substitution\n";
        for (int i = 0; i < subs3.size(); i++) {
            msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";
            String check = "+" + subs3.get(i).getLhsv() + "(";
            if (subs3.get(i).getRhsv().contains(check)) {
                String news = subs3.get(i).getRhsv().replace(check, "(");
                news = news.replace(")", ")*");
                Productions p = new Productions();
                p.setLhsv(subs3.get(i).getLhsv());
                p.setRhsv(news);
                subs3.set(i, p);
                subs = news;
                return subs;
            }


        }


//        msg += "\n New Arden's Equation \n";
//        for (int i = 0; i < subs3.size(); i++) {
//            String ard = "E+" + subs3.get(i).getLhsv() + "(";
//            for (int j = 0; j < subs.size(); j++) {
//                if (subs3.get(i).getRhsv().contains(ard) && !subs3.get(i).getRhsv().contains(subs.get(j))) {
//                    Productions p = new Productions();
//                    p.setLhsv(subs3.get(i).getLhsv());
//                    String nvar = subs3.get(i).getRhsv();
//                    nvar = nvar.replace("E+" + subs3.get(i).getLhsv(), "E");
//                    nvar = nvar.replace(")", ")*");
//                    nvar = nvar.replace("E", "");
//                    p.setRhsv(nvar);
//                    subs3.set(i, p);
//
//                } else {
//                    for (int k = 0; k < lhsList.size(); k++) {
//                        if (subs3.get(i).getRhsv().contains(lhsList.get(k)) && !lhsList.get(k).contains(lhsExpressions) && !lhsList.get(k).contains(initalQ)) {
//                            for (int m = 0; m < eqlist.size(); m++) {
//                                if (eqlist.get(m).getLhsv().contains(lhsList.get(k))) {
//                                    //substitutionVar(lhsList.get(k));
//                                    msg+="\n Checking the benfits \n";
//                                    msgs(msg);
//        String check = substitutionVar(lhsList.get(k));
//        String replacers = subs3.get(i).getRhsv().replace(eqlist.get(m).getLhsv(),check);
//        Productions p = new Productions();
//        p.setLhsv(subs3.get(i).getLhsv());
//        p.setRhsv(replacers);
//        subs3.set(i,p);
//                                }
//                            }
//
//
//                            /*
//                             * For sustitution from another equation is carried out here
//                             *
//                             * */
//                        }
//                    }
//                }
//                /*
//                 *
//                 *
//                 * */
//            }
//        }
        for (int i = 0; i < subs3.size(); i++) {
            msg += subs3.get(i).getLhsv() + " = " + subs3.get(i).getRhsv() + "\n";

        }


        msgs(msg);
        return subs;
    }

    public void firstSubsList(List<Productions> eqlist) {
        for (int j = 0; j < eqlist.size(); j++) {
            lhsList.add(eqlist.get(j).getLhsv());
        }
        for (int k = 0; k < lhsList.size(); k++) {
            int ck = 0;
            for (int l = 0; l < lhsList.size(); l++) {
                if (lhsList.get(k).matches(lhsList.get(l))) {
                    ck++;
                    if (ck > 1) {
                        lhsList.remove(l);
                        ck--;
                        l--;
                    }
                }
            }
        }
        for (int i = 0; i < eqlist.size(); i++) {
            msg += eqlist.get(i).getLhsv() + " = " + eqlist.get(i).getRhsv() + "\n";
        }
        msg += "LHS \n";
        for (int s = 0; s < lhsList.size(); s++) {
            msg += lhsList.get(s) + "\n";
        }
        /*if (!lhsList.get(lhsList.size() - 1).matches(eqlist.get(eqlist.size() - 2).getLhsv())) {
            //   msg+="\n This is for last and semi last item " +
            //          lhsList.get(lhsList.size()-1) + "!=" +  eqlist.get(eqlist.size()-2).getLhsv()
            //          + " does not match \n";
            subs.add(lhsList.get(lhsList.size() - 1));
            subs2.add(eqlist.get(eqlist.size() - 1));
        }*/
        for (int m = 0; m < lhsList.size(); m++) {
            int lk = 0;
            for (int n = 0; n < eqlist.size(); n++) {
                if (lhsList.get(m).matches(eqlist.get(n).getLhsv())) {
                    lk++;
                    //  msg+="New idea " + lhsList.get(m) + " Lost SomeWhere lk = "+lk + " \n";
//                    if (lk == 2 ) {
//                        if (m != lhsList.size()-1) {
//                            m++;
//                            lk = 0;
//                        }
//                    }
                } else {

                    if (lk == 1) {
                        // msg+="New idea " + lhsList.get(m) + " This came only once. \n";
                        // msgs(msg);
                        subs.add(lhsList.get(m));
                        subs2.add(eqlist.get(n - 1));
                    }
                    lk=0;
                }
            }
        }

        msg += "\n For 1st step substitution \n";
        for (int s = 0; s < subs2.size(); s++) {
            msg += subs2.get(s).getLhsv() + " = " + subs2.get(s).getRhsv() + "\n";
        }

        msgs(msg);
    }


}
