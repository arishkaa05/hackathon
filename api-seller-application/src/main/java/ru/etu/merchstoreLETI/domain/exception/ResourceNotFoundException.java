package ru.etu.merchstoreLETI.domain.exception;

/**
 * Класс исключения, который указывает на то, что ресурс не был найден.
 * Используется для пометки случаев, когда запрашиваемый ресурс отсутствует.
 */
public class ResourceNotFoundException  extends RuntimeException{
    /**
     * Конструктор класса ResourceNotFoundException.
     *
     * @param message сообщение об ошибке
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

