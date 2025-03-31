package ass01;

public class BoidsSimulator {

    private BoidsModel model;

    private final UpdaterMaster updaterMaster;

    public BoidsSimulator(BoidsModel model) {
        this.model = model;
        updaterMaster = new UpdaterMaster();
    }

    public void runSimulation() {
        updaterMaster.update(model);
    }
}
