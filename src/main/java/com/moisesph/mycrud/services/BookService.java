package com.moisesph.mycrud.services;

import com.moisesph.mycrud.controllers.BookController;
import com.moisesph.mycrud.model.Book;
import com.moisesph.mycrud.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks(){
        return repository.getAllBooks();
    }

    public long createBook(Book newBook){
        return repository.createBook(newBook);
    }
}
