package ru.anafro.wondercrates.drops;

import ru.anafro.wondercrates.utils.arrays.CircularArray;
import ru.anafro.wondercrates.utils.events.Randoms;

public class CrateDropTape {
    private static final int TAPE_SPINS = 2;
    private static final int TAPE_SPINS_RANGE = 1;
    private final CircularArray<CrateDrop> drops;

    public CrateDropTape(CrateDrop... drops) {
        this.drops = new CircularArray<>(drops);
    }

    public CrateDrop at(int loopingIndex) {
        return drops.get(loopingIndex);
    }

    public int nextLoopingDropIndex() {
        var rarities = drops.stream().mapToInt(drop -> drop.getRarity().getPercent()).sum();
        var rarityCursor = Randoms.nextInt(rarities);

        for (int i = 0; i < drops.size(); i += 1) {
            var drop = drops.get(i);
            rarityCursor -= drop.getRarity().getPercent();

            if (rarityCursor <= 0) {
                return i + drops.size() * Randoms.nextIntAround(TAPE_SPINS, TAPE_SPINS_RANGE);
            }
        }

        throw new RuntimeException("Rarity cursor has overflown the rarity sum.");
    }

    public String[] getLore() {
        return drops.stream().map(CrateDrop::toChatString).toArray(String[]::new);
    }
}
