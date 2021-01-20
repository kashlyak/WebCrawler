import bean.WebCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        WebCrawler webCrawler = new WebCrawler();
        List<String> list = new ArrayList<>();
        boolean forWhile = true;
        while (forWhile) {


            System.out.println("Введите слово для поиска:");
            String word = bufferedReader.readLine();
            if (word.equals("")) {
                forWhile = false;

            } else {
                list.add(word);
            }
        }
        System.out.println("Откуда начианть?");
        String link = bufferedReader.readLine();
//        https://en.wikipedia.org/wiki/Elon_Musk

        webCrawler.search(link, list, 0);

    }
}