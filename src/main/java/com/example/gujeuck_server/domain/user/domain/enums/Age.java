package com.example.gujeuck_server.domain.user.domain.enums;

public enum Age {
    BABY("유아아동", 0, 8),
    AGE_9_13("9~13", 9, 13),
    AGE_14_16("14~16", 14, 16),
    AGE_17_19("17~19", 17, 19),
    AGE_20_24("20~24", 20, 24),
    ADULT("성인", 25, Integer.MAX_VALUE);

    private final String label;
    private final int minAge;
    private final int maxAge;

    Age(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getLabel() {
        return label;
    }

    public static Age from(int age) {
        for (Age group : values()) {
            if (age >= group.minAge && age <= group.maxAge) {
                return group;
            }
        }
        throw new IllegalArgumentException("Invalid age: " + age);
    }
}
