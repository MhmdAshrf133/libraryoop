public enum MembershipType {
    STUDENT(0.25),
    STAFF(0.10),
    PUBLIC(0.0);

    private double waiverRate;

    MembershipType(double waiverRate) {
        this.waiverRate = waiverRate;
    }

    public double getWaiverRate() {
        return waiverRate;
    }
}
