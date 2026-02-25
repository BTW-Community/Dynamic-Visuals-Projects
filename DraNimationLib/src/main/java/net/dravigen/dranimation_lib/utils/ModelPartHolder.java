package net.dravigen.dranimation_lib.utils;

@SuppressWarnings("unused")
public class ModelPartHolder {
	public float[] head = new float[]{0, 0, 0, 0, 0, 0};
	public float[] body = new float[]{0, 0, 0, 0, 12, 0};
	public float[] rArm = new float[]{0, 0, 0, -5, 2, 0};
	public float[] lArm = new float[]{0, 0, 0, 5, 2, 0};
	public float[] rLeg = new float[]{0, 0, 0, -1.9f, 12, 0.1f};
	public float[] lLeg = new float[]{0, 0, 0, 1.9f, 12, 0.1f};
	
	public float[] prevHead = new float[]{0, 0, 0, 0, 0, 0};
	public float[] prevBody = new float[]{0, 0, 0, 0, 12, 0};
	public float[] prevRArm = new float[]{0, 0, 0, -5, 2, 0};
	public float[] prevLArm = new float[]{0, 0, 0, 5, 2, 0};
	public float[] prevRLeg = new float[]{0, 0, 0, -1.9f, 12, 0.1f};
	public float[] prevLLeg = new float[]{0, 0, 0, 1.9f, 12, 0.1f};
	
	public void resetAnimationRotationPoints() {
		head = new float[]{head[0], head[1], head[2], 0, 0, 0};
		body = new float[]{head[0], head[1], head[2], 0, 12, 0};
		rArm = new float[]{head[0], head[1], head[2], -5, 2, 0};
		lArm = new float[]{head[0], head[1], head[2], 5, 2, 0};
		rLeg = new float[]{head[0], head[1], head[2], -1.9f, 12, 0.1f};
		lLeg = new float[]{head[0], head[1], head[2], 1.9f, 12, 0.1f};
	}
	
	public float[] getHead() {
		return head;
	}
	
	public float[] getBody() {
		return body;
	}
	
	public float[] getrArm() {
		return rArm;
	}
	
	public float[] getlArm() {
		return lArm;
	}
	
	public float[] getrLeg() {
		return rLeg;
	}
	
	public float[] getlLeg() {
		return lLeg;
	}
	
}
