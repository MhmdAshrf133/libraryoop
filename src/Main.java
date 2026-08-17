import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Library library = new Library(100, 100);

        library.addItem(new Book(1, "Java Basics", "Ahmed Ali", 250));
        library.addItem(new Magazine(2, "Tech Monthly", 15));
        library.addItem(new DVD(3, "Learning Java", 120));

        library.addMember(new Member("Mohamed", "M001", MembershipType.STUDENT));
        library.addMember(new Member("Ahmed", "M002", MembershipType.STAFF));
        library.addMember(new Member("Omar", "M003", MembershipType.PUBLIC));

        int choice = -1;

        while (choice != 0) {
            System.out.println();
            System.out.println("1. View catalogue");
            System.out.println("2. Register member");
            System.out.println("3. Borrow item");
            System.out.println("4. Return item");
            System.out.println("5. Renew loan");
            System.out.println("6. Search item");
            System.out.println("7. View items by status");
            System.out.println("8. Pay outstanding fines");
            System.out.println("9. View all members");
            System.out.println("10. Library report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                library.viewCatalogue();

            } else if (choice == 2) {
                System.out.print("Name: ");
                String name = input.nextLine();

                System.out.print("Membership ID: ");
                String id = input.nextLine();

                System.out.println("1. Student");
                System.out.println("2. Staff");
                System.out.println("3. Public");
                System.out.print("Choose category: ");
                int type = input.nextInt();
                input.nextLine();

                MembershipType membershipType = MembershipType.PUBLIC;

                if (type == 1) {
                    membershipType = MembershipType.STUDENT;
                } else if (type == 2) {
                    membershipType = MembershipType.STAFF;
                }

                if (library.addMember(new Member(name, id, membershipType))) {
                    System.out.println("Member registered.");
                } else {
                    System.out.println("Could not register member.");
                }

            } else if (choice == 3) {
                System.out.print("Item ID: ");
                int itemId = input.nextInt();
                input.nextLine();

                System.out.print("Membership ID: ");
                String memberId = input.nextLine();

                library.borrowItem(itemId, memberId);

            } else if (choice == 4) {
                System.out.print("Item ID: ");
                int itemId = input.nextInt();

                System.out.print("Days overdue: ");
                int days = input.nextInt();
                input.nextLine();

                library.returnItem(itemId, days);

            } else if (choice == 5) {
                System.out.print("Item ID: ");
                int itemId = input.nextInt();
                input.nextLine();

                library.renewItem(itemId);

            } else if (choice == 6) {
                System.out.print("Item ID: ");
                int itemId = input.nextInt();
                input.nextLine();

                LibraryItem item = library.findItem(itemId);

                if (item == null) {
                    System.out.println("Item not found.");
                } else {
                    item.display();
                }

            } else if (choice == 7) {
                System.out.println("1. Available");
                System.out.println("2. On loan");
                System.out.println("3. Reserved");
                System.out.println("4. Lost");
                System.out.print("Choose status: ");
                int status = input.nextInt();
                input.nextLine();

                if (status == 1) {
                    library.viewByStatus(ItemStatus.AVAILABLE);
                } else if (status == 2) {
                    library.viewByStatus(ItemStatus.ON_LOAN);
                } else if (status == 3) {
                    library.viewByStatus(ItemStatus.RESERVED);
                } else if (status == 4) {
                    library.viewByStatus(ItemStatus.LOST);
                } else {
                    System.out.println("Invalid status.");
                }

            } else if (choice == 8) {
                System.out.print("Membership ID: ");
                String memberId = input.nextLine();

                System.out.print("Amount: ");
                double amount = input.nextDouble();
                input.nextLine();

                library.payFine(memberId, amount);

            } else if (choice == 9) {
                library.viewMembers();

            } else if (choice == 10) {
                library.report();

            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}
