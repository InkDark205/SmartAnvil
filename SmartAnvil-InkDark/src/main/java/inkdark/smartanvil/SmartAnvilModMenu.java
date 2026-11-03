package inkdark.smartanvil;

import inkdark.smartanvil.gui.SmartAnvilConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class SmartAnvilModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SmartAnvilConfigScreen::new;
    }
}
