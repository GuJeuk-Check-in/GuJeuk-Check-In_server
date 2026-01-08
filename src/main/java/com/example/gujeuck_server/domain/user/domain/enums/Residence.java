package com.example.gujeuck_server.domain.user.domain.enums;

import lombok.Getter;

@Getter
public enum Residence {
    GWANPYEONG_DONG("관평동"),
    GUJEOK_DONG("구즉동"),
    NOEUN_1_DONG("노은1동"),
    NOEUN_2_DONG("노은2동"),
    NOEUN_3_DONG("노은3동"),
    SANDAE_DONG("상대동"),
    SHINSEONG_DONG("신성동"),
    ONCHEON_1_DONG("온천1동"),
    ONCHEON_2_DONG("온천2동"),
    WONSINHEUNG_DONG("원신흥동"),
    JEONMIN_DONG("전민동"),
    JINJAM_DONG("진잠동"),
    HAKHA_DONG("학하동");

    private final String koreanName;

    Residence(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Residence fromKoreanName(String koreanName) {
        for (Residence r : values()) {
            if (r.koreanName.equals(koreanName)) {
                return r;
            }
        }
        return null;
    }
}