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
    OTHER,

    /**
     * 2.1)выбор способа доставки в постамат
     */
    SELECT_POSTAMAT,

    /**
     * 2.2)поиск постамата в конкретном подъезде дома
     */
    SEARCH_POSTAMAT_AT_HOUSE,

    /**
     * 3.1)Оплата заказа
     */
    PAY_ORDER,

    /**
     * 3.2)Открытие ячейки
     */
    OPEN_POSTAMAT,

    /**
     * 4.1)упаковка
     */
    PACKING,

    /**
     * 4.2)комплектность
     */
    COMPLETENESS,

    /**
     * 5.1)качество
     */
    QUALITY,

    /**
     * 5.2)несоответствие описанию на сайте
     */
    DESCRIPTION,

    /**
     * 6.1)работа постамата
     */
    WORK_POSTAMAT,

    /**
     * 6.2)его местоположение
     */
    LOCATION_POSTAMAT,

    /**
     * 6.3)внешний вид
     */
    VIEW_POSTAMAT,

    /**
     * 7.1)сроки
     */
    DEADLINE,

    /**
     * 7.2)Доставки стоимость
     */
    COAST_DELIVERY,

    /**
     * 7.3)Жалоба на работу курьеров
     */
    DELIVERY_GUY_REPORT,

    /**
     * 8.1)об оформленном заказе
     */
    CONFIRM_NOTIFICATION,

    /**
     * 8.2)о дате доставки
     */
    DELIVERY_NOTIFICATION,

    /**
     * 8.3)о готовности заказа к получению
     */
    READY_NOTIFICATION
}
