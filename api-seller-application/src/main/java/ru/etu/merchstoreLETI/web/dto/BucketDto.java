package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "BucketDto")
public class BucketDto {
    @Schema(description = "Bucket id",example = "1")
    private Long id;
    private List<Long> productIds;
    private Long userId;
}
