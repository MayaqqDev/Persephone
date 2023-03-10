package dev.mayaqq.persephone;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Persephone implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Persephone");

    @Override
    public void onInitialize() {
    }

    public interface PlayerExtensions {
        void setJoinInvulnerabilityTicks(int ticks);
    }
}
