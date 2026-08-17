public class Book extends LibraryItem implements Renewable {
    private String author;
    private int pageCount;

    public Book(int id, String title, String author, int pageCount) {
        super(id, title);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public double calculateFine(int daysOverdue) {
        return daysOverdue * 5.0;
    }

    @Override
    public int getLoanPeriod() {
        return 14;
    }

    @Override
    public String getCategory() {
        return "Book";
    }

    @Override
    public boolean renew() {
        if (getStatus() != ItemStatus.ON_LOAN) {
            return false;
        }

        if (getRenewalCount() >= getRenewalLimit()) {
            return false;
        }

        addRenewal();
        return true;
    }

    @Override
    public int getRenewalLimit() {
        return 2;
    }
}
