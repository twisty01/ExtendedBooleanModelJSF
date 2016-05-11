package com.model.logic;

import java.util.Arrays;
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
    public static Set<String> OPs = new HashSet<>(Arrays.asList(AND, OR, OP_PAR, CL_PAR)); // not NOT on purpose

    private LinkedList<String> atoms;

    private TermLoader termLoader;
    private List<DocResult> results;

    public boolean parse(String expression) {
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

        recAddPars(atoms.listIterator(), false);

        return true;
    }

    private ListIterator<String> recAddPars(ListIterator<String> l, boolean addedPar) {
        int i = l.nextIndex();
        String lastAtom = "";
        while (l.hasNext()) {
            String curAtom = l.next();
            if (!OPs.contains(curAtom) || curAtom.equals(NOT)) {
                continue;
            }
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
        }
        return l;
    }

    private ListIterator<String> recConvertToANDs(ListIterator<String> l) {
        return l;
    }

    public void removeDoubleNegations() {
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
            }
            last = cur;
        }
    }

    public void evaluate() {
        // todo evalute
        // every doc evaluate recursively
        results = termLoader.createResults();

    }

    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public List<DocResult> getResults() {
        return results;
    }

}
