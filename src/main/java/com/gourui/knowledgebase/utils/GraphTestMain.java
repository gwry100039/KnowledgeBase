package com.gourui.knowledgebase.utils;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.HashSet;
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


        Set<String> successors = getAllAccessibleSuccessors("C",graph);
        for (String successor : successors) {
            System.out.println(successor);
        }

        Set<String> predecessors = getAllAccessiblePredecessors("E",graph);
        for (String predecessor : predecessors) {
            System.out.println(predecessor);
        }
    }

    public static <T, N> Set<T> getAllAccessibleSuccessors(T node, MutableValueGraph<T, N> graph) {
        Set<T> notVisitedNodes = new HashSet<>(graph.nodes());
        Set<T> accessibleSuccessors = new HashSet<>();
        //递归入口
        findSuccessorsByOneNode(node, graph, notVisitedNodes, accessibleSuccessors);
        return accessibleSuccessors;
    }

    private static <T, N> void findSuccessorsByOneNode(T node, MutableValueGraph<T, N> graph, Set<T> notVisitedNodes, Set<T> accessibleSuccessors) {
        Set<T> successors = graph.successors(node);
        for ( T successor : successors) {
            if (notVisitedNodes.contains(successor)){
                accessibleSuccessors.add(successor);
                notVisitedNodes.remove(successor);
                //递归寻找所有的可达节点
                findSuccessorsByOneNode(successor, graph, notVisitedNodes, accessibleSuccessors);
            }
        }
    }

    public static <T, N> Set<T> getAllAccessiblePredecessors(T node, MutableValueGraph<T, N> graph) {
        Set<T> notVisitedNodes = new HashSet<>(graph.nodes());
        Set<T> accessiblePredecessors = new HashSet<>();
        //递归入口
        findPredecessorsByOneNode(node, graph, notVisitedNodes, accessiblePredecessors);
        return accessiblePredecessors;
    }

    private static <T, N> void findPredecessorsByOneNode(T node, MutableValueGraph<T, N> graph, Set<T> notVisitedNodes, Set<T> accessiblePredecessors) {
        Set<T> predecessors = graph.predecessors(node);
        for ( T predecessor : predecessors) {
            if (notVisitedNodes.contains(predecessor)){
                accessiblePredecessors.add(predecessor);
                notVisitedNodes.remove(predecessor);
                //递归寻找所有的可达节点
                findPredecessorsByOneNode(predecessor, graph, notVisitedNodes, accessiblePredecessors);
            }
        }
    }
}
