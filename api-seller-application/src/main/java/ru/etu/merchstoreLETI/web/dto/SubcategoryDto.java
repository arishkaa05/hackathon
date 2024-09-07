package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.model.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SubcategoryDto")
public class SubcategoryDto {
    @Schema(description = "Subcategory id",example = "1")
    private Long id;
    @Schema(description = "Subcategory title",example = "Футболки")
    private String title;
    @Schema(description = "Subcategory title url",example = "TShirt")
    private String titleUrl;
    private List<Long> productIds;
    @Schema(description = "Subcategory quantity",example = "123")
    private Integer quantity;
    @Schema(description = "Subcategory description",example = "Хлопковые футболки")
    private String description;
    @Schema(description = "Subcategory imageUrl",example = "image3.png")
    private String imageUrl;
}
