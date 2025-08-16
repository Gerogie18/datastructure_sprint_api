package keyin.datastructure.datastructure_sprint_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateMockData {

    // --- Simple Data Classes (to replace @Entity models) ---
    public static class User {
        public long id;
        public String name;
        public User(long id, String name) { this.id = id; this.name = name; }
    }

    public static class TreeRecord {
        public long id;
        public String inputNumbers;
        public String jsonTree;
        public long userId;
        public TreeRecord(long id, String inputNumbers, String jsonTree, long userId) {
            this.id = id; this.inputNumbers = inputNumbers; this.jsonTree = jsonTree; this.userId = userId;
        }
    }

    public static class BinaryNode {
        public int value;
        public BinaryNode left;
        public BinaryNode right;
        public BinaryNode(int value) { this.value = value; }
    }

    // --- Main Method to Run the Script ---
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        List<User> users = new ArrayList<>();
        List<TreeRecord> treeRecords = new ArrayList<>();
        long treeIdCounter = 1;

        // --- 1. Replicate User Creation ---
        users.add(new User(1L, "Susan"));
        users.add(new User(2L, "RiffRaff"));
        users.add(new User(3L, "Magenta"));
        users.add(new User(4L, "Frank-N-Furter"));

        // --- 2. Replicate Tree Creation ---
        for (User user : users) {
            // Skewed trees
            for (int height = 3; height <= 6; height++) {
                List<Integer> numbers = generateSkewedTreeNumbers(height);
                String jsonTree = buildAndSerializeTree(numbers, mapper);
                String inputStr = numbers.toString().replace("[", "").replace("]", "");
                treeRecords.add(new TreeRecord(treeIdCounter++, inputStr, jsonTree, user.id));
            }
            // Perfect tree
            List<Integer> balancedNumbers = generateBalancedTreeNumbers(3);
            String jsonTree = buildAndSerializeTree(balancedNumbers, mapper);
            String inputStr = balancedNumbers.toString().replace("[", "").replace("]", "");
            treeRecords.add(new TreeRecord(treeIdCounter++, inputStr, jsonTree, user.id));
        }

        // --- 3. Create the final map for json-server ---
        Map<String, Object> mockDb = new HashMap<>();
        mockDb.put("users", users);
        mockDb.put("trees", treeRecords);

        // --- 4. Write to db.json file ---
        mapper.writeValue(new File("db.json"), mockDb);
        System.out.println("Successfully created db.json with " + users.size() + " users and " + treeRecords.size() + " trees.");
    }

    // --- Tree Building Logic ---
    private static String buildAndSerializeTree(List<Integer> numbers, ObjectMapper mapper) throws IOException {
        if (numbers == null || numbers.isEmpty()) return "{}";
        BinaryNode root = null;
        for (int number : numbers) {
            root = insert(root, number);
        }
        return mapper.writeValueAsString(root);
    }

    private static BinaryNode insert(BinaryNode currentNode, int value) {
        if (currentNode == null) return new BinaryNode(value);
        if (value <= currentNode.value) {
            currentNode.left = insert(currentNode.left, value);
        } else {
            currentNode.right = insert(currentNode.right, value);
        }
        return currentNode;
    }

    // --- Helper Methods from DataSeeder ---
    private static List<Integer> generateSkewedTreeNumbers(int height) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    private static List<Integer> generateBalancedTreeNumbers(int height) {
        int totalNodes = (int) Math.pow(2, height + 1) - 1;
        List<Integer> numbers = new ArrayList<>();
        addBalancedNode(1, totalNodes, numbers);
        return numbers;
    }

    private static void addBalancedNode(int start, int end, List<Integer> numbers) {
        if (start > end) return;
        int mid = (start + end) / 2;
        numbers.add(mid);
        addBalancedNode(start, mid - 1, numbers);
        addBalancedNode(mid + 1, end, numbers);
    }
}
