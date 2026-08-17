public abstract class LibraryItem {
    private final int id;
    private final String title;
    private ItemStatus status;
    private String borrowerName;
    private int renewalCount;

    private static String libraryName = "Bayt Al Hekma";
    private static double administrativeCharge = 5.0;
    private static int itemsEverCatalogued = 0;

    public LibraryItem(int id, String title) {
        this.id = id;
        this.title = title;
        this.status = ItemStatus.AVAILABLE;
        this.borrowerName = "";
        this.renewalCount = 0;
        itemsEverCatalogued++;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public static String getLibraryName() {
        return libraryName;
    }

    public static double getAdministrativeCharge() {
        return administrativeCharge;
    }

    public static int getItemsEverCatalogued() {
        return itemsEverCatalogued;
    }

    public abstract double calculateFine(int daysOverdue);
    public abstract int getLoanPeriod();
    public abstract String getCategory();

    public void reserve() {
        status = ItemStatus.RESERVED;
    }

    public void markLost() {
        status = ItemStatus.LOST;
    }

    public void makeAvailable() {
        status = ItemStatus.AVAILABLE;
    }

    public boolean lend(String borrowerName) {
        if (status != ItemStatus.AVAILABLE) {
            return false;
        }

        status = ItemStatus.ON_LOAN;
        this.borrowerName = borrowerName;
        renewalCount = 0;
        return true;
    }

    public final void takeBack() {
        status = ItemStatus.AVAILABLE;
        borrowerName = "";
        renewalCount = 0;
    }

    public boolean addRenewal() {
        renewalCount++;
        return true;
    }

    public void display() {
        System.out.println(
            id + " | " +
            getCategory() + " | " +
            title + " | " +
            status + " | " +
            borrowerName + " | " +
            getLoanPeriod() + " days | " +
            calculateFine(1) + " EGP"
        );
    }
}
