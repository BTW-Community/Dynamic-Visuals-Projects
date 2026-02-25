package net.dravigen.dranimation_lib.mixin;

import net.dravigen.dranimation_lib.DraNimationLibAddon;
import net.dravigen.dranimation_lib.settings.DVS_ConfigManager;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	
	@Unique
	private static float lastPartialTicks = 0;
	
	@Unique
	private static float getDeltaTicks(Minecraft mc) {
		float currentPartialTicks = mc.getTimer().renderPartialTicks;
		float delta;
		
		if (currentPartialTicks >= lastPartialTicks) {
			delta = currentPartialTicks - lastPartialTicks;
		}
		else {
			delta = (1.0f - lastPartialTicks) + currentPartialTicks;
		}
		
		lastPartialTicks = currentPartialTicks;
		
		return Math.min(delta, 1.0f);
	}
	
	@Inject(method = "runGameLoop", at = @At("HEAD"))
	private void updateDelta(CallbackInfo ci) {
		AnimationUtils.delta = (float) (getDeltaTicks((Minecraft) (Object) this) /
				DraNimationLibAddon.SMOOTHNESS.getDouble());
	}
	
	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	private void saveSettings(CallbackInfo ci) {
		DVS_ConfigManager.save();
	}
}
