package base;

import com.google.common.collect.ComparisonChain;
import org.junit.Test;

/**
 * Created by edgar on 15-6-26.
 */
public class CompareToTest {

    @Test
    public void testComapre() {

    }

    public class Book implements Comparable<Book> {
        private String title;
        private String author;
        private String isbn;
        private double price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public int compareTo(Book o) {
            return ComparisonChain.start()
                    .compare(this.title, o.title)
                    .compare(this.author, o.author)
                    .compare(this.isbn, o.isbn)
                    .compare(this.price, o.price)
                    .result();
        }
    }
}
