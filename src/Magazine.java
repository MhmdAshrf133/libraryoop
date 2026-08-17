public class Magazine extends LibraryItem implements Renewable {
    private int issueNumber;

    public Magazine(int id, String title, int issueNumber) {
        super(id, title);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public double calculateFine(int daysOverdue) {
        double fine = daysOverdue * 3.0;

        if (fine > 30.0) {
            fine = 30.0;
        }

        return fine;
    }

    @Override
    public int getLoanPeriod() {
        return 7;
    }

    @Override
    public String getCategory() {
        return "Magazine";
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
        return 1;
    }
}
