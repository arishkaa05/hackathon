package ru.etu.merchstoreLETI.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import ru.etu.merchstoreLETI.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Город вводимый пользователем должен существовать.
     */
    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "contact_number", nullable = true,unique = true)
    private String contactNumber;

    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Поле username аннотировано с помощью @Size и @Pattern для проверки ввода имени пользователя.
     * Максимальное число символов, входящих в состав вводимого имени = 40
     * Вводить пользователь может только имя, имеющее в своем составе буквы русского или английского алфавитов.
     */
    @Column(name = "username", nullable = false)
    @Size(max = 40)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "Имя может содержать только английские или русские буквы")
    private String username;

    /**
     * Поле lastname аннотировано с помощью @Size и @Pattern для проверки ввода фамилии.
     * Максимальное количество символов в фамилии = 40
     * Фамилия, как и имя, может содержать лишь буквы: русские, английские.
     */
    @Column(name = "lastname", nullable = false)
    @Size(max = 40)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "Фамилия может содержать только английские или русские буквы")
    private String lastname;
    /**
     * Поле email аннотировано с помощью @ValidEmail для проверки ввода электронной почты.
     * Проверяется, имеет ли почта строение типа: vlad@gmail.com
     */
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Transient
    private String passwordConfirmation;

    /**
     * Поле password аннотировано с помощью @Size и указывает максимальную длину в 3000 символов.
     */
    @Column(name = "password", length = 3000, nullable = false)
    private String password;

    /**
     * Поле createDate аннотировано с помощью @JsonFormat для указания формата даты при сериализации в JSON,
     * и с помощью @Column, чтобы указать имя столбца. "yyyy-MM-dd HH:mm:ss" указывает на формат года (4 цифры),
     * месяца (2 цифры), дня (2 цифры), часа (24-часовой формат), минут, секунд.
     */
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    /**
     * В данном случае, она указывает, что User является "управляющей" стороной отношения, где User содержит
     * коллекцию объектов Order. Аннотация CascadeType.MERGE указывает, что операция объединения (merge) должна быть
     * применена к связанным сущностям типа Order, при вызове операции объединения над объектом User. Это означает,
     * что при объединении объекта User, JPA автоматически применит операцию объединения (merge) ко всем связанным объектам Order,
     * чтобы сохранить их в базе данных
     */
    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Bucket bucket;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "user")
    private FavoriteItem favoriteItem;
    /**
     * Аннотация @UpdateTimestamp указывает на автоматическое обновление значения поля "change". При каждом изменении
     * объекта в базе данных. Значение будет представлено в виде объекта LocalDateTime. @Column - поле будет отображаться в базе
     * данных как столбец с именем "change", оно не может быть пустым (содержать значение null).
     */
    @UpdateTimestamp
    @Column(name = "update_date",nullable = false)
    private LocalDateTime updateDate;
}
