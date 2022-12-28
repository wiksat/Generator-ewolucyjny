package agh.simulation;

import agh.gui.GuiWorldMap;
import agh.oop.AbstractWorldMap;

public class SimulationThreadController {

    private Thread thread;
    private SimulationEngine engine;

    public SimulationThreadController(AbstractWorldMap map, GuiWorldMap guiWorldMap) {
        this.engine = new SimulationEngine(map, guiWorldMap);

    }

    public void startSimulation() {
        this.thread = new Thread(this.engine);
        this.thread.start();
        //observary
    }

    public void stopSimulation() {
        if (this.thread != null && thread.isAlive()) {
            this.thread.interrupt();
            // observary
        }
        return;

    }

    public SimulationEngine getEngine() {
        return engine;
    }
}
