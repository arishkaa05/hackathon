package ru.etu.merchstoreLETI.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    /**
     * Поле "id" является уникальным идентификатором комментария. Оно генерируется автоматически при вставке в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Поле "product" является ассоциацией многие-к-одному с сущностью "Product". Оно связывает комментарий с продуктом,
     * к которому он относится.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    /**
     * Поле "user" является ассоциацией многие-к-одному с сущностью "User". Оно связывает комментарий с пользователем,
     * к которому он относится.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * Поле "message" представляет текст комментария. Оно является обязательным полем и имеет тип "text", что позволяет
     * хранить длинные тексты.
     */
    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    /**
     * Поле "createDate" представляет дату и время создания комментария. Значение этого поля устанавливается автоматически
     * перед сохранением комментария в базу данных с использованием аннотации @PrePersist.
     */
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;


    /**
     * Аннотация @UpdateTimestamp указывает на автоматическое обновление значения поля "change". При каждом изменении
     * объекта в базе данных. Значение будет представлено в виде объекта LocalDateTime. @Column - поле будет отображаться в базе
     * данных как столбец с именем "change", оно не может быть пустым (содержать значение null).
     */
    @UpdateTimestamp
    @Column(name = "update_date",nullable = false)
    private LocalDateTime updateDate;

    /**
     * Поле "rating" представляет рейтинг комментария. Значение этого поля устанавливается
     * в базу данных.
     */
    @Column(name = "rating",nullable = false)
    private Integer rating;

    /**
     * Аннотация @PrePersist указывает на метод, который будет выполнен перед сохранением сущности в базу данных.
     * Метод "onCreated()" устанавливает текущую дату и время в поле "createDate" перед сохранением комментария в базу данных.
     */
    @PrePersist
    protected void onCreated() {
        this.createDate = LocalDateTime.now();
    }
}
