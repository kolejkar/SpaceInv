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
public class Armo {
        public int x,y;
        
        public boolean IsColiderMonster(int monster_x, int monster_y)
        {
            if (x > monster_x && x < (monster_x + 20) && y > monster_y && y < (monster_y + 20))
                return true;
            return false;
        }
        
        public boolean active;
}
