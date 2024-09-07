package ru.etu.merchstoreLETI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.etu.merchstoreLETI.domain.enums.OrderStatus;
import ru.etu.merchstoreLETI.domain.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "OrderDtoWithProducts")
public class OrderDtoWithProducts {
    @Schema(description = "Order id",example = "1")
    private Long orderId;
    private List<ProductDto> products;
    @Schema(description = "Order address",example = "ул Пушкина")
    private String address;
    @Schema(description = "Order status",example = "PROCESSING")
    private OrderStatus status;
    @Schema(description = "Order comment",example = "Побыстрее!")
    private String comment;
    @Schema(description = "Order deliveryPrice",example = "1000")
    private BigDecimal deliveryPrice;
    private LocalDateTime createDate;
}
