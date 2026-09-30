package com.example.cpvpopt.mixin;

import com.example.cpvpopt.CpvpOptimizerClient;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class GameModeMixin {

	@Inject(method = "attack", at = @At("HEAD"))
	private void cpvpopt$onAttack(Player player, Entity target, CallbackInfo ci) {
		if (!CpvpOptimizerClient.optimizedCrystals) return;
		if (target instanceof EndCrystal) {
			// The attack packet is still sent to the server; we only remove it early on the client.
			target.discard();
		}
	}
}
