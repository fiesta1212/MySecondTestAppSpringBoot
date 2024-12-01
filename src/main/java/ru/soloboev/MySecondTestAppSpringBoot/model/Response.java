package ru.soloboev.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Response {

    /**
     * Уникальный идентификатор сообщения.
     * Обязательный параметр.
     */
    private String uid;

    /**
     * Уникальный идентификатор операции.
     * Обязательный параметр.
     */
    private String operationUid;

    /**
     * Время создания сообщения.
     */
    private String systemTime;

    /**
     * Код статуса обработки.
     * Возможные значения: "success", "failed".
     */
    private Codes code;

    /**
     * Годовая премия
     */
    private Double annualBonus;

    /**
     * Квартальная премия
     */
    private Double quarterlyBonus;

    /**
     * Код ошибки, если обработка завершилась неуспешно.
     * Возможные значения: "UnsupportedCodeException", "ValidationException", "UnknownException".
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке, если обработка завершилась неуспешно.
     * Возможные значения: "Не поддерживаемая ошибка", "Ошибка валидации", "Произошла непредвиденная ошибка".
     */
    private ErrorMessages errorMessage;
}
