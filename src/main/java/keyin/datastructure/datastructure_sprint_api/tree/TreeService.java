package keyin.datastructure.datastructure_sprint_api.tree;

import keyin.datastructure.datastructure_sprint_api.exception.ResourceNotFoundException;
import keyin.datastructure.datastructure_sprint_api.user.User;
import keyin.datastructure.datastructure_sprint_api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreeService {

    @Autowired
    private TreeRecordRepository treeRecordRepository;

    @Autowired
    private UserRepository userRepository; // Now needed to find the user by ID

    @Autowired
    private BinaryTreeService binaryTreeService;

    /**
     * Creates a tree and associates it with a user by their ID.
     */
    public TreeRecord createTree(List<Integer> numbers, Long userId) {
        // 1. Find the user by the provided ID. Throw an exception if not found.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Build the tree structure in-memory
        BinaryNode rootNode = binaryTreeService.buildTreeFromNumbers(numbers);

        // 3. Serialize the tree to a JSON string
        String jsonTree = binaryTreeService.serializeTreeToJson(rootNode);

        // 4. Format the input numbers into a simple string
        String inputNumbers = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        // 5. Create and save the record
        TreeRecord newRecord = new TreeRecord();
        newRecord.setInputNumbers(inputNumbers);
        newRecord.setJsonTree(jsonTree);
        newRecord.setUser(user); // Associate with the fetched user

        return treeRecordRepository.save(newRecord);
    }

    /**
     * Retrieves all tree records for a specific user by their ID.
     */
    public List<TreeRecord> getTreesForUser(Long userId) {
        // 1. Find the user by the provided ID.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Use the repository to find all records associated with that user object.
        return treeRecordRepository.findAllByUser(user);
    }
}