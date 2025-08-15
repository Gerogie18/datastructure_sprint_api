package keyin.datastructure.datastructure_sprint_api.tree.DTO;

import java.util.List;

public record CreateTreeRequest(
        Long userId,
        List<Integer> numbers
) {}
