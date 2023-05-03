import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Observer {
    void update(News news);
}

interface Subject {

    void notifyObservers(News news);
}

class News {
    private final String title;
    private String content;
    private final String topic;

    public News(String title, String content, String topic) {
        this.title = title;
        this.content = content;
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }
}

class NewsPublisher implements Subject {
    private final Map<String, List<Observer>> observers;
    private final List<News> newsList;

    public NewsPublisher() {
        observers = new HashMap<>();
        newsList = new ArrayList<>();
    }

    public void addNews(String title, String content, String topic) {
        News news = new News(title, content, topic);
        newsList.add(news);
        notifyObservers(news);
    }

    public void updateNews(String title, String content, String topic) {
        for (News news : newsList) {
            if (news.getTitle().equals(title) && news.getTopic().equals(topic)) {
                news.setContent(content);
                notifyObservers(news);
                break;
            }
        }
    }

    public void registerObserver(Observer observer, String topic) {
        if (!observers.containsKey(topic)) {
            observers.put(topic, new ArrayList<>());
        }
        observers.get(topic).add(observer);
    }

    public void notifyObservers(News news) {
        if (observers.containsKey(news.getTopic())) {
            for (Observer observer : observers.get(news.getTopic())) {
                observer.update(news);
            }
        }
    }
}

class NewsReader implements Observer {
    private final String name;
    private final List<String> topics;
    private final List<News> readNewsList;

    public NewsReader(String name) {
        this.name = name;
        topics = new ArrayList<>();
        readNewsList = new ArrayList<>();
    }

    public void registerTopic(String topic) {
        topics.add(topic);
    }

    public void update(News news) {
        if (topics.contains(news.getTopic())) {
            System.out.println("Читателят " + name + " получи следните новини: " + news.getTitle());
            readNewsList.add(news);
        }
    }

    public void markNewsAsRead(String title, String topic) {
        for (News news : readNewsList) {
            if (news.getTitle().equals(title) && news.getTopic().equals(topic)) {
                System.out.println("Читателят " + name + " получи следните новини: " + news.getTitle());
                readNewsList.remove(news);
                break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NewsPublisher publisher = new NewsPublisher();

        NewsReader reader1 = new NewsReader("Иван");
        reader1.registerTopic("Политика");
        reader1.registerTopic("Спорт");
        publisher.registerObserver(reader1, "Време");
        publisher.registerObserver(reader1, "Спорт");

        NewsReader reader2 = new NewsReader("Мария");
        reader2.registerTopic("Шоубизнес");
        reader2.registerTopic("Спорт");
        publisher.registerObserver(reader2, "Телевизия");
        publisher.registerObserver(reader2, "Спорт");

        publisher.addNews("Резултатите от изборите са обявени.", "Една от 6-те партии е спечелила.", "Политика");
        publisher.addNews("Финал на световното", "Англия спечели Световната купа по футбол.", "Спорт");

        reader1.markNewsAsRead("Резултатите от изборите са обявени", "Политика");

        publisher.updateNews("Финал на световното", "Англия спечели Световната купа по футбол.", "Спорт");

        reader1.markNewsAsRead("Финал на световното", "Спорт");
        reader2.markNewsAsRead("Финал на световното", "Спорт");
    }
}

