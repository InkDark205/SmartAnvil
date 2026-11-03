package inkdark.smartanvil;

import inkdark.smartanvil.config.SmartAnvilConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class SmartAnvilClient implements ClientModInitializer {
    public static final String MOD_ID = "smartanvil";
    private static KeyMapping openConfigKey;

    @Override
    public void onInitializeClient() {
        SmartAnvilConfig.load();
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.smartanvil.open_config",
                GLFW.GLFW_KEY_O,
                KeyMapping.Category.MISC
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.consumeClick()) {
                if (client.player != null) {
                    client.setScreen(new inkdark.smartanvil.gui.SmartAnvilConfigScreen(client.screen));
                }
            }
        });
    }
}
