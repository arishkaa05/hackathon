package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CommentDto")
public class CommentDto {
    @Schema(description = "Comment Id",example = "1")
    private Long id;
    @Schema(description = "Comment productId",example = "1")
    private Long productId;
    @Schema(description = "Comment userId",example = "1")
    private Long userId;
    @Schema(description = "Comment message",example = "Хороший продукт!")
    private String message;
    @Schema(description = "Comment rating",example = "5")
    private Integer rating;
}
