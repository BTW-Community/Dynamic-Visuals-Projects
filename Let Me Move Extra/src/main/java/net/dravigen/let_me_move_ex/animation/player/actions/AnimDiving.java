package net.dravigen.let_me_move_ex.animation.player.actions;

import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.dranimation_lib.utils.ModelPartHolder;
import net.dravigen.let_me_move_ex.LmmEx_Settings;
import net.minecraft.src.*;

import static net.dravigen.dranimation_lib.utils.GeneralUtils.*;
import static net.dravigen.let_me_move_ex.LetMeMoveExAddon.crawl_key;

public class AnimDiving extends AnimBaseAction {
	public static ResourceLocation id = new ResourceLocation("LMMEx", "diving");
	
	public AnimDiving() {
		super(id, 0.8f, 0.005f, true);
	}
	
	@Override
	public boolean isGeneralConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		if (!LmmEx_Settings.SHOULD_DIVE.getBool()) {
			return false;
		}
		
		return !player.isEating() && !player.isOnLadder() && !player.onGround && !isInsideWater(player);
	}
	
	@Override
	public boolean isActivationConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		return crawl_key.pressed;
	}
	
	@Override
	public void renderAnimation(ModelBiped model, EntityLivingBase entity, float f, float g, float h, float i, float j,
			float u, float delta) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) entity;
		
		float n = customEntity.lmm_$getLeaningPitch(Minecraft.getMinecraft().getTimer().renderPartialTicks);
		float leaningPitch = Math.min(1.0F, n);
		
		ModelPartHolder partHolder = customEntity.lmm_$getParHolder();
		
		i = clampedI(i);
		
		float[] head = new float[]{
				leaningPitch > 0.0F ? lerpAngle(leaningPitch, partHolder.getHead()[0], -pi / 4) : j * (pi / 180.0f),
				i * (pi / 180.0f),
				0,
				0,
				0,
				0
		};
		float[] body = new float[]{0, 0, 0, 0, 12, 0};
		float[] rArm = new float[]{
				lerpF(leaningPitch, partHolder.getrArm()[0], -pi - 1.8707964F * method_2807(0) / method_2807(14.0F)),
				lerpF(leaningPitch, partHolder.getrArm()[1], 0),
				lerpF(leaningPitch, partHolder.getrArm()[2], 0),
				-5,
				2,
				0
		};
		float[] lArm = new float[]{
				lerpAngle(leaningPitch,
						  partHolder.getrArm()[0],
						  -pi + 1.8707964F * method_2807(0) / method_2807(14.0F)),
				lerpAngle(leaningPitch, partHolder.getrArm()[1], 0),
				lerpAngle(leaningPitch, partHolder.getrArm()[2], 0),
				5,
				2,
				0
		};
		
		float[] rLeg = new float[]{-1f + n / 2, 0, 0, -1.9f, 12, 0.1f};
		float[] lLeg = new float[]{-1f + n / 2, 0, 0, 1.9f, 12, 0.1f};
		
		this.breath(h, head, rArm, lArm, rLeg, lLeg, body);
		
		this.hurt(h, entity, head, body, rArm, lArm, rLeg, lLeg);
		
		if (leaningPitch > 0.0F) {
			rLeg[4] = 11.3f;
			lLeg[4] = 11.3f;
		}
		
		AnimationUtils.rotateAll(partHolder, model, head, body, rArm, lArm, rLeg, lLeg);
	}
	
	@Override
	public void updateLeaning(EntityLivingBase entity) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) entity;
		float pitch = (float) ((-entity.motionY) * 1.25f);
		float goal = (pitch > 1 ? 1 : pitch) + 1;
		
		entity.limbSwingAmount = 0;
		entity.limbSwing = 0;
		
		customEntity.lmm_$setLeaningPitch(goal);
	}
	
	@Override
	public boolean getHungerCost(EntityPlayer player, double distX, double distY, double distZ) {
		if (distY > 0) {
			float par2 = 1.33f * 0.05f * getHungerDifficultyMultiplier(player);
			player.addExhaustion(par2);
		}
		
		return false;
	}
	
	@Override
	public boolean customBodyHeadRotation(EntityLivingBase entity) {
		return false;
	}
}
