package inkdark.smartanvil.gui;

import inkdark.smartanvil.config.SmartAnvilConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class SmartAnvilConfigScreen extends Screen {
    private final Screen parent;
    private int page;
    private SmartAnvilConfig cfg;

    public SmartAnvilConfigScreen(Screen parent) {
        super(Component.literal("Smart Anvil — InkDark"));
        this.parent = parent;
    }

    @Override protected void init() {
        cfg = SmartAnvilConfig.get();
        clearWidgets();
        int left = this.width / 2 - 155;
        int y = 52;
        addRenderableWidget(Button.builder(modeLabel(), b -> {
            cfg.mode = cfg.mode == SmartAnvilConfig.Mode.SMART ? SmartAnvilConfig.Mode.PRIORITY : SmartAnvilConfig.Mode.SMART;
            b.setMessage(modeLabel());
            SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(sideLabel(), b -> {
            cfg.sidePriority = cfg.sidePriority == SmartAnvilConfig.SidePriority.LEFT ? SmartAnvilConfig.SidePriority.RIGHT : SmartAnvilConfig.SidePriority.LEFT;
            b.setMessage(sideLabel()); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(Component.literal("Action delay: " + cfg.actionDelayTicks + " ticks"), b -> {
            cfg.actionDelayTicks = cfg.actionDelayTicks >= 10 ? 1 : cfg.actionDelayTicks + 1;
            b.setMessage(Component.literal("Action delay: " + cfg.actionDelayTicks + " ticks")); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(Component.literal("Max enchantment lines: " + cfg.maxEnchantmentLines), b -> {
            cfg.maxEnchantmentLines = cfg.maxEnchantmentLines >= 12 ? 1 : cfg.maxEnchantmentLines + 1;
            b.setMessage(Component.literal("Max enchantment lines: " + cfg.maxEnchantmentLines)); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(Component.literal("Max total enchantment levels: " + cfg.maxTotalEnchantmentLevels), b -> {
            cfg.maxTotalEnchantmentLevels = cfg.maxTotalEnchantmentLevels >= 100 ? 10 : cfg.maxTotalEnchantmentLevels + 10;
            b.setMessage(Component.literal("Max total enchantment levels: " + cfg.maxTotalEnchantmentLevels)); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(Component.literal("Auto-remove maxed books: " + (cfg.autoRemoveMaxedBooks ? "ON" : "OFF")), b -> {
            cfg.autoRemoveMaxedBooks = !cfg.autoRemoveMaxedBooks;
            b.setMessage(Component.literal("Auto-remove maxed books: " + (cfg.autoRemoveMaxedBooks ? "ON" : "OFF"))); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        y += 27;
        addRenderableWidget(Button.builder(Component.literal("Status messages: " + (cfg.showStatusMessages ? "ON" : "OFF")), b -> {
            cfg.showStatusMessages = !cfg.showStatusMessages;
            b.setMessage(Component.literal("Status messages: " + (cfg.showStatusMessages ? "ON" : "OFF"))); SmartAnvilConfig.save();
        }).bounds(left, y, 310, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Done"), b -> onClose()).bounds(left, this.height - 32, 310, 20).build());
    }

    private Component modeLabel() { return Component.literal("Mode: " + cfg.mode + " (click to switch)"); }
    private Component sideLabel() { return Component.literal("Anvil side priority: " + cfg.sidePriority + " (click to switch)"); }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(font, title, width / 2, 18, 0xFFFFFF);
        graphics.drawCenteredString(font, "Settings are saved to config/smartanvil.json", width / 2, 35, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override public void onClose() {
        SmartAnvilConfig.save();
        if (minecraft != null) minecraft.setScreen(parent);
    }
}
