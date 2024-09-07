package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "FavoriteItemDto")
public class FavoriteItemDto {
    @Schema(description = "FavoriteItem Id",example = "1")
    private Long id;
    @Schema(description = "FavoriteItem userId",example = "1")
    private Long userId;
    private List<ProductDto> productDtoList;

}
