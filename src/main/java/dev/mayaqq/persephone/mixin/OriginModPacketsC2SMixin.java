package dev.mayaqq.persephone.mixin;

import dev.mayaqq.persephone.Persephone;
import io.github.apace100.origins.networking.ModPacketsC2S;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModPacketsC2S.class)
public class OriginModPacketsC2SMixin {
    @Inject(method = "confirmOrigin", at = @At("HEAD"))
    private static void confirmOrigin(ServerPlayerEntity player, OriginLayer layer, Origin origin, CallbackInfo ci) {
        if (!player.getScoreboardTags().contains("firstSpawn")) {
            Persephone.PlayerExtensions playerExtensions = (Persephone.PlayerExtensions) player;
            playerExtensions.setJoinInvulnerabilityTicks(0);
            player.damage(firstSpawn(), 1000);
            if (player.getWorld().getGameRules().getBoolean(GameRules.DO_IMMEDIATE_RESPAWN)) {
                player.addScoreboardTag("firstSpawn");
            }
        }
    }
    private static DamageSource firstSpawn() {
        return (new DamageSource("firstSpawn").setBypassesArmor().setBypassesProtection().setUnblockable());
    }
}