package com.gourui.knowledgebase.utils;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTestMain {
    public static void main(String[] args) {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(false).build();

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


        Set<String> notVisitedNodes = new HashSet<>(graph.nodes());
        Set<String> successors = graph.successors(D);
        List<Set<String>> succList = new ArrayList<>();
        succList.add(successors);
        while ( succList.size() > 0 ) {
            for (String successor : succList.get(0)) {
                System.out.println(successor);
                Set<String> successorsA = graph.successors(successor);
                if(successorsA.size() > 0) {
                    succList.add(successorsA);
                }
            }
            succList.remove(0);
        }

//        Set<String> predecessors = graph.predecessors(D);
//        for (String predecessor : predecessors) {
//            System.out.println(predecessor);
//        }

        graph.edges();
        graph.nodes();
    }
}
