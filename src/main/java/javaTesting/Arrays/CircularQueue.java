package javaTesting.Arrays;


public class CircularQueue {


    private int maxSize;
    private int currentSize = 0;
    private int front;
    private int rear;
    private int queue[];
    private int element;

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }

    public int[] getQueue() {
        return queue;
    }

    public void setQueue(int[] queue) {
        this.queue = queue;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new int[this.maxSize];
        front = -1;
        rear = -1;
        element = 0;

    }

    public void enQueue(int element) {
        if (currentSize == 0) {

            try {
                if (queue.length == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                } else {
                    queue[rear + 1] = element;
                    front++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (currentSize < maxSize && currentSize > 0) {
            queue[rear + 1] = element;
        }

        if (currentSize == maxSize) {
            System.out.println("Queue full. Can't store any more elements");
            rear = maxSize - 2;
            currentSize = maxSize - 1;
        }

        rear++;
        currentSize++;


    }

    public void deQueue(int element) {

        if (currentSize == 0) {
            System.out.println("Queue is empty. Can't remove any elements");
        }

        if (currentSize > 0) {

            for (int k = 0; k < maxSize; k++) {
                for (int i = 0; i <= k; i++) {
                    if (queue[i] == element) {
                        queue[i] = 0;
                        for (int j = i; j < maxSize - 1; j++) {
                            //if (j < maxSize - 1) {
                            queue[j] = queue[j + 1];
                            queue[j + 1] = 0;
                            //}
                        }
                        rear--;
                        currentSize--;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        CircularQueue cq = new CircularQueue(5);

        for (int i = 0; i < cq.getMaxSize(); i++) {
            System.out.print(cq.getQueue()[i]);
            System.out.print(" ");
        }

        //System.out.println(cq);
        cq.enQueue(2);
        cq.enQueue(3);
        cq.enQueue(4);
        cq.enQueue(2);
        cq.enQueue(3);
        cq.enQueue(4);
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
        //System.out.println(cq);
        for (int i = 0; i < cq.getMaxSize(); i++) {
            System.out.print(cq.getQueue()[i]);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiii");
        cq.deQueue(3);
        //System.out.println(cq);
        for (int i = 0; i < cq.getMaxSize(); i++) {
            System.out.print(cq.getQueue()[i]);
            System.out.print(" ");
        }

        cq.enQueue(2);
        cq.enQueue(3);
        System.out.println();
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiii");
        for (int i = 0; i < cq.getMaxSize(); i++) {
            System.out.print(cq.getQueue()[i]);
            System.out.print(" ");
        }
        cq.deQueue(4);
        cq.deQueue(2);
        System.out.println();
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiii");
        for (int i = 0; i < cq.getMaxSize(); i++) {
            System.out.print(cq.getQueue()[i]);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiii");
        System.out.println("front : "+cq.getFront());
        System.out.println("rear : "+cq.getRear());
        System.out.println("currentSize : "+cq.getCurrentSize());
        System.out.println("maxSize : "+cq.getMaxSize());
    }

}


