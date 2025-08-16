package keyin.datastructure.datastructure_sprint_api.tree;

import keyin.datastructure.datastructure_sprint_api.tree.TreeRecordService;
import keyin.datastructure.datastructure_sprint_api.tree.DTO.CreateTreeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeRecordController {

    @Autowired
    private TreeService treeService;

    @Autowired TreeRecordService treeRecordService;

    /**
     * Gets all tree records from the entire table.
     * This corresponds to the GET /api/trees/all endpoint.
     */
    @GetMapping("/all")
    public ResponseEntity<List<TreeRecord>> getAllTrees() {
        List<TreeRecord> records = treeRecordService.getAllTrees();
        return ResponseEntity.ok(records);
    }

    /**
     * Creates a new binary search tree for a given user.
     * This fulfills the "/process-numbers" requirement.
     * URL: POST /api/trees
     */
    @PostMapping
    public ResponseEntity<TreeRecord> createTree(@RequestBody CreateTreeRequest request) {
        TreeRecord newRecord = treeService.createTree(request.numbers(), request.userId());
        return ResponseEntity.ok(newRecord);
    }

    /**
     * Gets all previous trees for a specific user.
     * This fulfills the "/previous-trees" requirement.
     * URL: GET /api/trees/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TreeRecord>> getTreesByUser(@PathVariable Long userId) {
        List<TreeRecord> records = treeService.getTreesForUser(userId);
        return ResponseEntity.ok(records);
    }
}
