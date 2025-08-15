package keyin.datastructure.datastructure_sprint_api.tree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BinaryTreeService {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Builds a Binary Search Tree from a list of numbers.
     * @param numbers The list of integers to insert.
     * @return The root node of the created tree.
     */
    public BinaryNode buildTreeFromNumbers(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }

        BinaryNode root = null;
        for (int number : numbers) {
            root = insert(root, number);
        }
        return root;
    }

    /**
     * A private helper method that recursively finds the correct spot
     * for a new value and inserts a new node there.
     */
    private BinaryNode insert(BinaryNode currentNode, int value) {
        if (currentNode == null) {
            return new BinaryNode(value);
        }

        if (value <= currentNode.value) {
            currentNode.left = insert(currentNode.left, value);
        } else {
            currentNode.right = insert(currentNode.right, value);
        }

        return currentNode;
    }

    /**
     * Converts a tree structure into a JSON string using Jackson.
     * @param root The root node of the tree.
     * @return A JSON string representation of the tree.
     */
    public String serializeTreeToJson(BinaryNode root) {
        try {
            // Use the ObjectMapper to write the object as a string
            return objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            // In case of a serialization error, throw a runtime exception
            throw new RuntimeException("Error converting tree to JSON", e);
        }
    }
}