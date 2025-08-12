package keyin.datastructure.datastructure_sprint_api.user.dto;

public record UserResponse(
        Long id,
        String username,
        String profileImagePath
) {}