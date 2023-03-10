package dev.mayaqq.persephone.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mayaqq.persephone.Persephone.LOGGER;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @Mutable
    @Shadow @Final private boolean isHardcore;
    @Inject(method = "init", at = @At("HEAD"))
    private void overrideHardcore(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        LOGGER.info("Player has played for " + client.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME)) + " ticks");
        // send the get stats packet
        client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
        if (!client.player.getScoreboardTags().contains("firstSpawn") && client.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME)) < 50) {
            this.isHardcore = false;
        }
    }

    @ModifyVariable(method = "<init>(Lnet/minecraft/text/Text;Z)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static boolean persephone$isHardcore(boolean isHardcore) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
        if (!client.player.getScoreboardTags().contains("firstSpawn") && client.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME)) < 50) {
            return false;
        }
        return isHardcore;
    }
}
