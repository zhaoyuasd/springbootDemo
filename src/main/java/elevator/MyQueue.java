package elevator;


/**
 * @author: dongli
 * @since: 2026/4/27 15:09
 * @description:
 */

public class MyQueue<T> {

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
                tail.next = task;
                tail = task;
        }
        if (empty) {
            notify();
            empty = false;
        }
    }

    public synchronized T popTask() throws Exception {
        if (header == null ) {
            empty = true;
            wait();
        }
        T t= header.ob;
        header = header.next;
        return t;
    }


    class Task{
        T ob;
        Task next = null;
    }
}
