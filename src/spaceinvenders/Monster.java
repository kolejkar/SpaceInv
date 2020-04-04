/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvenders;

/**
 *
 * @author Karol
 */
public class Monster {
    public int punkty;
    public boolean visible;
    public boolean dead;
    
    public Monster()
    {
        punkty = 1;
        visible = false;
        dead  = false;
    }
    
    public Monster(int points)
    {
        punkty = points;
        visible = false;
        dead  = false;
    }
}
