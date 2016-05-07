package com.model.logic;

import com.porter.Stemmer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean(name = "termLoader", eager = true)
@ApplicationScoped
public class TermLoader implements Serializable {

    public final String DATA_ROOT_PATH = "/travel_guides_txt";
    public final String STOP_WORDS_PATH = "/stop_words.txt";

    private HashSet<String> stopWords;
    private List<String> documents;
    private HashMap<String, List<Document>> terms;
    private List<String> listTerms;

    public TermLoader() {
        initialize();
    }

    private void initialize() {
        loadStopWords();
        loadDocuments();
        // 1. iterate over all documents, load document to map of terms and number of occurencies;
        terms = new HashMap<>();
        for (String path : documents) {
            File file = new File(path);
            Scanner scn = null;
            try {
                scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
            } catch (FileNotFoundException ex) {
            }
            HashMap<String, Integer> tmp = new HashMap<>();
            while (scn.hasNext()) {
                String word = scn.next().toLowerCase();
                if (isTerm(word)) {
                    String term = Stemmer.stem(word);
                    tmp.put(term, tmp.getOrDefault(term, 0) + 1);
                }
            }
            scn.close();
            // 2. from occurencies to frequencies, add to map of terms and map of documents with frequencies
            tmp.forEach((String term, Integer count) -> {
                double freq = ((double) count) / tmp.size();
                List<Document> documentList = terms.getOrDefault(term, new LinkedList<>());
                documentList.add(new Document(path, freq));
                terms.put(term, documentList);
            });
        }
        for (List<Document> documentList : terms.values()) {
            double maxFreq = 0f;
            for (Document d : documentList) {
                if (d.getWeight() > maxFreq) {
                    maxFreq = d.getWeight();
                }
            }
            for (Document d : documentList) {
                double freq = d.getWeight(); // temp. frekvence ulozena do vahy
                double tf = freq / maxFreq;
                double idf = Math.log(((double) documents.size()) / documentList.size());   // vadi prirozeny misto dvojkoveho?
                double weight = tf * idf;
                d.setWeight(weight);
            }
        }
        initListTerms();
    }

    private void loadDocuments() {
        String realPath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(DATA_ROOT_PATH);
        documents = new LinkedList<>();
        try {
            Files.walkFileTree(Paths.get(realPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    File f = file.toFile();
                    if (f.isFile()) {
                        documents.add(f.getPath());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
        }
    }

    private void loadStopWords() {
        String realPath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(STOP_WORDS_PATH);
        File file = new File(realPath);
        Scanner scn = null;
        try {
            scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
        } catch (FileNotFoundException ex) {
        }
        stopWords = new HashSet<>();
        while (scn.hasNextLine()) {
            stopWords.add(scn.nextLine());
        }
    }

    private void initListTerms() {
        ArrayList<String> tmp = new ArrayList<>(terms.size());
        for (String key : terms.keySet()) {
            tmp.add(key);
        }
        Collections.sort(tmp);
        listTerms = tmp;
    }

    // -------------------------------------------------------------------
    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public boolean isTerm(String word) {
        return !isStopWord(word) && word.length() > 1;
    }

    public HashMap<String, List<Document>> getTerms() {
        return terms;
    }

    public List<String> getListTerms() {
        return listTerms;
    }

    public List<Document> getDocumentsByTerm(String term) {
        return terms.get(term);
    }
    
    public List<Document> createResultsList(){
        LinkedList<Document> results = new LinkedList<>();
        for ( String s : documents){
            results.add(new Document(s,0));
        }
        return results;
    }
    

}