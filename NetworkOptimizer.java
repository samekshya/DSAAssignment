//Question no 5
// Description: Implements a network optimizer using a GUI.
// Allows adding nodes (servers/clients) and edges (connections) with costs.
// Provides options to optimize network and find shortest paths.

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

// Represents a node (server or client) in the network
 class Node {
    int id;
    int x, y;
    
    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

// Represents a connection (edge) between two nodes with an associated cost
class Edge {
    Node src, dest;
    int cost;
    
    public Edge(Node src, Node dest, int cost) {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
    }
}

// Graph structure to manage nodes and edges
class Graph {
    private final Map<Integer, Node> nodes = new HashMap<>(); // Stores nodes by ID
    private final List<Edge> edges = new ArrayList<>(); // Stores edges with costs
    private final Random rand = new Random();
    
    public void addNode(int id) {
        // Randomly place the node on the screen
        if (!nodes.containsKey(id)) {
            int x = rand.nextInt(400) + 50;
            int y = rand.nextInt(400) + 50;
            nodes.put(id, new Node(id, x, y));
        }
    }
    
    public void addEdge(int src, int dest, int cost) {
        // Add an edge only if both nodes exist
        if (nodes.containsKey(src) && nodes.containsKey(dest)) {
            edges.add(new Edge(nodes.get(src), nodes.get(dest), cost));
        }
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
    public Collection<Node> getNodes() {
        return nodes.values();
    }
    
    public void optimizeNetwork() {
        // Sort edges by cost (similar to Kruskal’s MST approach)
        edges.sort(Comparator.comparingInt(e -> e.cost));
    }
    
    public List<Integer> shortestPath(int src, int dest) {
        // Implements Dijkstra's algorithm for shortest path calculation
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> predecessors = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        
        // Initialize distances
        for (int node : nodes.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(src, 0);
        queue.add(src);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Edge edge : edges) {
                if (edge.src.id == current) {
                    int newDist = distances.get(current) + edge.cost;
                    if (newDist < distances.get(edge.dest.id)) {
                        distances.put(edge.dest.id, newDist);
                        predecessors.put(edge.dest.id, current);
                        queue.add(edge.dest.id);
                    }
                }
            }
        }
        
        // Construct shortest path from predecessors
        List<Integer> path = new ArrayList<>();
        for (Integer at = dest; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}

// GUI Application for Network Optimization
public class NetworkOptimizer extends JFrame {
    private final Graph graph = new Graph(); // Graph instance
    private final JButton addNodeButton, addEdgeButton, optimizeButton, shortestPathButton;
    private final GraphPanel graphPanel;

    public NetworkOptimizer() {
        setTitle("Network Optimizer");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel controlPanel = new JPanel(); // Control buttons panel
        addNodeButton = new JButton("Add Node");
        addEdgeButton = new JButton("Add Edge");
        optimizeButton = new JButton("Optimize Network");
        shortestPathButton = new JButton("Find Shortest Path");
        
        controlPanel.add(addNodeButton);
        controlPanel.add(addEdgeButton);
        controlPanel.add(optimizeButton);
        controlPanel.add(shortestPathButton);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        graphPanel = new GraphPanel(); // Graph visualization panel
        add(graphPanel, BorderLayout.CENTER);
        
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());
    }
    
    private void addNode() {
        // User inputs node ID
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter node ID:"));
        graph.addNode(id);
        graphPanel.repaint();
    }
    
    private void addEdge() {
        // Ensure at least two nodes exist before adding an edge
        if (graph.getNodes().size() < 2) {
            JOptionPane.showMessageDialog(this, "At least two nodes are required to add an edge.");
            return;
        }
        int src = Integer.parseInt(JOptionPane.showInputDialog("Enter source node ID:"));
        int dest = Integer.parseInt(JOptionPane.showInputDialog("Enter destination node ID:"));
        int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter cost:"));
        graph.addEdge(src, dest, cost);
        graphPanel.repaint();
    }
    
    private void optimizeNetwork() {
        graph.optimizeNetwork();
        JOptionPane.showMessageDialog(this, "Network optimized based on cost.");
        graphPanel.repaint();
    }
    
    private void findShortestPath() {
        int src = Integer.parseInt(JOptionPane.showInputDialog("Enter source node ID:"));
        int dest = Integer.parseInt(JOptionPane.showInputDialog("Enter destination node ID:"));
        List<Integer> path = graph.shortestPath(src, dest);
        
        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No path found between the selected nodes.");
        } else {
            JOptionPane.showMessageDialog(this, "Shortest path: " + path);
        }
        graphPanel.repaint();
    }
    
    // Custom panel for drawing the graph
    class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            for (Edge edge : graph.getEdges()) {
                g.drawLine(edge.src.x, edge.src.y, edge.dest.x, edge.dest.y);
                int midX = (edge.src.x + edge.dest.x) / 2;
                int midY = (edge.src.y + edge.dest.y) / 2;
                g.drawString(String.valueOf(edge.cost), midX, midY);
            }
            
            for (Node node : graph.getNodes()) {
                g.fillOval(node.x - 10, node.y - 10, 20, 20);
                g.drawString(String.valueOf(node.id), node.x - 5, node.y - 15);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkOptimizer().setVisible(true));
    }
}
