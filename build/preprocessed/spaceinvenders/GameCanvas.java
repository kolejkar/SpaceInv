package spaceinvenders;


import java.io.IOException;
import javax.microedition.lcdui.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Karol
 */
public class GameCanvas extends Canvas implements CommandListener  {
    
    private Midlet midlet; 
    private Monster monsters[][];
    private Hero hero;
    private Armo armo;
    
    private int POT_W_RZEDZIE = 12;
    private int MAX_RZEDOW = 12;
    
    private int rzad = 1;
    
    private boolean isRun = false;
    
     public GameCanvas(Midlet _midlet) 
    {
	midlet = _midlet;
        addCommand( new Command( "Koniec", Command.EXIT, 0 ));
        setCommandListener(this);
        
        monsters = new Monster[POT_W_RZEDZIE][MAX_RZEDOW];
         for (int i = 0; i < POT_W_RZEDZIE; i++)
        {
            for (int j = 0; j < MAX_RZEDOW; j++)
            {
                monsters[i][j] = new Monster();
            }
        }
        hero = new Hero();
        hero.x = 6;
        hero.y = 13;
        
        armo = new Armo();
        armo.active = false;
        
        HeartBeat t = new HeartBeat();
        isRun = true;
        t.start();
    }

    protected void paint(Graphics g) {
        
       int screenHeight = getHeight();
       int screenWidth  = getWidth();

        g.setColor(0xffffff);	
	g.fillRect(0,0,screenWidth, screenHeight);
        
        for (int i = 0; i < POT_W_RZEDZIE; i++)
        {
            for (int j = 0; j < MAX_RZEDOW; j++)
            {
                if (monsters[i][j].visible == true)
                {
                    g.setColor(0x000000);	
                    g.fillTriangle(i *20, 20 * j + 40, 20 * i + 20, 20 * j +40 , i * 20 + 10, j * 20 + 20);
                }
            }
        }
        g.setColor(0xffff00);	
        g.fillTriangle(hero.x, hero.y * 20 + 20, hero.x + 20, hero.y * 20 + 20 , hero.x + 10, hero.y * 20);
        
        if (armo.active == true)
        {
            g.setColor(0xff00ff);
            g.fillRect(armo.x-2, armo.y-2, 4, 4);
        }
        
    }

    public void commandAction(Command c, Displayable d) {
       switch( c.getCommandType() ) {
            case Command.EXIT:
                isRun = false;
                midlet.destroyApp( false );
                midlet.notifyDestroyed();
            break;
       }
    }
    
    private boolean isAddRzad = true;
    
    protected void keyPressed(int kod)
    {
	
	switch(kod)
	{
	    case KEY_NUM4:  
                hero.x-=4; 
                break;
	    case KEY_NUM6: 
                hero.x+=4;
                break;
            case KEY_NUM5:
            {
                if (armo.active == false)
                {
                    armo.active = true;
                    armo.x = hero.x + 10;
                    armo.y = hero.y * 20;
                }
            }
            break;
	}
        
         if (hero.punkty % 5 == 0 && isAddRzad == true)
         {
            for (int i=0;i<POT_W_RZEDZIE;i++)
            {
                for (int j=1; j < rzad; j++)
                {
                    monsters[i][rzad] = monsters[i][rzad-1];
                }
            }
            rzad++;
            for (int i=0;i<POT_W_RZEDZIE;i++)
            {
               monsters[i][0].dead = false;  
            }
            isAddRzad = false;
        }
    }
    
     protected void update()
    {        
        for (int i=0;i< POT_W_RZEDZIE; i++)
        {
            for (int j=0; j < rzad; j++)
            {
            
                if (monsters[i][j].dead == false)
                    monsters[i][j].visible = true;
            }
        }
        
        if (armo.active == true)
        {
                armo.y-=3;
            if (armo.y < 0)
            {
                armo.active = false;
            }
            for (int i=0; i < POT_W_RZEDZIE; i++)
            {
                for (int j=0; j < rzad; j++)
                {
                
                    if (armo.IsColiderMonster(i * 20, j * 20 + 20) == true && monsters[i][j].visible == true)
                    {
                        monsters[i][j].dead = true;
                        monsters[i][j].visible = false;
                        hero.punkty += monsters[i][j].punkty;
                        armo.active = false;
                        isAddRzad = true;
                    }
                }
            }
        }    
        
        this.repaint();
        serviceRepaints();
    }
     
      private class HeartBeat extends Thread 
    {    
        public void run() {            
	    while(isRun==true)
	    {
		try 
		{
		    sleep(10);
		    update();
		}
		catch(Exception e)
		{
		    isRun = false;
		    System.out.println("Exception on update thread");
		}
	    }
	    System.out.println("Leaving update thread!");
        }
    }
    
}
