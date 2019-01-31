import javax.swing.*;
import java.awt.*;


public class BlockMap extends JComponent implements GameLoop {



    int[][] allBlocks = new int[32][4];



    public BlockMap() {

        this.setSize(700,720);

        //1A
        allBlocks[0] = new int[] {60,60,180,20};
        allBlocks[1] = new int[] {60,80,20,160};
        //1B
        allBlocks[2] = new int[] {460,60,180,20};
        allBlocks[3] = new int[] {620,80,20,160};
        //1C
        allBlocks[4] = new int[] {620,460,20,160};
        allBlocks[5] = new int[] {460,620,180,20};
        //1D
        allBlocks[6] = new int[] {60,460,20,160};
        allBlocks[7] = new int[] {60,620,180,20};

        //2A
        allBlocks[8] = new int[] {260,120,180,20};
        //2B
        allBlocks[9] = new int[] {560,260,20,180};
        //2C
        allBlocks[10] = new int[] {260,560,180,20};
        //2D
        allBlocks[11] = new int[] {120,260,20,180};

        //3A
        allBlocks[12] = new int[] {180,180,100,20};
        allBlocks[13] = new int[] {180,200,20,80};
        //3B
        allBlocks[14] = new int[] {420,180,100,20};
        allBlocks[15] = new int[] {500,200,20,80};
        //3C
        allBlocks[16] = new int[] {500,420,20,80};
        allBlocks[17] = new int[] {420,500,100,20};
        //3D
        allBlocks[18] = new int[] {180,420,20,80};
        allBlocks[19] = new int[] {180,500,100,20};
        //4A
        allBlocks[20] = new int[] {240,240,220,20};
        allBlocks[21] = new int[] {240,260,20,60};
        allBlocks[22] = new int[] {440,260,20,60};
        //4B
        allBlocks[23] = new int[] {240,440,220,20};
        allBlocks[24] = new int[] {240,380,20,60};
        allBlocks[25] = new int[] {440,380,20,60};
        //5A
        allBlocks[26] = new int[] {300,300,20,100};
        //5B
        allBlocks[27] = new int[] {380,300,20,100};
        //boundaries
        allBlocks[28] = new int[] {0,0,700,20};
        allBlocks[29] = new int[] {0,680,700,20};
        allBlocks[30] = new int[] {0,20,20,660};
        allBlocks[31] = new int[] {680,20,20,660};
    }

    public void paint(Graphics g) {

        g.setColor(Color.blue);
        for (int i = 0; i < allBlocks.length; i++) {
            g.fillRect(allBlocks[i][0], allBlocks[i][1], allBlocks[i][2], allBlocks[i][3]);
        }



    }


    @Override
    public void update() {
        super.repaint();

        Rectangle block;

        for (int i = 0; i < allBlocks.length; i++) {

            block = new Rectangle(allBlocks[i][0],allBlocks[i][1],allBlocks[i][2],allBlocks[i][3]);

            if (block.intersects(Globals.mainCharacter.getBounds())){
                Globals.mainCharacter.setCollision();
            }


        }


    }
}
