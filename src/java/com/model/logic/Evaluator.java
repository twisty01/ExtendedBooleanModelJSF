package com.model.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Evaluator {

    // not? 
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

    private List<String> atoms;

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

        if (!isValid(atoms)) {
            return false;
        }

        addANDs(atoms);

        String lastAtom = "";
        int i = 0;
        while (i < atoms.size()) {
            String curAtom = atoms.get(i);
            if (OPs.contains(curAtom)) {
                if (lastAtom.equals(OR) && curAtom.equals(AND)) {
                    if (atoms.get(i - 2).equals(NOT)) {
                        atoms.add(i - 2, OP_PAR);
                    } else {
                        atoms.add(i - 1, OP_PAR);
                    }
                    i = recBracketing(atoms, i + 1);
                } else if (lastAtom.equals("") && curAtom.equals(AND)) {
                    atoms.add(0, OP_PAR);
                    i = recBracketing(atoms, i + 1);
                }

                lastAtom = curAtom;
            }
            i++;
        }

        return true;
    }

    private int recBracketing(List<String> list, int i) {
        String lastAtom = "";
        while (i < list.size()) {
            String curAtom = list.get(i);
            if (OPs.contains(curAtom)) {
                if (lastAtom.equals(OR) && curAtom.equals(AND)) {
                    if (atoms.get(i - 2).equals(NOT)) {
                        list.add(i - 2, OP_PAR);
                    } else {
                        list.add(i - 1, OP_PAR);
                    }
                    i = recBracketing(list, i + 1);
                } else if (lastAtom.equals(AND) && curAtom.equals(OR)) {
                    list.add(i, CL_PAR);
                    return i;
                }
                if (curAtom.equals(CL_PAR)) {
                    list.add(i, CL_PAR);
                    return i;
                }
                lastAtom = curAtom;
            }
            i++;
        }
        return i;
    }

    private boolean isValid(List<String> list) {
        int valid = 0;
        for (String s : list) {
            if (s.equals(OP_PAR)) {
                valid++;
            } else if (s.equals(CL_PAR)) {
                valid--;
            }
            if (valid < 0) {
                return false;
            }
        }

        return true;
    }

    private void addANDs(List<String> list) {
        String last = AND;
        int i = 0;
        while (i < list.size()) {
            String cur = list.get(i);
            if (!OPs.contains(last) && !OPs.contains(cur) && !last.equals(NOT)) {
                list.add(i, AND);
                i++;
            }
            last = cur;
            i++;
        }
    }

    public void evaluate() {
        // todo evalute
        // every doc evaluate recursively
        results = termLoader.createResults();
        
        // iterate over expression, create nested expression for each document, untill reaching the endNodes= terms
       
        
    }

    private int recEvaluate(List<String> atoms, int i) {
        boolean isAnd = false, decided = false;

        while (i < atoms.size()) {
            String curAtom = atoms.get(i);
            if ("(".equals(curAtom)) {
                i = recEvaluate(atoms, i + 1);
            } else if ("&&".equals(curAtom)) {
            } else if ("||".equals(curAtom)) {
            } else {
                if (!decided) {
                    String tmp = atoms.get(i + 1);
                    if (tmp.equals(AND)) {
                        isAnd = true;
                    } else if (tmp.equals(OR)) {
                        isAnd = false;
                    }
                    decided = true;
                }
                // todo?
            }
        }

        return 0;
    }

    public void setTermLoader(TermLoader termLoader) {
        this.termLoader = termLoader;
    }

    public List<DocResult> getResults() {
        return results;
    }

}
