package elevator;


/**
 * @author: dongli
 * @since: 2026/4/27 15:09
 * @description:
 */

public class MyQueue<T> {

    private volatile int taskCount;

    private volatile Task header = null;

    private volatile Task tail = null;
    private volatile boolean empty = false;

    public synchronized void addTask(T ob) {
        if (header == null) {
            header = new Task();
            header.ob = ob;
            tail = header;
        } else {
                Task task = new Task();
                task.ob = ob;
                header.next = task;
                taskCount ++ ;
        }
        taskCount++ ;
        if (empty) {
            notify();
        }
    }

    public synchronized T popTask() throws Exception {
        if (header == null ) {
            empty = true;
            wait();
        }
        Task tmp = header;
        header = header.next;
        return tmp.ob;
    }


    class Task{
        T ob;
        Task next = null;
    }
}
