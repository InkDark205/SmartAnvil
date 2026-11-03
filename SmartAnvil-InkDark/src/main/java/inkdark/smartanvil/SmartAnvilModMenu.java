package inkdark.smartanvil;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import inkdark.smartanvil.gui.SmartAnvilConfigScreen;

public final class SmartAnvilModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SmartAnvilConfigScreen::new;
    }
}
