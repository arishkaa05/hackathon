package ru.etu.merchstoreLETI.service.interf;

import org.springframework.web.bind.annotation.RequestParam;
import ru.etu.merchstoreLETI.web.dto.CommentDto;
import ru.etu.merchstoreLETI.web.dto.CommentDtoWithUsername;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService extends CRUDService<CommentDto,Long>,PaginationService<CommentDto,Long> {
    List<CommentDto> getCommentsByProduct(Long productId);

    List<CommentDto> getCommentsByUser(Long userId);
    CommentDto updateCommentRating(Long id, Integer newRating);

    List<CommentDto> searchComments(Integer minRating,
                                    Integer maxRating,
                                    LocalDateTime startDate,
                                    LocalDateTime endDate);

    List<CommentDtoWithUsername> getCommentsByProductWithUser(Long productId);
}
