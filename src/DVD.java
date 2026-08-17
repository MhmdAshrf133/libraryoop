public class DVD extends LibraryItem {
    private int runtime;

    public DVD(int id, String title, int runtime) {
        super(id, title);
        this.runtime = runtime;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public double calculateFine(int daysOverdue) {
        return daysOverdue * 15.0;
    }

    @Override
    public int getLoanPeriod() {
        return 3;
    }

    @Override
    public String getCategory() {
        return "DVD";
    }
}
