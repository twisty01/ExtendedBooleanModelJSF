package com.model.view;

import com.model.logic.Evaluator;
import com.model.logic.TermLoader;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "booleanModel", eager = true)
@SessionScoped
public class BooleanModel implements Serializable {

    @ManagedProperty("#{termLoader}")
    private TermLoader termLoader;

    private String expression = "";
    private long duration;
    private boolean simpleSearch = false;
    private String searchButton = "EBM Search", switchButton = "Switch to Simple Search";
    private String resultsVisibility = "none";
    private List<DocResult> results;
    private int resPerPage = 50;
    private int currentPage = 1;
    private List<DocResult> subRes;

    /* include magic here */
    public void evaulate() {
        duration = System.nanoTime();
        Evaluator evaluator = new Evaluator();
        evaluator.setTermLoader(termLoader);
        if (!evaluator.parse(expression)) {
            return;
        }
        if (!simpleSearch) {
            evaluator.evaluate();
        } else {
            evaluator.evaluateSimple();
        }
        results = evaluator.getResults();
        duration = System.nanoTime() - duration;
        duration /= 1000000;
        // show results
        reset();
        resultsVisibility = "block";
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public long getDuration() {
        return duration;
    }

    public String getSearchButton() {
        return searchButton;
    }

    public String getSwitchButton() {
        return switchButton;
    }

    public void switchSearch() {
        simpleSearch = !simpleSearch;
        if (simpleSearch) {
            searchButton = "Simple Search";
            switchButton = "Switch to EBM Search";
        } else {
            switchButton = "Switch to Simple Search";
            searchButton = "EBM Search";
        }
    }

    public String getResultsVisibility() {
        return resultsVisibility;
    }

    public int getResPerPage() {
        return resPerPage;
    }

    public void setResPerPage(int resPerPage) {
        this.resPerPage = resPerPage;
        reset();
    }

    public List<DocResult> getSubRes() {
        return subRes;
    }

    public void nextPage() {
        if (currentPage * resPerPage >= results.size()) {
            return;
        }
        currentPage++;
        int lb = (currentPage - 1) * resPerPage;
        int ub = lb + resPerPage;
        subRes = results.subList(
                lb > results.size() ? results.size() : lb,
                ub > results.size() ? results.size() : ub
        );
    }

    public void prevPage() {
        if (currentPage == 1) {
            return;
        }
        currentPage--;
        int lb = (currentPage - 1) * resPerPage;
        int ub = lb + resPerPage;
        subRes = results.subList(
                lb > results.size() ? results.size() : lb,
                ub > results.size() ? results.size() : ub
        );
    }

    public void reset() {
        currentPage = 1;
        int lb = (currentPage - 1) * resPerPage;
        int ub = lb + resPerPage;
        subRes = results.subList(
                lb > results.size() ? results.size() : lb,
                ub > results.size() ? results.size() : ub
        );
    }

    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

}
