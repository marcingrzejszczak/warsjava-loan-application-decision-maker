package pl.warsjawa.decisionmaker.worker

interface PropagationWorker {
    void checkAndPropagate(String loanApplicationId, String loanApplicationDetails)
}