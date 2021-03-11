package com.laboratories.lfpc;

import java.util.ArrayList;

public class Graph{

    public int size = 10;
    public String[][] matrix;
    ArrayList<Node> nodes;
    private int i;

    public Graph(){
        nodes = new ArrayList<>();
        matrix = new String[size][size];
    }

    public void addNode(String name){
        nodes.add(new Node(name));
    }

    public void addEdge(int nrState, int nrInput, String state){
        matrix[nrState][nrInput] = state;
    }
}
