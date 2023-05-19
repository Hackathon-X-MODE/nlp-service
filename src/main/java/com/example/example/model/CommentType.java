package com.example.example.model;

public enum CommentType {
    /**
     * Описание товара на сайте интернет-магазина / маркетплейса;
     */
    PRODUCT_DESCRIPTION,

    /**
     * Оформление заказа
     */
    PREPARE_ORDER,
    /**
     * Получение заказа
     */
    GETTING_ORDER,
    /**
     * Полученный заказ
     */
    GOT_ORDER,
    /**
     * Товар
     */
    PRODUCT,
    /**
     * Постамат
     */
    POST_BOX,

    /**
     * Доставка
     */
    DELIVERY,
    /**
     * Уведомления
     */
    NOTIFICATION,
    /**
     * Иное
     */
    OTHER
}
