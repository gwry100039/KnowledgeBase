package com.gourui.knowledgebase.utils;

import com.gourui.knowledgebase.utils.graph.DirectedGraphAdapter;

import java.util.Set;

public class GraphTestMain {
    public static void main(String[] args) {
        DirectedGraphAdapter<String, Integer> graph = new DirectedGraphAdapter<>();

        String D = "D";
        graph.putEdgeValue("A", "B", 10);
        graph.putEdgeValue("A", "C", 3);
        graph.putEdgeValue("A", D, 20);
        graph.putEdgeValue("B", D, 5);
        graph.putEdgeValue("C", "B", 2);
        graph.putEdgeValue("C", "E", 15);
        graph.putEdgeValue(D, "E", 11);
        graph.putEdgeValue("E", "F", 13);
        graph.putEdgeValue("E", "G", 13);
        graph.putEdgeValue("F", "H", 13);


        Set<String> successors = graph.getAllAccessibleSuccessors("C");
        System.out.println("C节点的所有后继节点：");
        System.out.println(successors);

        Set<String> predecessors = graph.getAllAccessiblePredecessors("E");
        System.out.println("E节点的所有前驱节点：");
        System.out.println(predecessors);
    }
}
