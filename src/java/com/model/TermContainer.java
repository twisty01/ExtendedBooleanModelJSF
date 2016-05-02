package com.model;

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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean(name = "termContainer", eager = true)
@ApplicationScoped
public class TermContainer implements Serializable {

    public final String DATA_ROOT_PATH = "/travel_guides_txt";
    public final String STOP_WORDS_PATH = "/stop_words.txt";

    private HashSet<String> stopWords;
    private List<String> documents;
    private HashMap<String, List<Document>> terms;

    public TermContainer() {
        initialize();
    }

    private void initialize() {
        loadStopWords();
        loadDocuments();
        // 1. iterate over all documents, load document to map of terms and number of occurencies;
        documents.stream().forEach((String path) -> {
            File file = new File(path);
            Scanner scn = null;
            try {
                scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            HashMap<String, Integer> tmp = new HashMap<>();
            while (scn.hasNext()) {
                String word = scn.next();
                if (!isStopWord(word)) {
                    String term = Stemmer.stem(word);
                    tmp.put(term, tmp.getOrDefault(term, 0) + 1);
                }
            }
            scn.close();
            // 2. from occurencies to frequencies, add to map of terms and map of documents with frequencies
            terms = new HashMap<>();
            tmp.forEach((String term, Integer u) -> {
                double freq = ((double) u) / tmp.size();
                List<Document> documentList = terms.getOrDefault(term, new LinkedList<>());
                documentList.add(new Document(path, freq));
                terms.put(term, documentList);
            });
        });
        terms.forEach((String t, List<Document> documentList) -> {
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
            documentList.sort((Document o1, Document o2) -> {
                return o1.compareTo(o2);
            });
        });
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
            ex.printStackTrace();
        }
    }

    private void loadStopWords() {
        String realPath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(STOP_WORDS_PATH);
        File file = new File(realPath);
        Scanner scn = null;
        try {
            scn = new Scanner(file).useDelimiter("[^a-zA-Z]+| <.*>");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        stopWords = new HashSet<>();
        while (scn.hasNextLine()) {
            stopWords.add(scn.nextLine());
        }
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public HashMap<String, List<Document>> getTerms() {
        return terms;
    }

    public List<Document> getDocumentsByTerm(String term) {
        return terms.get(term);
    }

    public List<Document> getDocumentsByTerm() {
        return terms.get("Brief");
    }

}
