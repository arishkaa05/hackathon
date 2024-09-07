package ru.etu.merchstoreLETI.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * Уникальный идентификатор категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Название категории
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * Название категории для frontend
     */
    @Column(name = "title_url", nullable = false, unique = true)
    private String titleUrl;
    /**
     * Список подкатегорий, связанных с данной категорией
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "categories_subcategories",
            joinColumns = @JoinColumn(name = "categories_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategories_id"))
    private List<Subcategory> subcategories;

    /**
     * Описание категории.
     */
    @Column(name = "description", nullable = true)
    private String description;

    /**
     * Изображение, связанное с категорией
     */
    @Column(name = "image_url", nullable = true)
    private String imageUrl;

}
