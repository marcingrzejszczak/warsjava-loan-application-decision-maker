package pl.warsjawa.decisionmaker.worker

interface PropagationWorker {

    void makeDecisionAndPropagate(String loanApplicationId, String loanApplicationDetails)

}
