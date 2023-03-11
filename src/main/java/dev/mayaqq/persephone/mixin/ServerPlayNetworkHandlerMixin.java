package dev.mayaqq.persephone.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;
    @Final
    @Shadow private MinecraftServer server;

    @Inject(method = "onClientStatus", at = @At(value = "JUMP", opcode = Opcodes.IFEQ, ordinal = 0), cancellable = true)
    private void modifyOnClientStatus(ClientStatusC2SPacket packet, CallbackInfo ci) {
        if (packet.getMode() == ClientStatusC2SPacket.Mode.PERFORM_RESPAWN) {
            if (this.player.notInAnyWorld) {
                this.player.notInAnyWorld = false;
                this.player = this.server.getPlayerManager().respawnPlayer(this.player, true);
                Criteria.CHANGED_DIMENSION.trigger(this.player, World.END, World.OVERWORLD);
            } else {
                if (this.player.getHealth() > 0.0F) {
                    return;
                }
                this.player = this.server.getPlayerManager().respawnPlayer(this.player, false);
                if (this.server.isHardcore() && this.player.getScoreboardTags().contains("firstSpawn")) {
                    this.player.changeGameMode(GameMode.SPECTATOR);
                    (this.player.getWorld().getGameRules().get(GameRules.SPECTATORS_GENERATE_CHUNKS)).set(false, this.server);
                }
                if (!this.player.getScoreboardTags().contains("firstSpawn")) {
                    this.player.getStatHandler().setStat(this.player, Stats.CUSTOM.getOrCreateStat(Stats.DEATHS), 0);
                }
                player.addScoreboardTag("firstSpawn");
            }

            ci.cancel();
            this.player.getStatHandler().sendStats(this.player);
        }
    }
}
