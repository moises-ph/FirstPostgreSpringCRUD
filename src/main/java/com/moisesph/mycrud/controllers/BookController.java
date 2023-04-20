package com.moisesph.mycrud.controllers;

import com.moisesph.mycrud.model.Book;
import com.moisesph.mycrud.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Long.parseLong;

@RestController
public class BookController {
    private class Result{
        public String Message;
        public boolean Error;
    }
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("/book")
    public long createBook(@RequestBody Book newBook){
        return bookService.createBook(newBook);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
    public Result updateBook(@PathVariable("id") String id,@RequestBody Book newBook){
        Book updateBook = new Book(parseLong(id), newBook.name);
        boolean result = bookService.updateBook(updateBook);
        if (result){
            Result result1 = new Result();
            result1.Error = false;
            result1.Message = "Actualizado correctamente";
            return result1;
        }
        else{
            Result result1 = new Result();
            result1.Error = true;
            result1.Message = "Hubo un error";
            return result1;
        }
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public Result deleteBook(@PathVariable("id") String id){
        boolean result = bookService.deleteBook(parseLong(id));
        if (result){
            Result result1 = new Result();
            result1.Error = false;
            result1.Message = "Eliminado correctamente";
            return result1;
        }
        else{
            Result result1 = new Result();
            result1.Error = true;
            result1.Message = "Hubo un error";
            return result1;
        }
    }
}
