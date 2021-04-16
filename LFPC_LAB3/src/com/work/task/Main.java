package com.work.task;

import java.util.*;

// Variant 16. Ana Petranis FAF-191

public class Main {

//    Stage 1 - Eliminate Epsilon

    public static void Stage1 (HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys){

        for (int i = 0; i < keys.size(); i++) {
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++) {

                if (grammar.get(keys.get(i)).get(j).equals("e")) { //replace non-terminals in productions if they derive to epsilon

                    for (int m = 0; m < keys.size(); m++) {
                        for (int n = 0; n < grammar.get(keys.get(m)).size(); n++) {

                            if (grammar.get(keys.get(m)).get(n).contains(keys.get(i))) {
                                grammar.get(keys.get(m)).add(grammar.get(keys.get(m)).get(n).replace(keys.get(i), ""));
                            }
                        }
                    }

                    grammar.get(keys.get(i)).remove(j); //remove epsilon itself
                }
            }
        }
    }

//    Stage 2 - Eliminate Any Renaming

    public static void Stage2 (HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys){

        Stage1(grammar, keys);

        for (int i = 0; i < keys.size(); i++){
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){

                for (int k = 0; k < keys.size(); k++){ // if the production has form A -> B, than B is replaced with its productions
                    if ((grammar.get(keys.get(i)).get(j).length() == 1) && (grammar.get(keys.get(i)).get(j).equals(keys.get(k)))){

                        for (int l = 0; l < grammar.get(keys.get(k)).size(); l++){
                            grammar.get(keys.get(i)).add(grammar.get(keys.get(k)).get(l));
                        }

                        grammar.get(keys.get(i)).remove(j);
                    }
                }
            }
        }
    }

//    Stage 3 - Eliminate The Inaccessible Symbols

    public static void Stage3(HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys){

        Stage2(grammar, keys);

        for (int k = 0; k < keys.size(); k++){

            int repetitions = 0;

            for (int i = 0; i < keys.size(); i++){
                for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){

                    if (grammar.get(keys.get(i)).get(j).contains(keys.get(k))){ // count the number of repetitions of the given non-terminal in the RHS
                        repetitions++;
                    }
                }
            }

            if (repetitions == 0){ // if the number of repetitions is equal to 0, then the symbol is inaccessible and it should be eliminated
                grammar.remove(keys.get(k));
            }
        }
    }

//    Stage 4 - Change The Terminal Symbols In The Productions Longer Than 1 Symbol

    public static void Stage4 (HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, ArrayList<String> terminals){

        Stage3(grammar, keys);

        for (int i = 0; i < terminals.size(); i++){
            String newkey = "X";
            newkey = newkey.concat(String.valueOf(i+1));

            keys.add(newkey);

            ArrayList<String> newarray = new ArrayList<>();
            newarray.add(terminals.get(i));

            grammar.put(newkey, newarray);

            for (int l = 0; l < grammar.size(); l++){
                try{
                    for (int j = 0; j < grammar.get(keys.get(l)).size(); j++){
                        if (grammar.get(keys.get(l)).get(j).length() > 1){ // if the productions has form  A -> ab, than terminals should be changed on the non-terminals
                            String replacement = null;                     // so that productions will have form A -> X1X2      X1 -> a      X2 -> b

                            replacement = grammar.get(keys.get(l)).get(j).replaceAll(terminals.get(i), newkey);

                            grammar.get(keys.get(l)).set(j, replacement);
                        }
                    }
                } catch (NullPointerException e){

                }
            }
        }

        for (int i = 0; i < keys.size()-terminals.size(); i++){
            try {
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){
                    for (int k = j + 1; k < grammar.get(keys.get(i)).size(); k++) {
                        if (grammar.get(keys.get(i)).get(j).equals(grammar.get(keys.get(i)).get(k))) {
                            grammar.get(keys.get(i)).remove(k);
                        }
                    }
                }
            }catch (NullPointerException e){

            }
        }
    }


