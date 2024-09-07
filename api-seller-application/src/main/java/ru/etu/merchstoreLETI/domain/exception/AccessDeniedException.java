package ru.etu.merchstoreLETI.domain.exception;

/**
 * Исключение, выбрасываемое при отказе в доступе.
 */
public class AccessDeniedException extends RuntimeException{
    /**
     * Создает новый экземпляр исключения AccessDeniedException без детального сообщения.
     */
    public AccessDeniedException(){
        super();
    }
}

