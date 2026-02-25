package net.dravigen.let_me_move.animation.player.poses;

import net.dravigen.dranimation_lib.interfaces.ICustomMovementEntity;
import net.dravigen.dranimation_lib.utils.AnimationUtils;
import net.dravigen.dranimation_lib.utils.ModelPartHolder;
import net.minecraft.src.*;

import static net.dravigen.dranimation_lib.utils.GeneralUtils.pi;

public class AnimLowFalling extends AnimCommon {
	public static final ResourceLocation id = new ResourceLocation("LMM", "lowFalling");
	public final static int minFallHeight = 3;
	
	public AnimLowFalling() {
		super(id, 1.8f, 0.02f);
	}
	
	@Override
	public boolean isGeneralConditonsMet(EntityPlayer player, AxisAlignedBB axisAlignedBB) {
		return player.fallDistance >= minFallHeight &&
				player.fallDistance < AnimHighFalling.minFallHeight &&
				!player.capabilities.isFlying;
	}
	
	@Override
	public void renderAnimation(ModelBiped model, EntityLivingBase entity, float f, float g, float h, float i, float j,
			float u, float delta) {
		ICustomMovementEntity customEntity = (ICustomMovementEntity) entity;
		ModelPartHolder partHolder = customEntity.lmm_$getParHolder();
		
		i = clampedI(i);
		
		float v = h % 200 / 1.75f;
		float sin = MathHelper.sin(v);
		float cos = MathHelper.cos(v);
		float cos1 = MathHelper.cos(v + 2);
		float sin1 = MathHelper.sin(v + 2);
		
		float[] head = new float[]{
				j * (pi / 180.0f), i * (pi / 180.0f), 0, 0, 0, 0
		};
		float[] body = new float[]{0, 0, 0, 0, 12, 0};
		float[] rArm = new float[]{cos * 0.65f, 0, 1.75f + sin * 0.65f, -5, 2, 0};
		float[] lArm = new float[]{cos1 * 0.65f, 0, -1.75f - sin1 * 0.65f, 5, 2, 0};
		float[] rLeg = new float[]{-sin * 0.5f, 0, 0, -1.9f, 12, 0.1f};
		float[] lLeg = new float[]{sin * 0.5f, 0, 0, 1.9f, 12, 0.1f};
		
		AnimationUtils.rotateAll(partHolder, model, head, body, rArm, lArm, rLeg, lLeg);
	}
}
