package hillClimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbing {

    static int yerDegistirme=0;
    public static void main(String[] args) {

        double[] totalProcessTime = new double[25];
        int[] totalRandomRestart = new int[25];
        int[] totalDeltaX = new int[25];

        int iteration = 0;
        int rr=0;

        for(int k=0;k<25;k++){
            long startTime = System.currentTimeMillis();

            while(true) {
                int[] chessBoard = new int[8];
                for (int i = 0; i < 8; i++) {
                    Random randInt = new Random();
                    int rand = randInt.nextInt(8);
                    chessBoard[i] = rand ;
                }
                System.out.println("Random Restart: "+rr);
                displayBoard(chessBoard);

                while (true) {
                    System.out.println("iteration: "+iteration);
                    List<int[]> successors = generateSuccessors(chessBoard);
                    int[] bestSuccessor = selectBestNode(successors);
                    if (boardScore(bestSuccessor) >= boardScore(chessBoard)) {
                        break;
                    }
                    System.out.println("Tahtanın skor değeri: "+boardScore(bestSuccessor));
                    copySuccessorToChessBoard(bestSuccessor, chessBoard);
                    displayBoard(chessBoard);
                    iteration++;
                }
                iteration=0;
                if (boardScore(chessBoard) == 0) {
                    totalDeltaX[k]=yerDegistirme;
                    System.out.println("Toplam yerdeğiştirme miktarı: "+yerDegistirme);
                    System.out.println("Toplam random restart sayısı: "+rr);
                    System.out.println("\n");
                    yerDegistirme=0;
                    break;
                }
                rr++;
            }
            totalRandomRestart[k]+=rr;
            rr=0;
            long endTime = System.currentTimeMillis();
            long totalTime = (endTime - startTime);
            double totalSeconds = totalTime / 1000.0;
            totalProcessTime[k] = totalSeconds;

        }
        int deltaX = 0, totalrr = 0;
        double processtime = 0;
        System.out.println("***Results***");
        System.out.println("Yer Değiştirme Sayısı" + "\t" + "Random Restart Sayısı" + "\t" + "İşlem Süreleri");
        for (int i = 0; i < 25; i++) {
            System.out.println(i + 1 + "\t\t\t" + totalDeltaX[i] + "\t\t\t\t\t" + totalRandomRestart[i] + "\t\t\t\t\t" + totalProcessTime[i]);
            deltaX += totalDeltaX[i];
            totalrr += totalRandomRestart[i];
            processtime += totalProcessTime[i];
        }
        System.out.println("Average:" + "\t" + deltaX / 25 + "\t\t\t\t\t" + totalrr / 25 + "\t\t\t\t" + processtime / 25);

    }

    public static List<int[]> generateSuccessors(int[] vertex){
        List<int[]> successors=new ArrayList<int[]>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(vertex[i]!=j){
                    int[] state=getCopyOfVertex(vertex);
                    state[i]=j;
                    successors.add(state);
                }
            }
        }
        return successors;
    }

    public static int[] getCopyOfVertex(int[] vertex){
        int[] copy=new int[8];
        for(int i=0;i<8;i++){
            copy[i]=vertex[i];
        }
        return copy;
    }

    public static int[] selectBestNode(List<int[]> successors){
        int min=boardScore(successors.get(0));
        int bestSuccessorScore = 0;
        for (int i = 1; i < successors.size(); i++) {
            int successorScore = boardScore(successors.get(i));
            if (successorScore < min) {
                min = successorScore;
                bestSuccessorScore = i;
            }
        }
        return successors.get(bestSuccessorScore);
    }

    public static int boardScore(int[] state){
        int score = 0;
        for (int i = 0; i < (state.length - 1); i++) {
            for (int j = i; j < state.length; j++) {
                if (i != j)
                {
                    //diagonal
                    if (Math.abs(state[i] - state[j]) == Math.abs(i - j)) {
                        score = score + 1;
                    }
                    //same col
                    else if ((state[i] == state[j])) {
                        score = score + 1;
                    }
                }
            }
        }
        return score;
    }
    public static void copySuccessorToChessBoard(int[] successor,int[] chessBoard){
        for(int i=0;i<8;i++){
            int x=Math.abs(successor[i]-chessBoard[i]);
            yerDegistirme+=x;
            chessBoard[i]=successor[i];
        }
    }
    public static void displayBoard(int[] board){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j  == board[i]) {
                    System.out.print("Q ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }
}