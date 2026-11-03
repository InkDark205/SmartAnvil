
package inkdark.smartanvil.gui;

import inkdark.smartanvil.config.SmartAnvilConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public final class SmartAnvilConfigScreen extends Screen {
    private final Screen parent;
    private SmartAnvilConfig cfg;

    public SmartAnvilConfigScreen(Screen parent) {
        super(Text.literal("Smart Anvil — InkDark"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        cfg = SmartAnvilConfig.get();
        clearChildren();

        int left = width / 2 - 155;
        int y = 52;
        int row = 27;
        int w = 310;

        // Master ON/OFF switch.
        addDrawableChild(ButtonWidget.builder(
                enabledLabel(),
                button -> {
                    cfg.enabled = !cfg.enabled;
                    button.setMessage(enabledLabel());
                    save();
                }
        ).dimensions(left, y, w, 20).build());

        // Mode.
        y += row;
        addDrawableChild(ButtonWidget.builder(
                modeLabel(),
                button -> {
                    cfg.mode = cfg.mode == SmartAnvilConfig.Mode.SMART
                            ? SmartAnvilConfig.Mode.PRIORITY
                            : SmartAnvilConfig.Mode.SMART;
                    button.setMessage(modeLabel());
                    save();
                }
        ).dimensions(left, y, w, 20).build());

        // Anvil side.
        y += row;
        addDrawableChild(ButtonWidget.builder(
                sideLabel(),
                button -> {
                    cfg.sidePriority =
                            cfg.sidePriority == SmartAnvilConfig.SidePriority.LEFT
                                    ? SmartAnvilConfig.SidePriority.RIGHT
                                    : SmartAnvilConfig.SidePriority.LEFT;
                    button.setMessage(sideLabel());
                    save();
                }
        ).dimensions(left, y, w, 20).build());

        // Numeric sliders.
        y += row;
        addDrawableChild(new IntSlider(
                left, y, w, 20,
                "Action delay (ticks)",
                cfg.actionDelayTicks, 1, 40,
                v -> cfg.actionDelayTicks = v
        ));

        y += row;
        addDrawableChild(new IntSlider(
                left, y, w, 20,
                "Max enchantment lines",
                cfg.maxEnchantmentLines, 1, 30,
                v -> cfg.maxEnchantmentLines = v
        ));

        y += row;
        addDrawableChild(new IntSlider(
                left, y, w, 20,
                "Max total enchantment levels",
                cfg.maxTotalEnchantmentLevels, 1, 1000,
                v -> cfg.maxTotalEnchantmentLevels = v
        ));

        // Boolean settings.
        y += row;
        addDrawableChild(toggle(
                left, y, w, "Auto-remove maxed books",
                () -> cfg.autoRemoveMaxedBooks,
                v -> cfg.autoRemoveMaxedBooks = v
        ));

        y += row;
        addDrawableChild(toggle(
                left, y, w, "Preserve compatible enchantments",
                () -> cfg.preserveCompatibleEnchantments,
                v -> cfg.preserveCompatibleEnchantments = v
        ));

        y += row;
        addDrawableChild(toggle(
                left, y, w, "Require enough experience",
                () -> cfg.requireEnoughExperience,
                v -> cfg.requireEnoughExperience = v
        ));

        y += row;
        addDrawableChild(toggle(
                left, y, w, "Status messages",
                () -> cfg.showStatusMessages,
                v -> cfg.showStatusMessages = v
        ));

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                button -> close()
        ).dimensions(left, height - 28, w, 20).build());
    }

    private Text enabledLabel() {
        return Text.literal(
                "SmartAnvil: " + (cfg.enabled ? "ON" : "OFF")
                        + " (click to toggle)"
        );
    }

    private Text modeLabel() {
        return Text.literal("Mode: " + cfg.mode + " (click to switch)");
    }

    private Text sideLabel() {
        return Text.literal(
                "Anvil side priority: " + cfg.sidePriority
                        + " (click to switch)"
        );
    }

    private ButtonWidget toggle(
            int x, int y, int width, String label,
            BooleanSupplier getter,
            Consumer<Boolean> setter
    ) {
        return ButtonWidget.builder(
                toggleLabel(label, getter.getAsBoolean()),
                button -> {
                    boolean next = !getter.getAsBoolean();
                    setter.accept(next);
                    button.setMessage(toggleLabel(label, next));
                    save();
                }
        ).dimensions(x, y, width, 20).build();
    }

    private Text toggleLabel(String label, boolean enabled) {
        return Text.literal(label + ": " + (enabled ? "ON" : "OFF"));
    }

    private void save() {
        SmartAnvilConfig.save();
    }

    @Override
    public void render(
            DrawContext context, int mouseX, int mouseY, float delta
    ) {
        // Avoid invoking Minecraft's blur background a second time.
        context.fill(0, 0, width, height, 0xC0101010);

        context.drawCenteredTextWithShadow(
                textRenderer, title, width / 2, 18, 0xFFFFFF
        );

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.literal("Saved to config/smartanvil.json"),
                width / 2, 35, 0xAAAAAA
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        SmartAnvilConfig.save();

        if (client != null) {
            client.setScreen(parent);
        }
    }

    private static final class IntSlider extends SliderWidget {
        private final String label;
        private final int min;
        private final int max;
        private final IntConsumer onChange;
        private int currentValue;

        IntSlider(
                int x, int y, int width, int height,
                String label, int initialValue,
                int min, int max, IntConsumer onChange
        ) {
            super(
                    x, y, width, height, Text.empty(),
                    (clamp(initialValue, min, max) - min)
                            / (double) (max - min)
            );

            this.label = label;
            this.min = min;
            this.max = max;
            this.onChange = onChange;
            this.currentValue = clamp(initialValue, min, max);

            updateMessage();
        }

        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }

        private int sliderValue() {
            return clamp(
                    min + (int) Math.round(value * (max - min)),
                    min, max
            );
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(label + ": " + sliderValue()));
        }

        @Override
        protected void applyValue() {
            int next = sliderValue();

            if (next != currentValue) {
                currentValue = next;
                onChange.accept(next);
                SmartAnvilConfig.save();
            }
        }
    }
}
