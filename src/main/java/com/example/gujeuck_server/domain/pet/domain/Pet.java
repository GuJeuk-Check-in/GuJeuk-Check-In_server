package com.example.gujeuck_server.domain.pet.domain;

import com.example.gujeuck_server.domain.pet.domain.enums.PetSpecies;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet extends BaseIdEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_user_id", nullable = false, unique = true)
    private PetUser petUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PetSpecies species;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int xp;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int streak;

    @Column(nullable = false)
    private int maxStreak;

    @Column(nullable = false)
    private int visits;

    private LocalDate lastCheckinDate;

    @Column(nullable = false)
    private Instant lastSeenAt;

    @Column(nullable = false)
    private int hunger;

    @Column(nullable = false)
    private int happy;

    @Column(nullable = false)
    private int clean;

    @Column(nullable = false)
    private int energy;

    @Column(length = 30)
    private String equippedHat;

    @Column(length = 30)
    private String equippedShirt;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_owned_hat", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "item_id", nullable = false, length = 30)
    private Set<String> ownedHats = new LinkedHashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_owned_shirt", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "item_id", nullable = false, length = 30)
    private Set<String> ownedShirts = new LinkedHashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_owned_prop", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "item_id", nullable = false, length = 30)
    private Set<String> ownedProps = new LinkedHashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_owned_buff", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "item_id", nullable = false, length = 30)
    private Set<String> ownedBuffs = new LinkedHashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "pet_placed_prop", joinColumns = @JoinColumn(name = "pet_id"))
    @OrderColumn(name = "display_order")
    @Column(name = "item_id", nullable = false, length = 30)
    private List<String> placedProps = new ArrayList<>();

    public int stage() {
        if (xp >= 350) return 4;
        if (xp >= 180) return 3;
        if (xp >= 80) return 2;
        if (xp >= 30) return 1;
        return 0;
    }

    public void applyStats(int hunger, int happy, int clean, int energy) {
        this.hunger = clampStat(hunger);
        this.happy = clampStat(happy);
        this.clean = clampStat(clean);
        this.energy = clampStat(energy);
    }

    public void markSeen(Instant seenAt) {
        this.lastSeenAt = seenAt;
    }

    public void awardCheckin(int awardedPoints, LocalDate today, int streak, int maxStreak) {
        this.points += awardedPoints;
        this.visits += 1;
        this.lastCheckinDate = today;
        this.streak = streak;
        this.maxStreak = maxStreak;
    }

    public void spendPoints(int cost) {
        this.points -= cost;
    }

    public void gainXp(int gainedXp) {
        this.xp += gainedXp;
    }

    public void increaseStat(String targetStat, int amount) {
        switch (targetStat) {
            case "hunger" -> this.hunger = clampStat(this.hunger + amount);
            case "happy" -> this.happy = clampStat(this.happy + amount);
            case "clean" -> this.clean = clampStat(this.clean + amount);
            case "energy" -> this.energy = clampStat(this.energy + amount);
            default -> throw new IllegalArgumentException("Unknown stat: " + targetStat);
        }
    }

    public int readStat(String targetStat) {
        return switch (targetStat) {
            case "hunger" -> hunger;
            case "happy" -> happy;
            case "clean" -> clean;
            case "energy" -> energy;
            default -> throw new IllegalArgumentException("Unknown stat: " + targetStat);
        };
    }

    public int minStat() {
        return Math.min(Math.min(hunger, happy), Math.min(clean, energy));
    }

    public boolean hasBuff(String itemId) {
        return ownedBuffs.contains(itemId);
    }

    public void addOwnedHat(String itemId) {
        ownedHats.add(itemId);
    }

    public void addOwnedShirt(String itemId) {
        ownedShirts.add(itemId);
    }

    public void addOwnedProp(String itemId) {
        ownedProps.add(itemId);
    }

    public void addOwnedBuff(String itemId) {
        ownedBuffs.add(itemId);
    }

    public void equip(String hat, String shirt) {
        this.equippedHat = hat;
        this.equippedShirt = shirt;
    }

    public void replacePlacedProps(List<String> props) {
        this.placedProps.clear();
        this.placedProps.addAll(props);
    }

    private int clampStat(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
