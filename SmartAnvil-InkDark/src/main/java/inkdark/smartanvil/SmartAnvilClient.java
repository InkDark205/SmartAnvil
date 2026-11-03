package inkdark.smartanvil;

import inkdark.smartanvil.config.SmartAnvilConfig;
import inkdark.smartanvil.gui.SmartAnvilConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class SmartAnvilClient implements ClientModInitializer {
    public static final String MOD_ID = "smartanvil";
    private static KeyBinding openConfigKey;

    @Override
    public void onInitializeClient() {
        SmartAnvilConfig.load();
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.smartanvil.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new SmartAnvilConfigScreen(client.currentScreen));
                }
            }
        });
    }
}
