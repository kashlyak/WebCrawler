package bean;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import service.WebCrawlerService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private static final int MAX_PAGES_TO_SEARCH = 100;
    private static final int MAX_DEPTH = 10;
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private static final String CSV_FILE = "D:\\test.csv";

    BufferedWriter writer;

    {
        try {
            writer = Files.newBufferedWriter(Paths.get(CSV_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CSVPrinter csvPrinter;

    {
        try {
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Link", "Count"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(String url, List<String> searchWord, int depth) throws IOException {
        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            if (depth++ < MAX_DEPTH) {
                String currentUrl;
                WebCrawlerService leg = new WebCrawlerService();
                if (this.pagesToVisit.isEmpty()) {
                    currentUrl = url;
                    this.pagesVisited.add(url);
                } else {
                    currentUrl = this.nextUrl();
                }
                leg.crawl(currentUrl);
                csvPrinter.printRecord(currentUrl);
                for (String word : searchWord) {

                    System.out.println(leg.searchForWord(word));


                    csvPrinter.printRecord(leg.searchForWord(word));

                    csvPrinter.flush();

                }


                this.pagesToVisit.addAll(leg.getLinks());
            }
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }


    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}
