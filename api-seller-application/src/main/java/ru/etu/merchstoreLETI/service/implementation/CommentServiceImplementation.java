package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.repository.CommentRepository;
import ru.etu.merchstoreLETI.repository.ProductRepository;
import ru.etu.merchstoreLETI.repository.UserRepository;
import ru.etu.merchstoreLETI.service.interf.CommentService;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.ProductService;
import ru.etu.merchstoreLETI.util.CommentDtoUtil;
import ru.etu.merchstoreLETI.util.CommentDtoWithUsernameUtil;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;
import ru.etu.merchstoreLETI.web.dto.CommentDto;
import ru.etu.merchstoreLETI.web.dto.CommentDtoWithUsername;
import ru.etu.merchstoreLETI.web.dto.ProductDto;
import ru.etu.merchstoreLETI.web.dto.SubcategoryDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation extends AbstractCRUDService<CommentDto, Long, Comment> implements CommentService, PaginationService<CommentDto, Long> {
    private final CommentRepository commentRepository;
    private final CommentDtoUtil commentDtoUtil;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentDtoWithUsernameUtil commentDtoWithUsernameUtil;

    @Override
    JpaRepository<Comment, Long> getRepository() {
        return commentRepository;
    }

    @Override
    EntityDtoUtil<CommentDto, Comment> getEntityDtoUtil() {
        return commentDtoUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комментарий не найден!"));
        CommentDto commentDto = new CommentDto();
        commentDto.setProductId(comment.getProduct().getId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setId(comment.getId());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setRating(comment.getRating());
        return commentDto;
    }

    @Override
    @Transactional
    public CommentDto updateEntity(CommentDto objectDto) {
        Comment existingComment = commentRepository.findById(objectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Комментарий не найден!"));
        existingComment.setMessage(objectDto.getMessage());
        Product product = productRepository.findById(objectDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден!"));
        existingComment.setProduct(product);
        User user = userRepository.findById(objectDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден!"));
        existingComment.setUser(user);
        existingComment.setRating(objectDto.getRating());
        CommentDto commentDto = new CommentDto();
        commentDto.setMessage(existingComment.getMessage());
        commentDto.setId(existingComment.getId());
        commentDto.setUserId(existingComment.getUser().getId());
        commentDto.setProductId(existingComment.getProduct().getId());
        commentDto.setRating(existingComment.getRating());
        return commentDto;
    }

    @Override
    @Transactional
    public void createEntity(CommentDto objectDto) {
        Comment newComment = new Comment();
        User user = userRepository.findById(objectDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с данным id: " + objectDto.getUserId() + " не найден"));
        newComment.setUser(user);
        newComment.setCreateDate(LocalDateTime.now());
        newComment.setMessage(objectDto.getMessage());
        newComment.setRating(objectDto.getRating());
        Product product = productRepository.findById(objectDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт с данным id: " + objectDto.getProductId() + " не найден!"));
        newComment.setProduct(product);
        commentRepository.save(newComment);
    }

    @Override
    public List<CommentDto> findAll() {
        List<Comment> comments = getRepository().findAll();
        return convertToDto(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        if (searchKey.equals("")) {
            Page<Comment> comments = getRepository().findAll(pageable);
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : comments) {
                CommentDto commentDto = getEntityDtoUtil().toDto(comment);
                commentDto.setProductId(comment.getProduct().getId());
                commentDto.setUserId(comment.getUser().getId());
                commentDtos.add(commentDto);
            }
            return commentDtos;
        } else {
            List<Comment> comments = commentRepository.findByProductContainingIgnoreCaseOrMessageContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
            return convertToDto(comments);
        }
    }

    @Override
    public List<CommentDto> getCommentsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Продукт с данным id: "+productId+ " не существует!"));
        List<Comment> comments = commentRepository.findByProduct(product);
        return convertToDto(comments);
    }

    @Override
    public List<CommentDto> getCommentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Пользователь с данным id: "+userId+ " не существует!"));
        List<Comment> comments = commentRepository.findByUser(user);
        return convertToDto(comments);
    }

    @Override
    public CommentDto updateCommentRating(Long id, Integer newRating) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setRating(newRating);
            comment = commentRepository.save(comment);
            CommentDto commentDto = commentDtoUtil.toDto(comment);
            commentDto.setProductId(comment.getProduct().getId());
            commentDto.setUserId(comment.getUser().getId());
            return commentDto;
        } else {
            throw  new ResourceNotFoundException("Данный комментарий не найден!");
        }
    }

    @Override
    public List<CommentDto> searchComments(Integer minRating, Integer maxRating, LocalDateTime startDate, LocalDateTime endDate) {
        List<Comment> matchingComments = commentRepository.searchComments(minRating, maxRating, startDate, endDate);
        return convertToDto(matchingComments);
    }

    @Override
    public List<CommentDtoWithUsername> getCommentsByProductWithUser(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Продукт с данным id: "+productId+ " не существует!"));
        List<Comment> comments = commentRepository.findByProduct(product);
        List<CommentDtoWithUsername> commentDtoWithUsernames = commentDtoWithUsernameUtil.toDtoList(comments);
        return commentDtoWithUsernames;
    }

    private List<CommentDto> convertToDto(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment: comments){
            CommentDto commentDto = commentDtoUtil.toDto(comment);
            commentDto.setProductId(comment.getProduct().getId());
            commentDto.setUserId(comment.getUser().getId());
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
}
