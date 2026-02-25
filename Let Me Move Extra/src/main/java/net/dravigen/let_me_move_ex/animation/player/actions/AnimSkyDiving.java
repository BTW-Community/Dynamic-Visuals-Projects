package net.dravigen.let_me_move_ex.animation.player.actions;

import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.dranimation_lib.utils.ModelPartHolder;
import net.dravigen.let_me_move_ex.LmmEx_Settings;
import net.minecraft.src.*;

import static net.dravigen.dranimation_lib.utils.GeneralUtils.pi;
import static net.dravigen.let_me_move_ex.LetMeMoveExAddon.crawl_key;

public class AnimSkyDiving extends AnimBaseAction {
	public static final ResourceLocation id = new ResourceLocation("LMMEx", "skyDiving");
	
	public AnimSkyDiving() {
		super(id, 1f, 0.2f, true);
	}
	
	@Override
	public boolean isGeneralConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		if (!LmmEx_Settings.SHOULD_SKY_DIVE.getBool()) {
			return false;
		}
		
		return !player.isEating() &&
				player.fallDistance >= 10 &&
				!player.capabilities.isFlying &&
				!player.doesStatusPreventSprinting();
	}
	
	@Override
	public boolean isActivationConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		return !crawl_key.pressed && player.isSneaking();
	}
	
	@Override
	public void renderAnimation(ModelBiped model, EntityLivingBase entity, float f, float g, float h, float i, float j,
			float u, float delta) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) entity;
		ModelPartHolder partHolder = customEntity.lmm_$getParHolder();
		
		boolean fMove = entity.moveForward > 0;
		
		i = clampedI(i);
		
		float[] head = new float[]{
				-0.5f, i * (pi / 180.0f), 0, 0, 0, 0
		};
		float[] body = new float[]{0, 0, 0, 0, 12, 0};
		float[] rArm = new float[]{0.5f, 0, fMove ? 0.5f : 2, -5, 2, 0};
		float[] lArm = new float[]{0.5f, 0, fMove ? -0.5f : -2, 5, 2, 0};
		float[] rLeg = new float[]{0.5f, 0, 0.15f, -1.9f, 12, 0.1f};
		float[] lLeg = new float[]{0.5f, 0, -0.15f, 1.9f, 12, 0.1f};
		
		this.hurt(h, entity, head, body, rArm, lArm, rLeg, lLeg);
		
		AnimationUtils.rotateAll(partHolder, model, head, body, rArm, lArm, rLeg, lLeg);
	}
	
	@Override
	public void updateLeaning(EntityLivingBase entity) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) entity;
		float goal = entity.moveForward > 0 ? 1.2f : 1;
		
		customEntity.lmm_$setLeaningPitch(goal);
	}
	
	@Override
	public boolean getHungerCost(EntityPlayer player, double distX, double distY, double distZ) {
		int total = Math.round(MathHelper.sqrt_double(distX * distX + distY * distY + distZ * distZ) * 100.0f);
		
		if (total > 0) {
			float par2 = 0.05f * total * 0.001f * getHungerDifficultyMultiplier(player);
			
			if (player.moveForward > 0) {
				par2 *= 1.5f;
				player.addExhaustion(par2);
			}
			else {
				player.addExhaustionWithoutVisualFeedback(par2);
			}
			
			player.addStat(StatList.distanceFallenStat, total);
		}
		
		return true;
	}
}
