package project.learning.java.nested_class.practice.ex1;

import lombok.AllArgsConstructor;


public class Library {
    private int currentCount;
    private Book[] books;

    public Library(int maxCount) {
        this.books = new Book[maxCount];
        this.currentCount = 0;
    }

    public void addBook(String title, String author) {
        if(currentCount >= books.length) {
            System.out.println("더 이상 책을 추가할 수 없습니다.");
            return;
        }
        Book newBook = new Book(title, author);
        books[currentCount++] = newBook;
    }

    public void showBooks() {
        System.out.println("=== 책 목록 출력 ===");
        for (Book book : books) {
            book.showBook();
        }
    }

    @AllArgsConstructor
    static class Book {
        private String title;
        private String author;

        public void showBook() {
            System.out.println("Title: " + title + ", Author: " + author);
        }
    }

}
