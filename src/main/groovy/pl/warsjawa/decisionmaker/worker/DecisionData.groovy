package pl.warsjawa.decisionmaker.worker

class DecisionData {
    final Person person
    final String job
    final int amount
    final String fraudStatus
    final String decision

    DecisionData(Person person, String job, int amount, String fraudStatus, String decision) {
        this.person = person
        this.job = job
        this.amount = amount
        this.fraudStatus = fraudStatus
        this.decision = decision
    }
}
