package com.gourui.knowledgebase.utils.graph;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.HashSet;
import java.util.Set;

public class DirectedGraphAdapter<N, V> {
    private MutableValueGraph<N, V> graph;

    public DirectedGraphAdapter() {
        graph = ValueGraphBuilder.directed().allowsSelfLoops(false).build();
    }

    public Set<N> getAllAccessibleSuccessors(N node) {
        Set<N> notVisitedNodes = new HashSet<>(graph.nodes());
        Set<N> accessibleSuccessors = new HashSet<>();
        //递归入口
        findSuccessorsByOneNode(node, notVisitedNodes, accessibleSuccessors);
        return accessibleSuccessors;
    }

    private void findSuccessorsByOneNode(N node, Set<N> notVisitedNodes, Set<N> accessibleSuccessors) {
        Set<N> successors = graph.successors(node);
        for ( N successor : successors) {
            if (notVisitedNodes.contains(successor)){
                accessibleSuccessors.add(successor);
                notVisitedNodes.remove(successor);
                //递归寻找所有的可达节点
                findSuccessorsByOneNode(successor, notVisitedNodes, accessibleSuccessors);
            }
        }
    }

    public Set<N> getAllAccessiblePredecessors(N node) {
        Set<N> notVisitedNodes = new HashSet<>(graph.nodes());
        Set<N> accessiblePredecessors = new HashSet<>();
        //递归入口
        findPredecessorsByOneNode(node, notVisitedNodes, accessiblePredecessors);
        return accessiblePredecessors;
    }

    private void findPredecessorsByOneNode(N node, Set<N> notVisitedNodes, Set<N> accessiblePredecessors) {
        Set<N> predecessors = graph.predecessors(node);
        for ( N predecessor : predecessors) {
            if (notVisitedNodes.contains(predecessor)){
                accessiblePredecessors.add(predecessor);
                notVisitedNodes.remove(predecessor);
                //递归寻找所有的可达节点
                findPredecessorsByOneNode(predecessor, notVisitedNodes, accessiblePredecessors);
            }
        }
    }

    public Set<N> getStartNodes() {
        Set<N> startNodes = new HashSet<>();
        for(N node : graph.nodes()) {
            if(graph.inDegree(node) == 0) { //入度为0
                startNodes.add(node);
            }
        }
        return startNodes;
    }

    public Set<N> getEndNodes() {
        Set<N> endNodes = new HashSet<>();
        for(N node : graph.nodes()) {
            //出度为0
            if(graph.outDegree(node) == 0) {
                endNodes.add(node);
            }
        }
        return endNodes;
    }

    public Set<N> getRelayNodes() {
        Set<N> relayNodes = new HashSet<>();
        for(N node : graph.nodes()) {
            //出度入度都大于0
            if(graph.outDegree(node) > 0 && graph.inDegree(node) > 0) {
                relayNodes.add(node);
            }
        }
        return relayNodes;
    }

    public void putEdgeValue(N var1, N var2, V var3) {
        graph.putEdgeValue(var1,var2,var3);
    }
}
