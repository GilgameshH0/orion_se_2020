package shilkin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Collection<URL> urlsCollection = getUrlsCollection();
        System.out.println("Последовательно получаю содержимое ссылок и записываю в файл..");
        toWriteSequential(urlsCollection, "sequential.txt");
        System.out.println("Параллельно получаю содержимое ссылок и записываю в файл..");
        toWriteParallel(urlsCollection, "parallel.txt");
    }

    public static void toWriteParallel(Collection<URL> urlsCollection, String fileName) throws InterruptedException, IOException, ExecutionException {
        File file = new File(fileName);
        ExecutorService service = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        Collection<Future<String>> sites = new CopyOnWriteArrayList<>();
        for (URL urlObject : urlsCollection) {
            String urlStr = urlObject.toString();
            Future<String> site = service.submit(() -> {
                StringBuilder site1 = new StringBuilder();
                site1.append("===== НАЧАЛО САЙТА <").append(urlStr).append("> ======\n");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlObject.openStream()))) {
                    while (reader.ready()) {
                        site1.append(reader.readLine()).append("\n");
                    }
                    site1.append("===== КОНЕЦ САЙТА <").append(urlStr).append("> ======\n");
                }
                return site1.toString();
            });
            sites.add(site);
        }
        service.shutdown();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (Future<String> site : sites) {
                writer.write(site.get() + "\n");
            }
        }
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed + ", размер файла: " + file.length() + " байт.");
    }

    public static void toWriteSequential(Collection<URL> urlsCollection, String fileName) throws IOException {
        File file = new File(fileName);
        long start = System.currentTimeMillis();
        for (URL urlObject : urlsCollection) {
            String urlStr = urlObject.toString();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlObject.openStream()))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    writer.write("===== НАЧАЛО САЙТА <" + urlStr + "> ======\n");
                    while (reader.ready()) {
                        writer.write(reader.readLine() + "\n");
                    }
                    writer.write("===== КОНЕЦ САЙТА <" + urlStr + "> ======\n");
                }
            }
        }
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed + ", размер файла: " + file.length() + " байт.");
    }

    public static Collection<URL> getUrlsCollection() throws MalformedURLException {
        Collection<URL> urlsCollection = new CopyOnWriteArrayList<>();
        urlsCollection.add(new URL("https://vk.com/"));
        urlsCollection.add(new URL("https://instagram.com/"));
        urlsCollection.add(new URL("https://ok.ru/"));
        urlsCollection.add(new URL("https://www.facebook.com/"));
        urlsCollection.add(new URL("https://auto.ru/"));
        urlsCollection.add(new URL("https://hh.ru/"));
        urlsCollection.add(new URL("https://meduza.io/"));
        urlsCollection.add(new URL("https://www.vesti.ru/"));
        urlsCollection.add(new URL("https://lenta.ru/"));
        urlsCollection.add(new URL("https://ria.ru/"));
        urlsCollection.add(new URL("https://www.rbc.ru/"));
        urlsCollection.add(new URL("https://amedia.online/"));
        urlsCollection.add(new URL("https://nn.mk.ru/"));
        urlsCollection.add(new URL("http://www.baidu.com/"));
        urlsCollection.add(new URL("https://www.drom.ru/"));
        urlsCollection.add(new URL("https://www.livejournal.com/"));
        urlsCollection.add(new URL("https://jut.su/"));
        urlsCollection.add(new URL("https://www.ozon.ru/"));
        urlsCollection.add(new URL("https://www.whatsapp.com/"));
        urlsCollection.add(new URL("https://kartaslov.ru/"));
        return urlsCollection;
    }
}
