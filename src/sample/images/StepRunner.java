package sample.images;

import sample.Controller;

public class StepRunner implements Runnable {

    Controller controller;

    public StepRunner(Controller stepController)
    {
        controller = stepController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                controller.Step();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
