public class Library {
    private LibraryItem[] catalogue;
    private Member[] members;
    private int itemCount;
    private int memberCount;

    public Library(int itemCapacity, int memberCapacity) {
        catalogue = new LibraryItem[itemCapacity];
        members = new Member[memberCapacity];
        itemCount = 0;
        memberCount = 0;
    }

    public boolean addItem(LibraryItem item) {
        if (itemCount >= catalogue.length || findItem(item.getId()) != null) {
            return false;
        }

        catalogue[itemCount] = item;
        itemCount++;
        return true;
    }

    public boolean addMember(Member member) {
        if (memberCount >= members.length || findMember(member.getMembershipId()) != null) {
            return false;
        }

        members[memberCount] = member;
        memberCount++;
        return true;
    }

    public LibraryItem findItem(int id) {
        for (int i = 0; i < itemCount; i++) {
            if (catalogue[i].getId() == id) {
                return catalogue[i];
            }
        }

        return null;
    }

    public Member findMember(String id) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMembershipId().equals(id)) {
                return members[i];
            }
        }

        return null;
    }

    public void viewCatalogue() {
        for (int i = 0; i < itemCount; i++) {
            catalogue[i].display();
        }
    }

    public void viewByStatus(ItemStatus status) {
        boolean found = false;

        for (int i = 0; i < itemCount; i++) {
            if (catalogue[i].getStatus() == status) {
                catalogue[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items found.");
        }
    }

    public boolean borrowItem(int itemId, String memberId) {
        LibraryItem item = findItem(itemId);
        Member member = findMember(memberId);

        if (item == null || member == null) {
            System.out.println("Item or member not found.");
            return false;
        }

        if (item.getStatus() != ItemStatus.AVAILABLE) {
            System.out.println("Item is not available.");
            return false;
        }

        if (!member.canBorrow()) {
            System.out.println("Member cannot borrow.");
            return false;
        }

        item.lend(member.getName());
        member.recordBorrowing();

        System.out.println("Borrowed successfully. Loan period: " + item.getLoanPeriod() + " days.");
        return true;
    }

    public boolean returnItem(int itemId, int daysOverdue) {
        if (daysOverdue < 0) {
            System.out.println("Invalid overdue days.");
            return false;
        }

        LibraryItem item = findItem(itemId);

        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }

        if (item.getStatus() != ItemStatus.ON_LOAN) {
            System.out.println("Item is not on loan.");
            return false;
        }

        Member member = findMemberByName(item.getBorrowerName());

        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        double baseFine = item.calculateFine(daysOverdue);
        double waiver = baseFine * member.getMembershipType().getWaiverRate();
        double finalFine = baseFine - waiver;
        double adminCharge = 0;

        if (daysOverdue > 0) {
            adminCharge = LibraryItem.getAdministrativeCharge();
        }

        double total = finalFine + adminCharge;

        member.chargeFine(total);
        member.recordReturn();

        System.out.println("Base fine: " + baseFine);
        System.out.println("Waiver: " + waiver);
        System.out.println("Administrative charge: " + adminCharge);
        System.out.println("Total: " + total);
        System.out.println("New balance: " + member.getBalance());

        item.takeBack();
        return true;
    }

    private Member findMemberByName(String name) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getName().equals(name)) {
                return members[i];
            }
        }

        return null;
    }

    public void renewItem(int itemId) {
        LibraryItem item = findItem(itemId);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (item instanceof Renewable) {
            Renewable renewable = (Renewable) item;

            if (renewable.renew()) {
                System.out.println("Renewed successfully.");
                System.out.println("Renewals remaining: " +
                    (renewable.getRenewalLimit() - item.getRenewalCount()));
            } else {
                System.out.println("Renewal failed.");
            }
        } else {
            System.out.println("This item cannot be renewed.");
        }
    }

    public void viewMembers() {
        for (int i = 0; i < memberCount; i++) {
            Member member = members[i];

            System.out.println(
                member.getName() + " | " +
                member.getMembershipId() + " | " +
                member.getMembershipType() + " | " +
                member.getItemsHeld() + " items | " +
                member.getBalance() + " EGP"
            );
        }
    }

    public boolean payFine(String memberId, double amount) {
        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        if (member.payFine(amount)) {
            System.out.println("Payment successful.");
            System.out.println("New balance: " + member.getBalance());
            return true;
        }

        System.out.println("Payment failed.");
        return false;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getItemsOnLoan() {
        int count = 0;

        for (int i = 0; i < itemCount; i++) {
            if (catalogue[i].getStatus() == ItemStatus.ON_LOAN) {
                count++;
            }
        }

        return count;
    }

    public double getLoanRate() {
        if (itemCount == 0) {
            return 0;
        }

        return (getItemsOnLoan() * 100.0) / itemCount;
    }

    public double getTotalOutstanding() {
        double total = 0;

        for (int i = 0; i < memberCount; i++) {
            total += members[i].getBalance();
        }

        return total;
    }

    public double getProjectedFines(int daysOverdue) {
        double total = 0;

        for (int i = 0; i < itemCount; i++) {
            if (catalogue[i].getStatus() == ItemStatus.ON_LOAN) {
                total += catalogue[i].calculateFine(daysOverdue);
            }
        }

        return total;
    }

    public void report() {
        System.out.println("Catalogue size: " + itemCount);
        System.out.println("Items ever catalogued: " + LibraryItem.getItemsEverCatalogued());
        System.out.println("Items on loan: " + getItemsOnLoan());
        System.out.println("Loan rate: " + getLoanRate() + "%");
        System.out.println("Total outstanding: " + getTotalOutstanding() + " EGP");
        System.out.println("Projected fines for 5 days: " + getProjectedFines(5) + " EGP");
    }
}
