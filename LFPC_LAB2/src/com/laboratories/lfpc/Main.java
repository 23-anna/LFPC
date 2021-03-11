package com.laboratories.lfpc;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

//      NFA CONSTRUCTION

        Graph AF = new Graph();

        AF.addNode("q0");
        AF.addNode("q1");
        AF.addNode("q2");
        AF.addNode("q3");

        AF.addEdge(0,0,"q1");
        AF.addEdge(0,1,"q0");
        AF.addEdge(1,0,"null");
        AF.addEdge(1,1,"q1,q2");
        AF.addEdge(2,0,"q2");
        AF.addEdge(2,1,"q3");
        AF.addEdge(3,0,"null");
        AF.addEdge(3,1,"null");

        String initialState = "q0";
        String[] finalStates = {"q3"};

//      PRINT NFA
        System.out.println("\nNFA:");
        System.out.printf("%-3s %-5s  %-8s   %-8s", " ", " ", "a", "b");
        System.out.println();
        for (int i = 0; i < AF.nodes.size(); i++){
            if (AF.nodes.get(i).name.equals(initialState)){
                System.out.printf("%-3s", "->");
                System.out.printf("%-5s", AF.nodes.get(i).name);
            } else {
                for (int k = 0; k < finalStates.length; k++) {
                    if (AF.nodes.get(i).name.equals(finalStates[k])) {
                        System.out.printf("%-3s", "*");
                        System.out.printf("%-5s", AF.nodes.get(i).name);
                    } else {
                        System.out.printf("%-3s%-5s", "", AF.nodes.get(i).name);
                    }
                }
            }
            for (int j = 0; j < 2; j++){
                System.out.printf("%-3s%-8s", "", AF.matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println();
//-----------------------------------------------------------------------------

//      DFA CONSTRUCTION
        Graph DFA = new Graph();

        DFA.addNode("q0");
        DFA.addNode("q1");
        DFA.addNode("q2");
        DFA.addNode("q3");
        DFA.addNode("Death State");

        for (int i = 0; i < DFA.nodes.size(); i++){

            if (DFA.nodes.get(i).name.equals("Death State")){
                DFA.addEdge(i, 0, "Death State");
                DFA.addEdge(i, 1, "Death State");
                continue;
            }

            for (int j = 0; j < 2; j++){
                DFA.addEdge(i, j, AF.matrix[i][j]);
                if (DFA.matrix[i][j].equals("null")){
                    DFA.matrix[i][j] = "Death State";
                }
            }
        }

        int i = 0;
        int j = 0;
        int n = 0;

        while (i != DFA.nodes.size()) {
            for (j = 0; j < 2; j++) {
                for (int k = 0; k < DFA.nodes.size(); k++){
                    if (DFA.nodes.get(k).name.equals(DFA.matrix[i][j])){
                        n++;
                    }
                }
                if (n == 0){

//                  GENERAL LOGICS
                    DFA.addNode(DFA.matrix[i][j]);
                    String node = DFA.nodes.get(DFA.nodes.size()-1).name;
                    String[] nodeSplit;
                    nodeSplit = node.split(",");


                    for (int m = 0; m < nodeSplit.length; m++) {
                        for (int l = 0; l < DFA.nodes.size(); l++){
                            if (DFA.nodes.get(l).name.equals(nodeSplit[m])){

                                if (!(DFA.matrix[l][0].equals("Death State"))) {
                                    DFA.matrix[DFA.nodes.size()-1][0] = DFA.matrix[DFA.nodes.size() - 1][0] + "," + DFA.matrix[l][0];
                                }

                                if (!(DFA.matrix[l][1].equals("Death State"))) {
                                    DFA.matrix[DFA.nodes.size()-1][1] = DFA.matrix[DFA.nodes.size()-1][1] + "," + DFA.matrix[l][1];
                                }
                            }
                        }
                    }

                    DFA.matrix[DFA.nodes.size()-1][0] = DFA.matrix[DFA.nodes.size()-1][0].replace("null,", "");
                    DFA.matrix[DFA.nodes.size()-1][1] = DFA.matrix[DFA.nodes.size()-1][1].replace("null,", "");
                }
                n = 0;
            }
            i++;
        }

//      PRINT DFA
        System.out.println();
        System.out.println("DFA:");
        System.out.printf("%-15s   %-15s%-15s", "", "a", "b");
        System.out.println();
        for (i = 0; i < DFA.nodes.size(); i++){
            if (DFA.nodes.get(i).name.equals(initialState)){
                System.out.printf("%-3s", "->");
                System.out.printf("%-15s", DFA.nodes.get(i).name);
            } else {
                for (int k = 0; k < finalStates.length; k++) {
                    if (DFA.nodes.get(i).name.contains(finalStates[k])) {
                        System.out.printf("%-3s", "*");
                        System.out.printf("%-15s", DFA.nodes.get(i).name);
                    } else {
                        System.out.printf("%-3s%-15s", "", DFA.nodes.get(i).name);
                    }
                }
            }
            for (j = 0; j < 2; j++){
                System.out.printf("%-15s", DFA.matrix[i][j]);
            }
            System.out.println();
        }

    }
}
