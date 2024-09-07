package ru.etu.merchstoreLETI.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.etu.merchstoreLETI.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    /**
     * Аннотация @Id указывает, что поле "id" является первичным ключом в таблице базы данных. Аннотация @GeneratedValue
     * (strategy = GenerationType.IDENTITY) указывает, что значение первичного ключа будет генерироваться автоматически с
     * использованием автоинкремента в базе данных.  Аннотация @Column указывает на название столбца "order_id" в таблице
     * базы данных, где будет храниться значение идентификатора. Также указано, что это поле не может быть пустым (nullable = false),
     * то есть должно быть заполнено обязательно.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  Long id;

    /**
     *  '@Column' - поле будет отображаться в базе данных как столбец с именем "created", не может быть пустым
     *  (содержать значение null).  Аннотация @CreationTimestamp указывает на автоматическую генерацию значения поля "created"
     *  при сохранении объекта в базе данных. Значение будет представлено в виде объекта LocalDateTime.
     */
    @Column(name = "create_date",nullable = false)
    @CreationTimestamp
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
     *  '@ManyToOne' - это аннотация JPA (Java Persistence API), которая указывает на то, что поле является частью отношения
     *  "многие к одному" между текущей сущностью и сущностью User. В данном случае, указывает на то, что каждый комментарий
     *  относится к одному пользователю.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /**
    * Это поле представляет собой список продуктов, связанных с заказом.
    * Аннотация @ManyToMany указывает, что это отношение многие-ко-многим,
    * то есть один заказ может содержать несколько продуктов, и каждый продукт может быть связан с несколькими заказами.
    * Аннотация @JoinTable определяет таблицу, которая будет использоваться для хранения информации о связи
    * между заказами и продуктами. В данном случае таблица называется "orders_products".
    * Аннотации @JoinColumns и @InverseJoinColumns указывают, какие столбцы в таблице будут использоваться
    * для связывания заказов и продуктов.
    */
    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;


    /**
     *  '@Column' - это аннотация JPA, которая указывает на свойства столбца в базе данных для данного поля. Поле address
     *  должно быть отображено в столбце "address" в базе данных.
     */
    @Column(name = "address")
    private String address;

    /**
     * '@Enumerated(EnumType.STRING)' - это аннотация JPA, которая указывает на способ отображения перечисления (Enum) в
     * базе данных. В данном случае, аннотация указывает, что значение перечисления будет сохранено в виде строки (STRING).
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     *  '@Column' - это аннотация JPA, которая указывает на свойства столбца в базе данных для данного поля. Поле comment
     *  должно быть отображено в столбце "comment" в базе данных.
     */
    @Column(name = "comment")
    private String comment;

    /**
     *  '@Column' - это аннотация JPA, которая указывает на свойства столбца в базе данных для данного поля. Поле deliveryPrice
     *  должно быть отображено в столбце "delivery_price" в базе данных. Стоимость доставки для товара. Объявлена как BigDecimal
     *  для точной обработки десятичных значений.
     */
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
}
