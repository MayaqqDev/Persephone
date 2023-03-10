package dev.mayaqq.persephone.mixin;

import dev.mayaqq.persephone.Persephone;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements Persephone.PlayerExtensions {

    @Shadow private int joinInvulnerabilityTicks;

    public void setJoinInvulnerabilityTicks(int ticks) {
        joinInvulnerabilityTicks = ticks;
    }
}
