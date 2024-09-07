package ru.etu.merchstoreLETI.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import ru.etu.merchstoreLETI.domain.enums.SizeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    /**
     * `id` - это первичный ключ сущности, генерируемый автоматически с помощью `@GeneratedValue`.
     * Используется стратегия "identity", что означает, что база данных автоматически будет генерировать и присваивать
     * уникальные значения для поля `id`.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * '@Column'- аннотация указывает, что это поле соответствует столбцу базы данных с именем "likes".
     * Атрибут nullable указывает, что этот столбец позволяет значения null.
     */
    @Column(name = "likes", nullable = true)
    private Integer likes;

    /**
     * Это поле представляет набор имен пользователей, которые лайкнули определенную сущность, такую как продукт.
     * '@Column' - эта аннотация указывает, что это поле соответствует столбцу базы данных с именем "liked_users".
     * Аннотация @ElementCollection используется для обозначения того, что это поле представляет коллекцию простых значений.
     * Атрибут targetClass указывает тип класса элементов в коллекции, в данном случае - String.
     * Инициализируется как HashSet.
     */
    @Column(name = "liked_users")
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers;

    /**
     * `@Column` указывает, что поле `title` в базе данных будет соответствовать столбцу с именем "title".
     * Этот столбец должен быть обязательным (не может быть пустым) и уникальным (в нём не может быть повторяющихся значений).
     * '@NotEmpty` - означает, что поле `title` должно быть заполнено (не может быть пустым).
     * В случае, если поле оказывается пустым, будет сгенерировано сообщение ошибки "Название не должно быть пустым!"
     */
    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty(message = "Название не должно быть пустым!")
    private String title;

    /**
     * `@Column` указывает, что поле `titleForUrl` в базе данных будет соответствовать столбцу с именем "title_for_url".
     * Этот столбец должен быть обязательным (не может быть пустым) и уникальным (в нём не может быть повторяющихся значений).
     * '@NotEmpty` - означает, что поле `title` должно быть заполнено (не может быть пустым).
     * В случае, если поле оказывается пустым, будет сгенерировано сообщение ошибки "Название не должно быть пустым!"
     */
    @Column(name = "title_for_url", nullable = false, unique = true)
    @NotEmpty(message = "Название не должно быть пустым!")
    private String titleForUrl;

    /**
     * '@Column` - указывает, что поле `oldPrice` в базе данных будет соответствовать столбцу с именем "old_price".
     * Этот столбец должен быть обязательным (не может быть пустым), значение по умолчанию устанавливается в null.
     * Аннотация `@NotNull(message = "Старая цена не может быть пуста")` означает, что поле `oldPrice` не может иметь значение null.
     * В случае, если значение оказывается null, будет сгенерировано сообщение ошибки "Старая цена не может быть null"
     */
    @Column(name = "old_price", nullable = true)
    @NotNull(message = "Cтарая цена не может быть пуста")
    private BigDecimal oldPrice;

    /**
     * `@Column` указывает, что поле `newProduct` будет соответствовать столбцу "new_product" в базе данных.
     * Значение этого столбца не может быть пустым, поэтому установлено значение `nullable = false`.
     * Аннотация `@NotNull` гарантирует, что поле `newProduct`
     * не будет равно `null`. Если значение поля окажется `null`, то будет сгенерировано сообщение ошибки
     * "Является ли продукт новым? Да:нет".
     */
    @Column(name = "new_product", nullable = false)
    @NotNull(message = "Является ли продукт новым? Да:нет")
    private Boolean newProduct;

    /**
     * `@Column` указывает, что поле `hotProduct` будет соответствовать столбцу "hot_product" в базе данных.
     * Значение этого столбца не может быть пустым, поэтому установлено значение `nullable = false`.
     * Аннотация `@NotNull` гарантирует, что поле `hotProduct` не будет равно `null`. Если значение поля окажется `null`,
     * то будет сгенерировано сообщение ошибки "Является ли продукт горячим? Да:нет".
     */
    @Column(name = "hot_product", nullable = false)
    @NotNull(message = "Является ли продукт горячим? Да:нет")
    private Boolean hotProduct;


    /**
     * '@Column' - указывает, что поле price в базе данных будет соответствовать столбцу с именем "price".
     * Этот столбец должен быть обязательным (не может быть пустым), значение по умолчанию устанавливается в null.
     * Аннотация @NotNull(message = "Цена должна быть указана!") означает, что поле price не может иметь значение null.
     * В случае, если значение оказывается null, будет сгенерировано сообщение ошибки "Цена должна быть указана!".
     */
    @Column(name = "price", nullable = false)
    @NotNull(message = "Цена должна быть указана!")
    private BigDecimal price;

    /**
     * '@Column' - указывает на свойство description в базе данных, где оно будет сохранено.
     * Length ограничивает длину значения поля description до 65535 символов.
     * Nullable = true означает, что поле description может быть пустым, то есть необязательным.
     */
    @Column(name = "description", length = 65535, nullable = true)
    private String description;

    /**
     * '@Column' - указывает на свойство quantity в базе данных, где оно будет сохранено.
     * Nullable = false гарантирует, что поле quantity не может быть пустым.
     * '@NotNull' - проверяет, что значение поля quantity не является пустым. Если значение пустое, будет выдано сообщение,
     * указанное в параметре message.
     */
    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Количество не должно быть пустым")
    private Integer quantity;

    /**
     * '@Column' - указывает на свойство weight в базе данных, где оно будет сохранено.
     * Nullable = true означает, что поле weight может быть пустым, то есть необязательным.
     */
    @Column(name = "weight", nullable = true)
    private Double weight;

    /**
     * '@Column' - указывает на свойство image_url в базе данных, где оно будет сохранено.
     */
    @Column(name = "image_url")
    private String imageUrl;


    /**
     * '@Column' - аннотация указывает, что поле "createDate" отображается в столбце с именем "create_date" в базе данных.
     */
    @Column(name = "create_date")
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
     * '@PrePersist' - аннотация указывает на метод, который будет выполнен перед сохранением сущности (Product) в базу данных.
     * В данном случае метод "onCreate()" устанавливает текущую дату и время в поле "createDate" перед сохранением.
     */
    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    /**
     * '@OneToMany' - аннотация указывает на отношение один-ко-многим между текущей сущностью (Product) и другой сущностью (Comment).
     * Атрибут "cascade" определяет каскадные операции, которые должны быть применены к связанным сущностям Comment.
     * В данном случае указано значение CascadeType.REMOVE, что означает, что при удалении сущности Product все связанные
     * сущности Comment также будут удалены.
     * Атрибут "mappedBy" определяет свойство в сущности Comment, которое является владеющей стороной отношения.
     * Свойство "product" в сущности Comment является владеющей стороной отношения.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comment> comments;

    /**
     * Здесь создается связь "многие ко многим" между классами "Product" и "Subcategory".
     * Поле "subcategories" представляет собой множество подкатегорий, к которым относится данный продукт.
     * Маппинг происходит через поле "products" в классе "Subcategory".
     */
    @ManyToMany(mappedBy = "products")
    private Set<Subcategory> subcategories;

    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private SizeEnum size;
}
