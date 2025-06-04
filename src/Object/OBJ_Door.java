package Object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(GamePanel gp){
        this.gp=gp;
        name ="Door";
        try {
            image =ImageIO.read(getClass().getResourceAsStream("/Objects/door.png"));
            image =   uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            System.out.println(" error getting image ressource");
        }
        collision = true;
    }
}
