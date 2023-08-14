import java.util.concurrent.*;

public class Main {
    public static int value = 0;
    public static void outOfSyncExpample(){
        //reader-demon-thread
        Thread reader = new Thread(() -> {
            int temp = 0;
            while (true) {
                if (temp != value) {
                    temp = value;
                    System.out.println("value = " + value);
                }
            }
        });
        reader.setDaemon(true);

        //writer-active-thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                value++;
                System.out.println("value updated: " + value);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        });

        reader.start();
        writer.start();
    }

    public static void simpleThreadsExample(){
        Runnable fstTask = () -> {
            for(int i = 0; i < 10; i++)
                System.out.println(i);
        };

        Runnable secTask = () -> {
            for(int i = 10; i < 20; i++)
                System.out.println(i);
        };

        Thread boy = new Thread(fstTask);
        Thread girl = new Thread(secTask);

        boy.start();
        girl.start();
    }

    public static void threadNames(){
        Runnable myName = () -> {
            System.out.println(Thread.currentThread().getName());
        };

        for(int i = 0; i < 5; i++){
            Thread t = new Thread(myName);
            t.start();
        }
    }

    public static void executorsExample(){
        Runnable introduceYourself = () -> {
            System.out.println(
                    "Name:" + Thread.currentThread().getName()
                            + " ID:" + Thread.currentThread().getId());
        };

        ExecutorService cached = Executors.newCachedThreadPool();
        ExecutorService fixed  = Executors.newFixedThreadPool(5);
        ExecutorService single = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            cached.submit(introduceYourself);
            fixed.submit(introduceYourself);
            single.submit(introduceYourself);
        }
    }

    public static void futureExample(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> c = () -> {
            int sum = -10000;
            for(int i = 0; i < 10000; i++)
                sum++;
            return sum;
        };

        Future<Integer> res = executor.submit(c);

        System.out.println(res.isDone());
        //Integer value = res.get();       //try-catch

        System.out.println(res.isDone());
        System.out.println(value);
    }

    public static void main(String[] args) {
        //simpleThreadsExample();
        //threadNames();
        //executorsExample();
        //futureExample();
        //outOfSyncExpample();
    }
}
