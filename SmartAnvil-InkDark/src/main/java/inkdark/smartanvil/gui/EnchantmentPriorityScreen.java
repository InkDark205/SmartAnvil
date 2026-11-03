package inkdark.smartanvil.gui;

import inkdark.smartanvil.config.SmartAnvilConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Searchable, persistent ordering editor for Priority Mode enchantments. */
public final class EnchantmentPriorityScreen extends Screen {
    private static final int PAGE_SIZE = 5;

    private final Screen parent;
    private final SmartAnvilConfig config;
    private final List<String> orderedIds = new ArrayList<>();
    private TextFieldWidget searchField;
    private int page;
    private String searchText = "";

    public EnchantmentPriorityScreen(Screen parent) {
        super(Text.literal("Smart Anvil — Enchantment Priority"));
        this.parent = parent;
        this.config = SmartAnvilConfig.get();
        loadOrder();
    }

    private void loadOrder() {
        Set<String> ids = new LinkedHashSet<>();
        if (config.enchantmentPriorityCsv != null && !config.enchantmentPriorityCsv.isBlank()) {
            for (String id : config.enchantmentPriorityCsv.split(",")) {
                String clean = id.trim();
                if (!clean.isEmpty()) ids.add(clean);
            }
        }

        List<String> registered = Registries.ENCHANTMENT.getIds().stream()
                .map(Identifier::toString)
                .sorted(Comparator.naturalOrder())
                .toList();
        ids.addAll(registered);
        orderedIds.addAll(ids);
    }

    @Override
    protected void init() {
        rebuildWidgets();
    }

    private void rebuildWidgets() {
        clearChildren();
        int left = width / 2 - 165;

        if (searchField == null) {
            searchField = new TextFieldWidget(textRenderer, left, 48, 330, 20,
                    Text.literal("Search enchantments"));
            searchField.setMaxLength(100);
            searchField.setChangedListener(value -> {
                searchText = value;
                page = 0;
                rebuildWidgets();
                if (searchField != null) setFocused(searchField);
            });
        }
        addDrawableChild(searchField);

        List<String> filtered = filteredIds();
        int pageCount = Math.max(1, (filtered.size() + PAGE_SIZE - 1) / PAGE_SIZE);
        page = Math.max(0, Math.min(page, pageCount - 1));
        int start = page * PAGE_SIZE;
        int end = Math.min(filtered.size(), start + PAGE_SIZE);

        for (int i = start; i < end; i++) {
            String id = filtered.get(i);
            int row = i - start;
            int y = 91 + row * 25;
            int rank = orderedIds.indexOf(id) + 1;
            addDrawableChild(ButtonWidget.builder(Text.literal(displayName(id)), button -> { })
                    .dimensions(left, y, 215, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("#" + rank), button -> { })
                    .dimensions(left + 219, y, 42, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("↑"), button -> move(id, -1))
                    .dimensions(left + 265, y, 28, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("↓"), button -> move(id, 1))
                    .dimensions(left + 297, y, 28, 20).build());
        }

        int controlsY = 91 + PAGE_SIZE * 25 + 2;
        addDrawableChild(ButtonWidget.builder(Text.literal("Previous"), button -> {
            page = Math.max(0, page - 1);
            rebuildWidgets();
        }).dimensions(left, controlsY, 100, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal((page + 1) + " / " + pageCount), button -> { })
                .dimensions(left + 110, controlsY, 110, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Next"), button -> {
            page = Math.min(pageCount - 1, page + 1);
            rebuildWidgets();
        }).dimensions(left + 230, controlsY, 95, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Reset to alphabetical"), button -> {
            orderedIds.sort(Comparator.naturalOrder());
            saveOrder();
            rebuildWidgets();
        }).dimensions(left, height - 55, 160, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(left + 170, height - 55, 155, 20).build());
    }

    private List<String> filteredIds() {
        String query = searchText.toLowerCase(Locale.ROOT).trim();
        if (query.isEmpty()) return new ArrayList<>(orderedIds);
        return orderedIds.stream().filter(id -> id.toLowerCase(Locale.ROOT).contains(query)
                || displayName(id).toLowerCase(Locale.ROOT).contains(query)).toList();
    }

    private void move(String id, int direction) {
        int index = orderedIds.indexOf(id);
        int target = index + direction;
        if (index < 0 || target < 0 || target >= orderedIds.size()) return;
        String other = orderedIds.get(target);
        orderedIds.set(target, id);
        orderedIds.set(index, other);
        saveOrder();
        rebuildWidgets();
    }

    private void saveOrder() {
        config.enchantmentPriorityCsv = String.join(",", orderedIds);
        SmartAnvilConfig.save();
    }

    private static String displayName(String id) {
        String path = id.substring(id.indexOf(':') + 1).replace('_', ' ');
        String[] words = path.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (!result.isEmpty()) result.append(' ');
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xE0101010);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 16, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Search filters the view only; ↑ / ↓ changes the saved global priority."),
                width / 2, 31, 0xAAAAAA);
        context.drawTextWithShadow(textRenderer, Text.literal("ENCHANTMENT"), width / 2 - 165, 76, 0xBBBBBB);
        context.drawTextWithShadow(textRenderer, Text.literal("PRIORITY"), width / 2 + 54, 76, 0xBBBBBB);
        context.drawTextWithShadow(textRenderer, Text.literal("ORDER"), width / 2 + 103, 76, 0xBBBBBB);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        saveOrder();
        if (client != null) client.setScreen(parent);
    }
}
