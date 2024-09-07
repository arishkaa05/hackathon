package ru.etu.merchstoreLETI.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс представляет 'детали корзины'. Аннотирован как сущность JPA с использованием таблицы "bucket_details".
 * '@Entity' - показывает, что данный класс является JPA-сущностью и будет отображаться в таблице базы данных.
 * '@Data' - аннотация Lombok, которая генерирует методы getter, setter, equals, hashCode и toString.
 * '@AllArgsConstructor' - аннотация Lombok, которая генерирует конструктор с аргументами.
 * '@NoArgsConstructor' - аннотация Lombok, которая генерирует конструктор без аргументов.
 * '@Table' - указывает название таблицы, к которой будет отображаться данная сущность.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buckets")
public class Bucket {
    /**
     * '@Id' - указывает, что это поле является идентификатором сущности.
     * '@GeneratedValue' - указывает, что значение идентификатора будет генерироваться автоматически и будет использоваться
     * стратегия GenerationType.IDENTITY - идентификатор будет автоматически инкрементироваться при вставке нового объекта
     * в базу данных. @Column - указывает, что это поле отображается на столбец с именем "bucket_id" в базе данных, не может
     * иметь значение null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Поле, которое представляет связь с другой сущностью User.
     * '@OneToOne' - указывает, что это поле устанавливает связь 'один к одному' с другой сущностью(user).
     * '@JoinColumn' - указывает, что в базе данных для данного поля будет создан столбец с именем "user_id" для хранения
     * связи с сущностью User.
     * '@JsonIgnore' - указывает, что данное поле должно быть игнорировано при сериализации в JSON. Это означает, что
     * значение этого поля не будет включено в вывод JSON-объекта.
     */
    @OneToOne
    @JoinColumn(name = "users_id")
    private User user;

    /**
     * Список объектов класса Product, представляющий связь между текущей сущностью (Bucket) и сущностью Product.
     * '@ManyToMany' - указывает, что это поле устанавливает связь многие-ко-многим с сущностью Product.
     * '@JoinTable' - указывает, что для установления связи между текущей сущностью и сущностью Product будет использована
     * таблица "bucket_products". Таблица будет иметь две колонки - "bucket_id" и "product_id", которые будут использоваться
     * для хранения связи.
     */
    @ManyToMany
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name = "buckets_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;

    /**
     * Поле класса - представляет общую стоимость.
     * '@Column' - указывает, что это поле отображается на столбец с именем "total_cost" в базе данных.
     * Nullable = true означает, что данное поле может иметь значение null(быть пустым).
     */
    @Column(name = "total_cost", nullable = true)
    private BigDecimal totalCost;

    /**
     * Поле класса - количество продуктов.
     * '@Column' - указывает, что это поле отображается на столбец с именем "amount_products" в базе данных.
     * Nullable = true означает, что данное поле может иметь значение null.
     */
    @Column(name = "amount_products", nullable = true)
    private Integer amountProducts;

}
