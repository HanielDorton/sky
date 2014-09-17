package com.haniel.game.Google;

public interface ActionResolver {
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void submitScoreGPGS(float score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS();
	  public void getAchievementsGPGS();
	}
