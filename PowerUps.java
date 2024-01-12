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
        return playerVel += 5;
    }
    
    public int getEnemySpeed()
    {
        return enemyVel += 5;
    }
}
