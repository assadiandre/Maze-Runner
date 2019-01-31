
interface GameLoop {

    public void update();

}

public class Game implements Runnable {


    private long gameTime = 0;
    private int totalFrames = 0;

    public Game() {
    }

    public void run() {

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
                this.updateScore();
            }

            this.runGame();

            try {
                this.gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(this.gameTime);

            } catch (Exception e) {
            }

        }


    }


    public void updateScore() {

        if (Globals.gameStart) {
            if (Main.player.getSessionCharacter().equals("Runner")) {
                Globals.score += 1;
                Main.player.postScore(Globals.score);
            }
            else {
                Main.player.getScore();
            }
        }

    }

    public void runGame() {


        Globals.mainCharacter.update();
        if (Globals.data != null) {
            Globals.data.update();
        }

        if (Globals.data.isReady() && Globals.gameStart ) {
            Globals.mainOpponent.update();
            Globals.allBlocks.update();
            Main.player.postXCoord(Globals.mainCharacter.getXCoord());
            Main.player.postYCoord(Globals.mainCharacter.getYCoord());


            if (Globals.mainOpponent.getBounds().intersects(Globals.mainCharacter.getBounds())) {

                Globals.mainCharacter.setCollision();
                Main.player.postGameStatus(true);

                Main.player.getGameStatus(new CallBack() {
                    public void onComplete() {
                        System.out.println("GAME OVER");
                        Globals.page.setPage(2);
                        Globals.page.setVisible(true);
                        Globals.gameStart = false;
                        Globals.mainCharacter.setMovement(false);

                    }
                });
                //System.out.println("COLLISION GAME OVER");

            }

        }


    }

}








