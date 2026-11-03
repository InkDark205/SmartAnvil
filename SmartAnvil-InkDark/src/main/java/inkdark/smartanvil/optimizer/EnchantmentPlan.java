package inkdark.smartanvil.optimizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Game-independent planning primitives; actual XP cost remains server-authoritative. */
public final class EnchantmentPlan {
    private EnchantmentPlan() {}

    public record Enchantment(String id, int level, int maxLevel, boolean compatible) {}
    public record MergeStep(String leftLabel, String rightLabel, int estimatedPriorWorkPenalty, int priorityRank) {}

    public static int countLines(List<Enchantment> enchantments) {
        return (int) enchantments.stream().filter(e -> e.level() > 0).map(Enchantment::id).distinct().count();
    }

    public static int totalLevels(List<Enchantment> enchantments) {
        return enchantments.stream().mapToInt(e -> Math.max(0, e.level())).sum();
    }

    public static boolean reachesConfiguredLimit(List<Enchantment> enchantments, int maxLines, int maxLevels) {
        return countLines(enchantments) >= maxLines || totalLevels(enchantments) >= maxLevels;
    }

    /** Returns a stable, low-prior-work-first order for candidate merges. */
    public static List<MergeStep> orderSmart(List<MergeStep> candidates) {
        List<MergeStep> result = new ArrayList<>(candidates);
        result.sort(Comparator.comparingInt(MergeStep::estimatedPriorWorkPenalty)
                .thenComparingInt(MergeStep::priorityRank)
                .thenComparing(MergeStep::leftLabel)
                .thenComparing(MergeStep::rightLabel));
        return result;
    }
}
