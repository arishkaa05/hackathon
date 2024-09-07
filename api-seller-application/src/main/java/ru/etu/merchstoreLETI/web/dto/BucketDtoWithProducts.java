package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "BucketDtoWithProducts")
public class BucketDtoWithProducts {
    @Schema(description = "Bucket id",example = "1")
    private Long id;
    private List<ProductDto> productDtos;
    private Long userId;
}
