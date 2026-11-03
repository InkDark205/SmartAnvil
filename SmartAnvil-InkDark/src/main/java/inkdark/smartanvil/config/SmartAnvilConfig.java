
package inkdark.smartanvil.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SmartAnvilConfig {
    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().create();

    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir()
                    .resolve("smartanvil.json");

    private static SmartAnvilConfig INSTANCE = new SmartAnvilConfig();

    public enum Mode { SMART, PRIORITY }
    public enum SidePriority { LEFT, RIGHT }
    public enum RemovalMode { DROP, CHEST }

    // Master switch for the feature.
    public boolean enabled = true;

    public Mode mode = Mode.SMART;
    public SidePriority sidePriority = SidePriority.LEFT;
    public RemovalMode removalMode = RemovalMode.DROP;

    public int actionDelayTicks = 4;
    public int maxEnchantmentLines = 6;
    public int maxTotalEnchantmentLevels = 100;

    public boolean autoRemoveMaxedBooks = false;
    public boolean preserveCompatibleEnchantments = true;
    public boolean requireEnoughExperience = true;
    public boolean showStatusMessages = true;

    public String enchantmentPriorityCsv =
            "minecraft:mending,minecraft:unbreaking,"
                    + "minecraft:efficiency,minecraft:sharpness";

    public static SmartAnvilConfig get() {
        return INSTANCE;
    }

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                try (Reader reader = Files.newBufferedReader(FILE)) {
                    SmartAnvilConfig loaded =
                            GSON.fromJson(reader, SmartAnvilConfig.class);

                    if (loaded != null) {
                        INSTANCE = loaded;
                    }
                }
            }

            INSTANCE.sanitize();

            // Write defaults or migrated settings back to disk.
            save();
        } catch (Exception e) {
            System.err.println(
                    "[SmartAnvil] Could not load config: " + e.getMessage()
            );
            INSTANCE = new SmartAnvilConfig();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FILE.getParent());

            try (Writer writer = Files.newBufferedWriter(FILE)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (Exception e) {
            System.err.println(
                    "[SmartAnvil] Could not save config: " + e.getMessage()
            );
        }
    }

    private void sanitize() {
        actionDelayTicks =
                Math.max(1, Math.min(40, actionDelayTicks));

        maxEnchantmentLines =
                Math.max(1, Math.min(30, maxEnchantmentLines));

        maxTotalEnchantmentLevels =
                Math.max(1, Math.min(1000, maxTotalEnchantmentLevels));

        if (mode == null) mode = Mode.SMART;
        if (sidePriority == null) sidePriority = SidePriority.LEFT;
        if (removalMode == null) removalMode = RemovalMode.DROP;

        if (enchantmentPriorityCsv == null) {
            enchantmentPriorityCsv = "";
        }
    }
}
