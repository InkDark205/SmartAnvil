package inkdark.smartanvil.gui;

import inkdark.smartanvil.config.SmartAnvilConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

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

        addDrawableChild(ButtonWidget.builder(modeLabel(), button -> {
            cfg.mode = cfg.mode == SmartAnvilConfig.Mode.SMART
                    ? SmartAnvilConfig.Mode.PRIORITY
                    : SmartAnvilConfig.Mode.SMART;
            button.setMessage(modeLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(sideLabel(), button -> {
            cfg.sidePriority = cfg.sidePriority == SmartAnvilConfig.SidePriority.LEFT
                    ? SmartAnvilConfig.SidePriority.RIGHT
                    : SmartAnvilConfig.SidePriority.LEFT;
            button.setMessage(sideLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(delayLabel(), button -> {
            cfg.actionDelayTicks = cfg.actionDelayTicks >= 10 ? 1 : cfg.actionDelayTicks + 1;
            button.setMessage(delayLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(linesLabel(), button -> {
            cfg.maxEnchantmentLines = cfg.maxEnchantmentLines >= 12 ? 1 : cfg.maxEnchantmentLines + 1;
            button.setMessage(linesLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(levelsLabel(), button -> {
            cfg.maxTotalEnchantmentLevels = cfg.maxTotalEnchantmentLevels >= 100
                    ? 10 : cfg.maxTotalEnchantmentLevels + 10;
            button.setMessage(levelsLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(autoRemoveLabel(), button -> {
            cfg.autoRemoveMaxedBooks = !cfg.autoRemoveMaxedBooks;
            button.setMessage(autoRemoveLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        y += 27;
        addDrawableChild(ButtonWidget.builder(statusLabel(), button -> {
            cfg.showStatusMessages = !cfg.showStatusMessages;
            button.setMessage(statusLabel());
            SmartAnvilConfig.save();
        }).dimensions(left, y, 310, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(left, height - 32, 310, 20).build());
    }

    private Text modeLabel() {
        return Text.literal("Mode: " + cfg.mode + " (click to switch)");
    }

    private Text sideLabel() {
        return Text.literal("Anvil side priority: " + cfg.sidePriority + " (click to switch)");
    }

    private Text delayLabel() {
        return Text.literal("Action delay: " + cfg.actionDelayTicks + " ticks");
    }

    private Text linesLabel() {
        return Text.literal("Max enchantment lines: " + cfg.maxEnchantmentLines);
    }

    private Text levelsLabel() {
        return Text.literal("Max total enchantment levels: " + cfg.maxTotalEnchantmentLevels);
    }

    private Text autoRemoveLabel() {
        return Text.literal("Auto-remove maxed books: " + (cfg.autoRemoveMaxedBooks ? "ON" : "OFF"));
    }

    private Text statusLabel() {
        return Text.literal("Status messages: " + (cfg.showStatusMessages ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 18, 0xFFFFFF);
        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.literal("Settings are saved to config/smartanvil.json"),
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
}
