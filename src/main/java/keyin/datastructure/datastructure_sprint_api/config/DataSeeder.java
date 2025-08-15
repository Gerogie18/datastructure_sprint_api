package keyin.datastructure.datastructure_sprint_api.config;

import keyin.datastructure.datastructure_sprint_api.tree.TreeService;
import keyin.datastructure.datastructure_sprint_api.user.User;
import keyin.datastructure.datastructure_sprint_api.user.UserRepository;
import keyin.datastructure.datastructure_sprint_api.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TreeService treeService;

    @Override
    public void run(String... args) throws Exception {
        // Only seed data if the database is empty
        if (userRepository.count() == 0) {
            System.out.println("Database is empty. Seeding initial data...");
            seedData();
        } else {
            System.out.println("Database is not empty. Skipping seeder.");
        }
    }

    private void seedData() {
        // 1. Create 4 users
        User user1 = userService.createUser("Susan");
        User user2 = userService.createUser("RiffRaff");
        User user3 = userService.createUser("Magenta");
        User user4 = userService.createUser("Frank-N-Furter");

        List<User> users = List.of(user1, user2, user3, user4);

        // 2. For each user, create the skewed trees
        for (User user : users) {
            System.out.println("Seeding skewed trees for user: " + user.getName());
            for (int height = 3; height <= 6; height++) {
                List<Integer> skewedNumbers = generateSkewedTreeNumbers(height);
                treeService.createTree(skewedNumbers, user.getId());
            }

            // 3. For each user, also create one perfect tree
            System.out.println("Seeding one perfect tree for user: " + user.getName());
            List<Integer> balancedNumbers = generateBalancedTreeNumbers(3);
            treeService.createTree(balancedNumbers, user.getId());
        }
        System.out.println("Data seeding complete.");
    }


    /**
     * Helper method to generate a list of numbers that will produce a
     * skewed "stick" tree of a specific height.
     * @param height The desired height of the tree.
     * @return A list of sequential integers (e.g., [1, 2, 3] for height 3).
     */
    private List<Integer> generateSkewedTreeNumbers(int height) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            numbers.add(i);
        }
        return numbers;

    }

    /**
     * Helper method to generate a list of numbers that will produce a
     * perfect, balanced binary tree of a specific height.
     * @param height The desired height of the tree (e.g., height 3 has 15 nodes).
     * @return A list of numbers in an order that creates a balanced tree.
     */
    private List<Integer> generateBalancedTreeNumbers(int height) {
        // Calculate the total number of nodes in a perfect tree of a given height
        int totalNodes = (int) Math.pow(2, height + 1) - 1;
        List<Integer> numbers = new ArrayList<>();
        addBalancedNode(1, totalNodes, numbers);
        return numbers;
    }

    // A recursive helper to build the balanced insertion list
    private void addBalancedNode(int start, int end, List<Integer> numbers) {
        if (start > end) {
            return;
        }
        int mid = (start + end) / 2;
        numbers.add(mid); // Add the middle element first (the root of the subtree)
        addBalancedNode(start, mid - 1, numbers); // Recurse on the left half
        addBalancedNode(mid + 1, end, numbers);   // Recurse on the right half
    }
}
