package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CategoryDtoWithProducts")
public class CategoryDtoWithProducts {
    @Schema(description = "Category Id",example = "1")
    private Long id;
    @Schema(description = "Category Title",example = "Кружки с надписью ЛЭТИ")
    private String title;
    @Schema(description = "Category Title URL",example = "Mag")
    private String titleUrl;
    private List<SubcategoryDto> subcategoryDtos;
    @Schema(description = "Category description",example = "Классные кружки для вас")
    private String description;
    @Schema(description = "Category imageUrl",example = "image1.png")
    private String imageUrl;
    private List<ProductDto> productDtos;
}
