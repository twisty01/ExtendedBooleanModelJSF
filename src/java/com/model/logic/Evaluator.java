package com.model.logic;

import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Pattern;

public class Evaluator {

    public static String notQ = Pattern.quote("!");
    public static String andQ = Pattern.quote("&&");
    public static String orQ = Pattern.quote("||");
    public static String openParQ = Pattern.quote("(");
    public static String closeParQ = Pattern.quote(")");
    public static String NOT = "!";
    public static String AND = "&&";
    public static String OR = "||";
    public static String OP_PAR = "(";
    public static String CL_PAR = ")";
    public static Set<String> OPs = new HashSet<>(Arrays.asList(AND, OR, OP_PAR, CL_PAR, NOT)); // not NOT on purpose

    public LinkedList<String> atoms;
    public List<DocResult> results;

    private TermLoader termLoader;

    private boolean parse(String expression) {
        atoms = new LinkedList<>(Arrays.asList(expression
                .toLowerCase()
                .replaceAll(andQ, " && ")
                .replaceAll(orQ, " || ")
                .replaceAll(openParQ, " ( ")
                .replaceAll(closeParQ, " ) ")
                .replaceAll(notQ, " ! ")
                .replaceAll("  +", " ")
                .trim()
                .split(" ")));
        if (!checkPars()) {
            return false;
        }
        addANDs();
        //if (!checkOPs(atoms.listIterator())) {
        //    return false;
        //}
        recAddPars(atoms.listIterator(), false);
        //recConvertToANDs(atoms.listIterator());
        removeDoubleNegations();
        return true;
    }

    private ListIterator<String> recAddPars(ListIterator<String> l, boolean addedPar) {
        int i = l.nextIndex();
        String lastAtom = "";
        while (l.hasNext()) {
            String curAtom = l.next();
            if (OPs.contains(curAtom) && !curAtom.equals(NOT)) {
                if (lastAtom.equals(OR) && curAtom.equals(AND)) {
                    while (l.previous().equals(OR)) {
                    }
                    l.previous();
                    l.add(OP_PAR);
                    l.next();
                    l = recAddPars(l, true);
                } else if (lastAtom.equals(AND) && curAtom.equals(OR)) {
                    l.previous();
                    l.add(CL_PAR);
                    if (!addedPar) {
                        int tmp = l.nextIndex();
                        atoms.add(i, OP_PAR);
                        l = atoms.listIterator(tmp + 1);
                    }
                } else if (curAtom.equals(OP_PAR)) {
                    l = recAddPars(l, false);
                } else if (curAtom.equals(CL_PAR)) {
                    if (addedPar) {
                        l.add(CL_PAR);
                    }
                    return l;
                }
                lastAtom = curAtom;
            } else if (!l.hasNext()) {
                if (addedPar) {
                    l.add(CL_PAR);
                }
            }
        }
        return l;
    }

    private void removeDoubleNegations() {
        String lastAtom = "";
        ListIterator<String> l = atoms.listIterator();
        while (l.hasNext()) {
            String curAtom = l.next();
            if (lastAtom.equals(NOT) && curAtom.equals(NOT)) {
                l.previous();
                l.remove();
            }
            lastAtom = curAtom;
        }
    }

    private boolean checkPars() {
        int valid = 0;
        for (String curAtom : atoms) {
            if (curAtom.equals(OP_PAR)) {
                valid++;
            } else if (curAtom.equals(CL_PAR)) {
                valid--;
            }
            if (valid < 0) {
                return false;
            }
        }
        return true;
    }

    private ListIterator<String> recConvertToANDs(ListIterator<String> l) {
        return l;
    }

    private boolean checkOPs(ListIterator<String> l) {
        return true;
    }

    private void addANDs() {
        String last = AND;
        ListIterator<String> l = atoms.listIterator();
        while (l.hasNext()) {
            String cur = l.next();
            if (!OPs.contains(last) && (!OPs.contains(cur) || cur.equals(NOT))) {
                l.previous();
                l.add(AND);
                l.next();
            }
            last = cur;
        }
    }

    private double recEvaluate(int idx, ListIterator<String> l) {
        boolean conjunction = true;
        boolean nextIsNegation = false;
        double value = 0;
        int t = 0;
        while (l.hasNext()) {
            String curAtom = l.next();
            t++;
            if (curAtom.equals(OR)) {
                conjunction = false;
                break;
            } else if (curAtom.equals(AND)) {
                conjunction = true;
                break;
            }
        }
        for (int i = 0; i < t; i++) {
            l.previous();
        }
        t = 0;
        while (l.hasNext()) {
            String curAtom = l.next();
            if (!OPs.contains(curAtom)) {
                double tmp = getWeight(idx, curAtom);
                t++;
                if ((conjunction && nextIsNegation) || (!conjunction && !nextIsNegation)) {
                    value += tmp * tmp;
                } else {
                    value += (1 - tmp) * (1 - tmp);
                }
            } else {
                if (curAtom.equals(NOT)) {
                    nextIsNegation = !nextIsNegation;
                } else if (curAtom.equals(OP_PAR)) {
                    double tmp = recEvaluate(idx, l);
                    t++;
                    if ((conjunction && nextIsNegation) || (!conjunction && !nextIsNegation)) {
                        value += tmp * tmp;
                    } else {
                        value += (1 - tmp) * (1 - tmp);
                    }
                } else if (curAtom.equals(CL_PAR)) {
                    break;
                } else if (curAtom.equals(AND) || curAtom.equals(OR)) {
                    nextIsNegation = false;
                }
            }
        }
        value /= t;
        value = sqrt(value);
        if (conjunction) {
            value = 1 - value;
        }
        return value;
    }

    private double getWeight(int idx, String term) {
        List<DocTermValue> list = termLoader.getDocValueByTerm(term);
        /*
         int i = Collections.binarySearch(list, new DocTermValue(idx, 0));
         if (i < 0) {
         return 0;
         }
         DocTermValue tmp = list.get(i);
         return tmp.getWeight();
         */
        for (DocTermValue v : list) {
            if (v.getIdx() == idx) {
                return v.getWeight();
            }
        }
        return 0;
    }

    /// --------------------------------------
    public boolean evaluate(String expression) {
        if (!parse(expression)) {
            return false;
        }
        // todo evalute
        // every doc evaluate recursively
        results = termLoader.createResults();
        for (int i = 0; i < results.size(); i++) {
            DocResult r = results.get(i);
            r.setRelevance(recEvaluate(i, atoms.listIterator()));
        }
        results.sort((DocResult o1, DocResult o2) -> {
            if (o1.getRelevance() - o2.getRelevance() < 0) {
                return 1;
            } else if (o1.getRelevance() - o2.getRelevance() > 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return true;
    }

    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public List<DocResult> getResults() {
        return results;
    }

}
