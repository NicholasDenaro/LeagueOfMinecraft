package me.bittnerdenaro.lom.entity;

public interface Healthable
{
	public void damage(double amount);
	public double getHealth();
	public void setHealth(double health);
}
