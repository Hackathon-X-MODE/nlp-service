package com.example.example.service;

import com.example.example.model.CommentType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommentDirectory {

    private final Map<String, CommentType> storage = new HashMap<>();

    public CommentDirectory() {
        this.storage.putAll(
                Map.of(
                        "описание товара на сайте интернет-магазина", CommentType.PRODUCT_DESCRIPTION,
                        "оформление заказа", CommentType.PREPARE_ORDER,
                        "получение заказа", CommentType.GETTING_ORDER,
                        "полученный заказ", CommentType.GOT_ORDER,
                        "товар", CommentType.PRODUCT,
                        "работа постамата", CommentType.POST_BOX,
                        "доставка", CommentType.DELIVERY,
                        "уведомления", CommentType.NOTIFICATION,
                        "иное", CommentType.OTHER
                )
        );

        this.storage.putAll(
                Map.of(
                        "выбор способа доставки в постамат", CommentType.SELECT_POSTAMAT,
                        "поиск постамата в конкретном подъезде дома", CommentType.SEARCH_POSTAMAT_AT_HOUSE,
                        "оплата заказа", CommentType.PAY_ORDER,
                        "открытие ячейки", CommentType.OPEN_POSTAMAT,
                        "упаковка", CommentType.PACKING,
                        "комплектность", CommentType.COMPLETENESS,
                        "качество", CommentType.QUALITY,
                        "несоответствие описанию на сайте", CommentType.DESCRIPTION,
                        "работа постамата", CommentType.WORK_POSTAMAT,
                        "местоположение постамата", CommentType.LOCATION_POSTAMAT
                )
        );

        this.storage.putAll(
                Map.of(
                        "внешний вид постамата", CommentType.VIEW_POSTAMAT,
                        "сроки доставки", CommentType.DEADLINE,
                        "стоимость доставки", CommentType.COAST_DELIVERY,
                        "жалоба на работу курьеров", CommentType.DELIVERY_GUY_REPORT,
                        "уведомление об оформленном заказе", CommentType.CONFIRM_NOTIFICATION,
                        "уведомление о дате доставки", CommentType.DELIVERY_NOTIFICATION,
                        "уведомление о готовности заказа к получению", CommentType.READY_NOTIFICATION
                )
        );
    }


    public Map<String, CommentType> getStorage() {
        return Map.copyOf(this.storage);
    }
}
