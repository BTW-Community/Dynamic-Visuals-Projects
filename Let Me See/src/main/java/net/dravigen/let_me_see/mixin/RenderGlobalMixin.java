package net.dravigen.let_me_see.mixin;

import net.dravigen.let_me_see.config.LMS_Settings;
import net.minecraft.src.*;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
	
	@Shadow
	private Minecraft mc;
	
	@Redirect(method = "renderEntities", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;thirdPersonView:I", opcode = Opcodes.GETFIELD, ordinal = 0))
	private int renderPlayerIn1stPerson(GameSettings instance) {
		if (LMS_Settings.FIRST_PERSON_MODEL.getBool() &&
				!(mc.currentScreen instanceof GuiContainerCreative || mc.currentScreen instanceof GuiInventory)) {
			return 1;
		}
		
		return instance.thirdPersonView;
	}
}
