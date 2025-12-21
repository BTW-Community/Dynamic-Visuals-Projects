package net.dravigen.let_me_move.mixin.client;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityClientPlayerMP.class)
public abstract class EntityClientPlayerMPMixin extends EntityPlayerSP {
	
	public EntityClientPlayerMPMixin(Minecraft par1Minecraft, World par2World, Session par3Session, int par4) {
		super(par1Minecraft, par2World, par3Session, par4);
	}
	
	@Inject(method = "sendMotionUpdates", at = @At(value = "HEAD"), cancellable = true)
	private void preventIllegalStance(CallbackInfo ci) {
		if (this.posY - this.boundingBox.minY < 0.2) {
			ci.cancel();
		}
	}
}
