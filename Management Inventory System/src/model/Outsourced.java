package model;

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String partName, double price, int stock, int min, int max, String companyName) {
        super(id, partName, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter and Setter for companyName.
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
