public class Member {
    private String name;
    private String membershipId;
    private MembershipType membershipType;
    private double balance;
    private int itemsHeld;

    public Member(String name, String membershipId, MembershipType membershipType) {
        this(name, membershipId, membershipType, 0);
    }

    public Member(String name, String membershipId, MembershipType membershipType, double balance) {
        this.name = name;
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.balance = balance;
        this.itemsHeld = 0;
    }

    public String getName() {
        return name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public double getBalance() {
        return balance;
    }

    public int getItemsHeld() {
        return itemsHeld;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canBorrow() {
        return itemsHeld < 3 && balance <= 100;
    }

    public boolean chargeFine(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    public boolean payFine(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public void recordBorrowing() {
        itemsHeld++;
    }

    public boolean recordReturn() {
        if (itemsHeld <= 0) {
            return false;
        }

        itemsHeld--;
        return true;
    }
}
