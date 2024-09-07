package ru.etu.merchstoreLETI.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Класс, представляющий ответ на сообщение.
 * Содержит сообщение в качестве своего атрибута.
 * Используется для отправки ответного сообщения клиенту.
 * '@Data' - это аннотация, которая автоматически создает типичные методы: геттеры, сеттеры, equals, hashCode и toString.
 * '@AllArgsConstructor' - это аннотация, которая автоматически создает конструктор с параметрами для всех полей класса.
 * '@NoArgsConstructor' - это аннотация, которая автоматически создает конструктор без параметров для класса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    /**
     * Поле message, содержащее ответное сообщение пользователю.
     */
    private String message;

}