//    Stage 5 - Finalize CNF

    public static void CNF (HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, ArrayList<String> terminals){

        Stage4(grammar, keys, terminals);

        int n = 0;

//        if the productions have form A -> BX1X2..., then minimize the number of non-ternimals in productions up to 2
//        so that productions will have form A -> BY1   Y1 -> X1X2
        for (int i = 0; i <= grammar.size(); i++){
            try {
                for (int j = 0; j < grammar.get(keys.get(i)).size(); j++) {

                    int numberOfNonTerminals = 0;
                    int k = 0;

                    while (k < grammar.get(keys.get(i)).get(j).length()) {
                        if (grammar.get(keys.get(i)).get(j).charAt(k) == 'X') {
                            numberOfNonTerminals++;
                            k += 2;
                        } else if (grammar.get(keys.get(i)).get(j).charAt(k) != 'Y'){
                            numberOfNonTerminals++;
                            k++;
                        }
                    }

                    if (numberOfNonTerminals > 2) {

                        String firstNonTerminal = null;
                        String secondNonTerminal = grammar.get(keys.get(i)).get(j);

                        if (secondNonTerminal.charAt(0) == 'X' || secondNonTerminal.charAt(0) == 'Y'){
                            firstNonTerminal = String.valueOf(secondNonTerminal.charAt(0)) + String.valueOf(secondNonTerminal.charAt(1));
                            secondNonTerminal = secondNonTerminal.replaceFirst(firstNonTerminal, "");
                        } else {
                            firstNonTerminal = String.valueOf(secondNonTerminal.charAt(0));
                            secondNonTerminal = secondNonTerminal.replaceFirst(firstNonTerminal, "");
                        }

                        String foundedKey = null;
                        int q = 0;
                        for (int b = 0; b < grammar.size(); b++){
                            try{
                                for (int d = 0; d < grammar.get(keys.get(b)).size(); d++){
                                    if (grammar.get(keys.get(b)).get(d).equals(secondNonTerminal)){
                                        q++;
                                        foundedKey = keys.get(b);
                                    }
                                }
                            } catch (NullPointerException e){

                            }
                        }

                        String replacement = null;

                        if (q == 0){
                            String newkey = "Y";
                            newkey = newkey.concat(String.valueOf(n+1));

                            keys.add(newkey);

                            ArrayList<String> newarray = new ArrayList<>();
                            newarray.add(secondNonTerminal);

                            grammar.put(newkey, newarray);

                            replacement = grammar.get(keys.get(i)).get(j).replace(secondNonTerminal, newkey);
                            grammar.get(keys.get(i)).set(j, replacement);

                            n++;
                        } else {
                            replacement = grammar.get(keys.get(i)).get(j).replace(secondNonTerminal, foundedKey);
                            grammar.get(keys.get(i)).set(j,replacement);
                        }
                    }
                }
            } catch (NullPointerException e){

            }
        }
    }

    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> grammar = new HashMap<String, ArrayList<String>>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> terminals = new ArrayList<>();
        ArrayList<String> productionsS = new ArrayList<>();
        ArrayList<String> productionsA = new ArrayList<>();
        ArrayList<String> productionsB = new ArrayList<>();
        ArrayList<String> productionsC = new ArrayList<>();

        keys.add("S");
        keys.add("A");
        keys.add("B");
        keys.add("C");

        terminals.add("a");
        terminals.add("b");

        productionsS.add("abAB");
        grammar.put(keys.get(0), productionsS);

        productionsA.add("aSab");
        productionsA.add("BS");
        productionsA.add("aA");
        productionsA.add("b");
        grammar.put(keys.get(1), productionsA);

        productionsB.add("BA");
        productionsB.add("ababB");
        productionsB.add("b");
        productionsB.add("e");
        grammar.put(keys.get(2), productionsB);

        productionsC.add("AS");
        grammar.put(keys.get(3), productionsC);

        System.out.println("Input grammar: " + grammar.entrySet());

        CNF(grammar, keys, terminals);

        System.out.println("Grammar in Chomsky Normal Form: " + grammar.toString());
    }
}
