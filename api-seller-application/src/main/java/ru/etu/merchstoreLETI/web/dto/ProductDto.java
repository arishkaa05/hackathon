package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.enums.SizeEnum;
import ru.etu.merchstoreLETI.domain.model.Comment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ProductDto")
public class ProductDto {
    @Schema(description = "Product id", example = "id")
    private Long id;
    @Schema(description = "Product title", example = "Футболка фирменная LETI")
    private String title;
    @Schema(description = "Product old price", example = "1400.00")
    private BigDecimal oldPrice;
    @Schema(description = "Product: new product?", example = "true")
    private Boolean newProduct;
    @Schema(description = "Product: hot product?", example = "false")
    private Boolean hotProduct;
    @Schema(description = "Product price", example = "1200.00")
    private BigDecimal price;
    @Schema(description = "Product description", example = "Данная футболка не марается, не ломается, лучше всех и вся")
    private String description;
    @Schema(description = "Product quantity", example = "199")
    private Integer quantity;
    @Schema(description = "Product weight", example = "0.12")
    private Double weight;
    @Schema(description = "Product imageUrl", example = "image2.png")
    private String imageUrl;
    @Schema(description = "Product title for url frontend", example = "TShirt")
    private String titleForUrl;
    @Schema(description = "Product size", example = "M")
    private SizeEnum size;

}
