package net.dravigen.let_me_move_ex.mixin.client.render;

import btw.entity.model.PlayerArmorModel;
import net.dravigen.dranimation_lib.animation.BaseAnimation;
import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.dranimation_lib.utils.GeneralUtils;
import net.dravigen.let_me_move.LetMeMoveAddon;
import net.dravigen.let_me_move.animation.player.poses.AnimHighFalling;
import net.dravigen.let_me_move_ex.animation.player.actions.AnimCrawling;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public abstract class ModelBipedMixin extends ModelBase {
	
	@Inject(method = "render", at = @At("HEAD"))
	private void rotateBody(Entity entity, float f, float g, float h, float i, float j, float u, CallbackInfo ci) {
		if (!AnimationUtils.extraIsPresent) return;
		
		if (!(entity instanceof EntityPlayer player) || (ModelBiped) (Object) this instanceof PlayerArmorModel) return;
		
		ICustomMovementEntity customEntity = (ICustomMovementEntity) player;
		
		LetMeMoveAddon.updateModelInfo(entity, player, customEntity, (ModelBiped) (Object) this);
		
		BaseAnimation animation = customEntity.lmm_$getAnimation();
		
		if (animation == null) return;
		
		float leaningPitch = customEntity.lmm_$getLeaningPitch(Minecraft.getMinecraft().getTimer().renderPartialTicks);
		
		float delta = AnimationUtils.delta;
		
		float prevXRotation;
		float prevYRotation;
		float prevZRotation;
		float prevOffset;
		
		float[] renderRotOff = customEntity.lmm_$getRenderRotOff();
		
		if (animation.needYOffsetUpdate) {
			if (customEntity.lmm_$isAnimation(AnimHighFalling.id)) {
				prevOffset = GeneralUtils.incrementUntilGoal(renderRotOff[0], 0.5f, 0.4f * delta);
				prevYRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[2],
																	 (12f * leaningPitch) % 360,
																	 0.3f * delta);
				prevZRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[3],
																	 (22.5f * leaningPitch) % 360,
																	 0.3f * delta);
				prevXRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[1],
																	 (45f * leaningPitch) % 360,
																	 0.3f * delta);
			}
			else {
				prevOffset = animation.yOffset != 0 ? animation.yOffset : 1.98f - entity.height;
				prevYRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[2], 0, 0.2f * delta);
				prevZRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[3], 0, 0.2f * delta);
				prevXRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[1],
																	 90 * leaningPitch,
																	 (customEntity.lmm_$isAnimation(AnimCrawling.id)
																	  ? 0.8f
																	  : 0.4f) * delta);
			}
		}
		else {
			prevOffset = GeneralUtils.incrementUntilGoal(renderRotOff[0], 0, 0.5f * delta);
			prevYRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[2], 0, 0.75f * delta);
			prevZRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[3], 0, 0.75f * delta);
			prevXRotation = GeneralUtils.incrementAngleUntilGoal(renderRotOff[1], 0, 0.75f * delta);
		}
		
		float[] newRenderRotOff = new float[]{prevOffset, prevXRotation, prevYRotation, prevZRotation};
		
		customEntity.lmm_$setRenderRotOff(newRenderRotOff);
		
		GL11.glTranslatef(0, prevOffset, 0);
		GL11.glRotatef(prevYRotation, 0, 1, 0);
		GL11.glRotatef(prevZRotation, 0, 0, 1);
		GL11.glRotatef(prevXRotation, 1, 0, 0);
		
		if (customEntity.lmm_$isAnimation(AnimHighFalling.id)) GL11.glTranslatef(0, -prevOffset, 0);
	}
}