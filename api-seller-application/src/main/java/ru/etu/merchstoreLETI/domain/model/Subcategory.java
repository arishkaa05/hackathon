package ru.etu.merchstoreLETI.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subcategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    /**
     * '@Id' - аннотация JPA для указания, что поле "id" является идентификатором.
     * '@GeneratedValue' - аннотация JPA для указания автоматической генерации значения идентификатора.
     * '@Column' - аннотация JPA для указания имени столбца и ограничений на поле - не может быть null.
     * Поле для хранения идентификатора подкатегории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * '@Column' - аннотация JPA для указания имени столбца и ограничений на поле - не может быть null, поле уникально.
     * Поле для хранения названия подкатегории
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * '@Column' - аннотация JPA для указания имени столбца и ограничений на поле - не может быть null, поле уникально.
     * Поле для хранения названия в url
     */
    @Column(name = "title_url", nullable = false, unique = true)
    private String titleUrl;

    /**
     * '@ManyToMany' - аннотация JPA для указания отношения "многие ко многим".
     * '@JoinTable' - аннотация JPA для указания имени промежуточной таблицы, которая связывает продукты и подкатегории.
     * joinColumns = @JoinColumn(name = "category_id") - аннотация JPA для указания имени столбца, связывающего подкатегорию
     * с промежуточной таблицей.
     * inverseJoinColumns = @JoinColumn(name = "product_id")) - аннотация JPA для указания имени столбца, связывающего
     * продукт с промежуточной таблицей.
     */
    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "subcategories_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;

    /**
     * Поле для хранения общего количества продуктов в подкатегории.
     * '@Column' - аннотация JPA для указания имени столбца и ограничений на поле - может повторяться, не уникально
     */
    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    /**
     * Поле для хранения описания подкатегории.
     * '@Column' - аннотация JPA для указания имени столбца и ограничений на поле - поле может быть не уникальным, повторяться.
     */
    @Column(name = "description", nullable = true)
    private String description;

    /**
     * '@Column' - аннотация JPA для указания имени столбца в базе данных, связанного с полем "image".
     * Поле "image" предназначено для хранения изображения категории.
     * Колонка "image" может содержать значение null (nullable = true), что означает, что поле может быть пустым.
     */
    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @ManyToMany(mappedBy = "subcategories")
    private Set<Category> categories;
}
