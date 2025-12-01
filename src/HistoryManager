package Streaming;

import java.util.Stack;

public class HistoryManager {
    private static Stack<History> stackHistory = new Stack<>();

    public static void currentHistory (String location, String task){
        stackHistory.push(new History(location, task));
    }

    public static void printHistory () {
        if (stackHistory.isEmpty()){
            System.out.println("No changes have been currently made.");
        }
        else {
            for (int i = 0; i < stackHistory.size(); i++) {
                System.out.println((i + 1) + " " + stackHistory.get(i));
            }
        }
    }

    public static Stack<History> getStackHistory() {
        return stackHistory;
    }

    public static void setStackHistory(Stack<History> stackHistory) {
        HistoryManager.stackHistory = stackHistory;
    }
}
