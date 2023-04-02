package dev.mayaqq.persephone.mixin;

import dev.mayaqq.persephone.Persephone;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerManager.class)
abstract class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        if (!player.getScoreboardTags().contains("firstSpawn")) {
            Persephone.PlayerExtensions playerExtensions = (Persephone.PlayerExtensions) player;
            playerExtensions.setJoinInvulnerabilityTicks(0);
            player.damage(firstSpawn(), 1000);
        }
    }

    private static DamageSource firstSpawn() {
        return (new DamageSource("firstSpawn").setBypassesArmor().setBypassesProtection().setUnblockable());
    }
}