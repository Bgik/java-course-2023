package labirint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {

    private int[][] maze;
    private int rows;
    private int cols;
    private int startRow;
    private int startCol;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
        generateMaze();
        findStart();
    }

    private void generateMaze() {
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = random.nextInt(2); // 0 - путь, 1 - стена
            }
        }

        maze[0][0] = 0; // стартовая точка всегда является путем
        maze[rows - 1][cols - 1] = 0; // конечная точка всегда является путем
    }

    private void findStart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 0) {
                    startRow = i;
                    startCol = j;
                    return;
                }
            }
        }
    }

    public void solveMaze() {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];
        dfs(startRow, startCol, visited, path);
        printMaze(path);
    }

    private void dfs(int row, int col, boolean[][] visited, List<int[]> path) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || maze[row][col] == 1 || visited[row][col])
            return;

        visited[row][col] = true;
        path.add(new int[]{row, col});

        if (row == rows - 1 && col == cols - 1)
            return;

        dfs(row - 1, col, visited, path); // вверх
        dfs(row, col + 1, visited, path); // вправо
        dfs(row + 1, col, visited, path); // вниз
        dfs(row, col - 1, visited, path); // влево

        if (!path.isEmpty() && (row != rows - 1 || col != cols - 1))
            path.remove(path.size() - 1); // откатываемся назад при обратном проходе
    }

    public void printMaze(List<int[]> path) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 0)
                    System.out.print(" "); // путь
                else
                    System.out.print("#"); // стена
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Путь из точки А в точку Б:");
        for (int[] point : path) {
            System.out.println(point[0] + "," + point[1]);
        }
    }

    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
        maze.solveMaze();
    }
}
