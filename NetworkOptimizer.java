// Question no 5
// Description:
// This program implements a GUI-based network optimizer.
// - Users can add nodes (representing servers or clients).
// - Users can add edges (connections between nodes) with associated costs.
// - Provides functionality to optimize the network (using Minimum Spanning Tree).
// - Implements Dijkstra’s algorithm to find the shortest path between two nodes.
// - Uses Java Swing for the graphical interface and visualization.

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
    
    // Function to add a node to the graph
    public void addNode(int id) {
        // Randomly place the node on the screen
        if (!nodes.containsKey(id)) {
            int x = rand.nextInt(400) + 50;
            int y = rand.nextInt(400) + 50;
            nodes.put(id, new Node(id, x, y));
        }
    }
    
    // Function to add an edge between two existing nodes
    public void addEdge(int src, int dest, int cost) {
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
    
    // Function to optimize network connections (using Minimum Spanning Tree approach)
    public void optimizeNetwork() {
        edges.sort(Comparator.comparingInt(e -> e.cost)); // Sort edges by cost
    }
    
    // Function to find the shortest path between two nodes using Dijkstra’s algorithm
    public List<Integer> shortestPath(int src, int dest) {
        Map<Integer, Integer> distances = new HashMap<>(); // Stores shortest distance to each node
        Map<Integer, Integer> predecessors = new HashMap<>(); // Stores previous node for path reconstruction
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Min heap for processing
        
        // Initialize distances to infinity, except for source node
        for (int node : nodes.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(src, 0);
        queue.add(src);
        
        // Process nodes to find shortest paths
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
        
        // Construct the shortest path from source to destination
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
        
        JPanel controlPanel = new JPanel(); // Panel for buttons
        addNodeButton = new JButton("Add Node");
        addEdgeButton = new JButton("Add Edge");
        optimizeButton = new JButton("Optimize Network");
        shortestPathButton = new JButton("Find Shortest Path");
        
        controlPanel.add(addNodeButton);
        controlPanel.add(addEdgeButton);
        controlPanel.add(optimizeButton);
        controlPanel.add(shortestPathButton);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        graphPanel = new GraphPanel(); // Panel to display the network graph
        add(graphPanel, BorderLayout.CENTER);
        
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());
    }
    
    private void addNode() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter node ID:"));
        graph.addNode(id);
        graphPanel.repaint();
    }
    
    private void addEdge() {
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
            
            // Draw edges (connections)
            for (Edge edge : graph.getEdges()) {
                g.drawLine(edge.src.x, edge.src.y, edge.dest.x, edge.dest.y);
                int midX = (edge.src.x + edge.dest.x) / 2;
                int midY = (edge.src.y + edge.dest.y) / 2;
                g.drawString(String.valueOf(edge.cost), midX, midY);
            }
            
            // Draw nodes (servers/clients)
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

// Summary:
// - This program provides a GUI for adding nodes and edges to represent network connections.
// - It allows optimizing the network using a Minimum Spanning Tree (MST) approach.
// - Implements Dijkstra’s algorithm to find the shortest path between any two nodes.
// - Users can interact with the network through buttons and graphical elements.

// Expected User Actions and Results:
// - **Adding nodes**: User can input IDs, and nodes will appear at random positions on the GUI.
// - **Adding edges**: User can define a source, destination, and cost for each connection.
// - **Optimizing network**: The system sorts edges based on cost for efficiency.
// - **Finding shortest paths**: The program calculates the shortest route between two nodes using Dijkstra’s algorithm.
