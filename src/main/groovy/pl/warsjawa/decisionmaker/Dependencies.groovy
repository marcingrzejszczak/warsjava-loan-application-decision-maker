package pl.warsjawa.decisionmaker


public enum Dependencies {
    MARKETING, REPORTING

    @Override
    String toString() {
        return super.toString().toLowerCase()
    }
}