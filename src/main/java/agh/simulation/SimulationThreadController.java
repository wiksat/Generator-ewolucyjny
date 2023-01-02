package agh.simulation;

import agh.gui.GuiStatisticsModule;
import agh.gui.GuiWorldMap;
import agh.oop.AbstractWorldMap;
import agh.statistics.StatisticsModule;

public class SimulationThreadController {
    private final StatisticsModule statisticsModule;

    private Thread thread;
    private final SimulationEngine engine;

    public SimulationThreadController(AbstractWorldMap map, GuiWorldMap guiWorldMap, StatisticsModule statisticsModule, GuiStatisticsModule guiStatisticsModule) throws Exception {
        this.statisticsModule=statisticsModule;
        this.engine = new SimulationEngine(map, guiWorldMap, statisticsModule, guiStatisticsModule);

    }

    public void startSimulation() {
        this.thread = new Thread(this.engine);
        this.thread.start();
    }
    public StatisticsModule getStatisticsModule() {
        return statisticsModule;
    }
    public void stopSimulation() {
        if (this.thread != null && thread.isAlive()) {
            this.thread.interrupt();
        }
    }

    public SimulationEngine getEngine() {
        return engine;
    }
}
