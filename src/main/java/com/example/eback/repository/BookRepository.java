package com.example.eback.repository;

import com.example.eback.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findBookById(int id);
    void deleteBookByid(int id);
    List<Book> findBooksByNameContains(String str);

    List<Book> findBooksByNameContaining(String str);

    Book findBookByNameEquals(String name);

    Book findBookByIsbnEquals(String isbn);
    @Query("DELETE FROM Book WHERE isbn =:isbn")
    @Modifying
    void deleteBookByIsbnEquals(@Param("isbn") String isbn);
}