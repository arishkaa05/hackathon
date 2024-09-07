package ru.etu.merchstoreLETI.domain.exception;

/**
 * Исключение, выбрасываемое при возникновении ошибки при сопоставлении ресурсов.
 */
public class ResourceMappingException extends RuntimeException{
    /**
     * Конструктор класса.
     * @param message сообщение об ошибке
     */
    public ResourceMappingException(String message) {
        super(message);
    }
}
