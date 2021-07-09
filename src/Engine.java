import java.util.LinkedList;
import java.util.Random;

public class Engine {
    int max_power_on_grid = 1; //Maximum 2^x power to spawn
    int[] points_on_grid = new int[16];
    static Random random = new Random();
    public Engine(){
        int roll1 = random.nextInt(16);
        int roll2 = random.nextInt(16);
        while (roll1 == roll2)
            roll2 = random.nextInt(16);
        points_on_grid[roll1] = 2;
        points_on_grid[roll2] = 2;
    }
    private Integer[] free_points_array(){
        LinkedList<Integer> free_points = new LinkedList<>();
        for (int i = 0;i < 16; i++)
            if (points_on_grid[i] == 0) free_points.push(i);
        return free_points.toArray(new Integer[free_points.size()]);
    }
    private void generate_number(){
        Integer[] points = free_points_array();
        Integer selected = points[random.nextInt(points.length)];
        points_on_grid[selected] = power(random.nextInt(max_power_on_grid)+1);
    }
    public boolean move(Directions direction){
        boolean moved = false;
        if (direction == Directions.UP) {
            for (int x = 0; x < 4; x++)//For each column
                for (int y1 = 1; y1 < 4; y1++)//For each point in column
                {
                    if (get_2d_point_value(x, y1) == 0)//We don't move zeros
                        continue;
                    for (int y2 = y1 - 1; y2 >= 0; y2--)//We check where we can move it
                    {
                        if (y2 == 0 && get_2d_point_value(x, y2) == 0)//If there is an empty cell near wall, we move our value
                        {
                            add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2));
                            moved = true;
                        } else if (get_2d_point_value(x, y2) == get_2d_point_value(x, y1))//We add together if they're of the same value
                        {
                            add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2));
                            moved = true;
                        } else if (get_2d_point_value(x, y2) != 0) //If there is value other than 0 in the way
                        {
                            if (y2 + 1 != y1) //We move our point to previous empty cell, if there was any, otherwise break
                            {
                                add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2 + 1));
                                moved = true;
                            }
                            break;
                        }
                    }
                }
        }
        //Same logic applies for other directions
        else if(direction == Directions.DOWN) {
            for (int x = 0; x < 4; x++)////For each column
                for (int y1 = 2; y1 >= 0; y1--)
                {
                    if (get_2d_point_value(x, y1) == 0)
                        continue;
                    for (int y2 = y1 + 1; y2 < 4; y2++)
                    {
                        if (y2 == 3 && get_2d_point_value(x, y2) == 0)
                        {
                            add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2));
                            moved = true;
                        } else if (get_2d_point_value(x, y2) == get_2d_point_value(x, y1))
                        {
                            add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2));
                            moved = true;
                        } else if (get_2d_point_value(x, y2) != 0) {
                            if (y2 - 1 != y1) {
                                add_together(get_2d_point_index(x, y1), get_2d_point_index(x, y2 - 1));
                                moved = true;
                            }
                            break;
                        }
                    }
                }
        }
        else if(direction == Directions.RIGHT) {
            for (int y = 0; y < 4; y++)////For each row
                for (int x1 = 2; x1 >= 0; x1--)
                {
                    if (get_2d_point_value(x1, y) == 0)
                        continue;
                    for (int x2 = x1 + 1; x2 < 4; x2++)
                    {
                        if (x2 == 3 && get_2d_point_value(x2, y) == 0)
                        {
                            add_together(get_2d_point_index(x1, y), get_2d_point_index(x2, y));
                            moved = true;
                        } else if (get_2d_point_value(x2, y) == get_2d_point_value(x1, y))
                        {
                            add_together(get_2d_point_index(x1, y), get_2d_point_index(x2, y));
                            moved = true;
                        } else if (get_2d_point_value(x2, y) != 0) {
                            if (x2 - 1 != x1) {
                                add_together(get_2d_point_index(x1, y), get_2d_point_index(x2-1, y));
                                moved = true;
                            }
                            break;
                        }
                    }
                }
        }
        else if(direction == Directions.LEFT) {
            for (int y = 0; y < 4; y++)//For each row
                for (int x1 = 1; x1 < 4; x1++)
                {
                    if (get_2d_point_value(x1, y) == 0)
                        continue;
                    for (int x2 = x1 - 1; x2 >= 0; x2--)
                    {
                        if (x2 == 0 && get_2d_point_value(x2, y) == 0)
                        {
                            add_together(get_2d_point_index(x1, y), get_2d_point_index(x2, y));
                            moved = true;
                        } else if (get_2d_point_value(x2, y) == get_2d_point_value(x1, y))
                        {
                            add_together(get_2d_point_index(x1, y), get_2d_point_index(x2, y));
                            moved = true;
                        } else if (get_2d_point_value(x2, y) != 0) {
                            if (x2 + 1 != x1) {
                                add_together(get_2d_point_index(x1, y), get_2d_point_index(x2+1, y));
                                moved = true;
                            }
                            break;
                        }
                    }
                }
        }
        if(moved)
            generate_number();
        return moved;
    }
    //Like move, but tests every direction to check if there is any possible move
    public boolean can_make_move(){
        boolean can_move = false;
        {
            for (int x = 0; x < 4; x++)
                for (int y1 = 1; y1 < 4; y1++)
                {
                    if (get_2d_point_value(x, y1) == 0)
                        continue;
                    for (int y2 = y1 - 1; y2 >= 0; y2--)
                    {
                        if(can_move)
                            return true;
                        else if (y2 == 0 && get_2d_point_value(x, y2) == 0)
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x, y2) == get_2d_point_value(x, y1))
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x, y2) != 0) {
                            if (y2 + 1 != y1) {
                                can_move = true;
                            }
                            break;
                        }
                    }
                }
        }
        {
            for (int x = 0; x < 4; x++)
                for (int y1 = 2; y1 >= 0; y1--)
                {
                    if (get_2d_point_value(x, y1) == 0)
                        continue;
                    for (int y2 = y1 + 1; y2 < 4; y2++)
                    {
                        if(can_move)
                            return true;
                        else if (y2 == 3 && get_2d_point_value(x, y2) == 0)
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x, y2) == get_2d_point_value(x, y1))
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x, y2) != 0) {
                            if (y2 - 1 != y1) {
                                can_move = true;
                            }
                            break;
                        }
                    }
                }
        }
        {
            for (int y = 0; y < 4; y++)
                for (int x1 = 2; x1 >= 0; x1--)
                {
                    if (get_2d_point_value(x1, y) == 0)
                        continue;
                    for (int x2 = x1 + 1; x2 < 4; x2++)
                    {
                        if(can_move)
                            return true;
                        else if (x2 == 3 && get_2d_point_value(x2, y) == 0)
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x2, y) == get_2d_point_value(x1, y))
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x2, y) != 0) {
                            if (x2 - 1 != x1) {
                                can_move = true;
                            }
                            break;
                        }
                    }
                }
        }
        {
            for (int y = 0; y < 4; y++)
                for (int x1 = 1; x1 < 4; x1++)
                {
                    if (get_2d_point_value(x1, y) == 0)
                        continue;
                    for (int x2 = x1 - 1; x2 >= 0; x2--)
                    {
                        if(can_move)
                            return true;
                        else if (x2 == 0 && get_2d_point_value(x2, y) == 0)
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x2, y) == get_2d_point_value(x1, y))
                        {
                            can_move = true;
                        } else if (get_2d_point_value(x2, y) != 0) {
                            if (x2 + 1 != x1) {
                                can_move = true;
                            }
                            break;
                        }
                    }
                }
        }
        return false;
    }
    private int get_2d_point_value(int x,int y){
        return points_on_grid[4 * y + x];
    }
    private int get_2d_point_index(int x,int y){
        return 4 * y + x;
    }
    private void add_together(int a,int b){
        points_on_grid[b] += points_on_grid[a];
        points_on_grid[a] = 0;
        if (points_on_grid[b] > power(max_power_on_grid))
            max_power_on_grid += 1;
    }
    private int power(int x){
        int a = 2;
        for (int i = 1;i < x;i++)
            a *= 2;
        return a;
    }
}
