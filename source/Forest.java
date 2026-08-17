public class Forest{

    private Tree[][] trees; //array of trees

    public Forest(int rows, int columns){
        trees = new Tree[rows][columns];

        for (int row = 0; row < rows; row++){
            for(int col = 0; col < columns; col++){
                trees[row][col] = new Tree();
            }
        }
    }

    //constructors
    public Tree getTree(int row, int col){
        return trees[row][col];
    }

    public int getRows(){
        return trees.length;
    }

    public int getColumns(){
        return trees[0].length;
    }

    public void displayForest(){
        
    }
}