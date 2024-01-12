public class PowerUps {
    
    private int playerVel;
    private int enemyVel;

    public int getPlayerVel()
    {
        return playerVel;
    }

    public int getEnemyVel()
    {
        return enemyVel;
    }

    public PowerUps(int playerVel, int enemyVel)
    {
        this.playerVel = playerVel;
        this.enemyVel = enemyVel;
    }

    public int getSpeedPowerUp()
    {
        return playerVel += 2;
    }
    
    public int removeSpeedPowerUp()
    {
        return playerVel -= 2;
    }
    
    public int getSlowPowerUp()
    {
        return enemyVel += 5;
    }

    public int removeSlowPowerUp()
    {
        return enemyVel -= 2;
    }
}
