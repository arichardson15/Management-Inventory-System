package model;

public class InHouse extends Part {
private int machineId;

public InHouse(int id, String partName, double price, int stock, int min, int max, int machineId) {
    super(id, partName, price, stock, min, max);
    this.machineId = machineId;

}
    /**
     * Getter and Setter for machineId.
     * @return
     */
    public int getMachineId() {
        return machineId;
    }
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
