package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.enums.PetItemCategory;
import com.example.gujeuck_server.domain.pet.exception.InvalidPetItemException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PetShopCatalog {
    private static final Map<String, PetShopItem> ITEMS = Arrays.stream(PetShopItem.values())
            .collect(Collectors.toUnmodifiableMap(PetShopItem::getId, Function.identity()));

    private PetShopCatalog() {
    }

    public static PetShopItem get(String itemId) {
        PetShopItem item = ITEMS.get(itemId);

        if (item == null) {
            throw InvalidPetItemException.EXCEPTION;
        }

        return item;
    }

    public static List<PetShopItem> itemsByCategory(PetItemCategory category) {
        return ITEMS.values().stream()
                .filter(item -> item.getCategory() == category)
                .toList();
    }

    public enum PetShopItem {
        CAP("cap", "빨간 모자", 18, PetItemCategory.HAT, null),
        PARTY("party", "파티 고깔", 14, PetItemCategory.HAT, null),
        BEANIE("beanie", "방울 비니", 20, PetItemCategory.HAT, null),
        CROWN("crown", "작은 왕관", 26, PetItemCategory.HAT, null),
        RIBBON("ribbon", "핑크 리본", 22, PetItemCategory.HAT, null),
        FLOWER("flower", "꽃 머리핀", 18, PetItemCategory.HAT, null),
        STRIPE("stripe", "줄무늬 티셔츠", 16, PetItemCategory.SHIRT, null),
        HEART("heart", "하트 티셔츠", 16, PetItemCategory.SHIRT, null),
        STAR("star", "별 티셔츠", 19, PetItemCategory.SHIRT, null),
        HOODIE("hoodie", "초록 후드", 24, PetItemCategory.SHIRT, null),
        RAINBOW("rainbow", "무지개 티셔츠", 22, PetItemCategory.SHIRT, null),
        BALL("ball", "공", 10, PetItemCategory.PROP, null),
        BONE("bone", "장난감 뼈다귀", 11, PetItemCategory.PROP, null),
        BOOK("book", "그림책", 13, PetItemCategory.PROP, null),
        BALLOON("balloon", "풍선", 12, PetItemCategory.PROP, null),
        CUSHION("cushion", "쿠션", 15, PetItemCategory.PROP, null),
        FLOWERPOT("flowerpot", "작은 화분", 16, PetItemCategory.PROP, null),
        GIFT("gift", "선물 상자", 18, PetItemCategory.PROP, null),
        TEDDY("teddy", "곰인형", 20, PetItemCategory.PROP, null),
        LAMP("lamp", "무드 램프", 24, PetItemCategory.PROP, null),
        SLOWDECAY("slowdecay", "든든 사료통", 40, PetItemCategory.BUFF, "decay_x0.5"),
        XPBOOST("xpboost", "성장 영양제", 45, PetItemCategory.BUFF, "xp_x1.1"),
        PTBOOST("ptboost", "행운의 부적", 50, PetItemCategory.BUFF, "checkin_plus_1");

        private final String id;
        private final String name;
        private final int cost;
        private final PetItemCategory category;
        private final String effect;

        PetShopItem(String id, String name, int cost, PetItemCategory category, String effect) {
            this.id = id;
            this.name = name;
            this.cost = cost;
            this.category = category;
            this.effect = effect;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCost() {
            return cost;
        }

        public PetItemCategory getCategory() {
            return category;
        }

        public String getEffect() {
            return effect;
        }
    }
}
