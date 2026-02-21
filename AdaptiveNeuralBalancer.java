import java.util.*;

class Server {
    private String serverId;
    private double performanceScore;
    private int tasksHandled;
    private Random random;

    public Server(String serverId) {
        this.serverId = serverId;
        this.performanceScore = 1.0; // Initial neutral score
        this.tasksHandled = 0;
        this.random = new Random();
    }

    public double processTask(int complexity) {
        // Simulated execution time (random factor + complexity)
        double executionTime = (random.nextDouble() * 2 + 1) * complexity;

        // Update learning score (lower execution time = better performance)
        performanceScore = (performanceScore + (1 / executionTime)) / 2;
        tasksHandled++;

        return executionTime;
    }

    public double getPerformanceScore() {
        return performanceScore;
    }

    public String getServerId() {
        return serverId;
    }

    public int getTasksHandled() {
        return tasksHandled;
    }
}

class NeuralLoadBalancer {
    private List<Server> servers;
    private Random random;

    public NeuralLoadBalancer(int numberOfServers) {
        servers = new ArrayList<>();
        random = new Random();
        for (int i = 1; i <= numberOfServers; i++) {
            servers.add(new Server("Server-" + i));
        }
    }

    public void assignTask(int complexity) {
        Server bestServer = selectBestServer();
        double time = bestServer.processTask(complexity);

        System.out.println("Task (Complexity: " + complexity + ") assigned to " 
                + bestServer.getServerId() + 
                " | Execution Time: " + String.format("%.2f", time));
    }

    private Server selectBestServer() {
        return servers.stream()
                .max(Comparator.comparing(Server::getPerformanceScore))
                .orElse(servers.get(0));
    }

    public void showServerStats() {
        System.out.println("\n=== Server Performance Report ===");
        for (Server server : servers) {
            System.out.println(server.getServerId() +
                    " | Score: " + String.format("%.4f", server.getPerformanceScore()) +
                    " | Tasks: " + server.getTasksHandled());
        }
    }
}

public class AdaptiveNeuralBalancer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NeuralLoadBalancer balancer = new NeuralLoadBalancer(3);

        System.out.println("=== Adaptive Neural Load Balancer Simulation ===");

        for (int i = 1; i <= 10; i++) {
            int complexity = new Random().nextInt(5) + 1;
            balancer.assignTask(complexity);
        }

        balancer.showServerStats();
        scanner.close();
    }
}