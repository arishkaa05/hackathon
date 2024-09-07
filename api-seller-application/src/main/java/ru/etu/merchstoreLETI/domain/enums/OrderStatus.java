package ru.etu.merchstoreLETI.domain.enums;


/**
 * Данный класс представляет собой перечисление, представляющее статус заказа.
 */
public enum OrderStatus {
    /**
     * в процессе
     */
    PROCESSING,

    /**
     * завершен
     */
    COMPLETED,

    /**
     * отменен
     */
    CANCELED
}
