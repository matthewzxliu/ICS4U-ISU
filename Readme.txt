If worked with a partner, list the responsibilities of each person:
- Matthew Liu
    - Bomb.java
    - PowerUp.java
    
- Jeevesh Balendra
    - Enemy.java
    - Score.java
    - CompareByName.java

Graphics
    - Jeevesh Balendra
        - Designed Wallpapers and chose images

Main.java
    - paintComponent()
        - gamestate 0, 2, 6, 7: Matthew Liu
        - gamestate 1, 3, 4, 6, 7: Jeevesh Balendra
    - explodeBomb()
        - Break walls: Matthew Liu
        - Kill player: Matthew Liu
        - Kill Enemies: Jeevesh Balendra
    - Matthew Liu
        - Main()
        - mousePressed()
        - keyPressed()
        - keyReleased()
        - move()
        - loadMaps()
        - generatePowerUps()
        - run()
        - playMusic()
        - playBackground()
        - main()
    - Jeevesh Balendra
        - checkCollision()
        - generateEnemies()
        - timesPlayed()
        - enterHighscoreName()
        - readHighscore()
        - reset()

Hints on how to play (skip levels?)
    - Press "-" to lose
    - Press "=" to win
    - Bed coordinates are printed in the console
    - Power up coordinates are printed in the console

Any functionalities missing from your original plan for the game
    - N/A.

Any additional functionalities added from your original plan
    - Score.java class
    - Comparator class

Known bugs / errors in your game
    - A few times while testing, the speed up powerup (coffee) did not not wear off. Hard to recreate.
    - When you have two coffee power ups, the character may sometimes get stuck in the walls. Can be avoided by holding down a direction at all times.

Any other important info for me to play/mark your game
    - N/A.