package dev.mayaqq.persephone;

import io.github.apace100.origins.networking.ModPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Persephone implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Persephone");

    @Override
    public void onInitialize() {
        LOGGER.info("The Queen of the underworld has awoken!");
    }

    public interface PlayerExtensions {
        void setJoinInvulnerabilityTicks(int ticks);

    }
}
