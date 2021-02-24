package com.bookstore.senla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.senla.dao.BookRepository;
import com.bookstore.senla.model.BookList;
import com.bookstore.senla.services.BookListService;

import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBException;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class BookstoresenlaApplication {

    @Autowired
    private BookRepository repository;
    
    private final BookListService bookListService;
    
    public BookstoresenlaApplication(BookListService bookListService) {
        this.bookListService = bookListService;
    }

    @PostMapping("/register")
    public String register(@RequestBody BookList booklist) {
        repository.save(booklist);
        return "Book added successfully";
    }
    
    @GetMapping("/getAllBooks")
    public List<BookList> findAllBooks() {
        return repository.findAll();
    }

    @GetMapping("/findBook/{title}")
    public List<BookList> findBook(@PathVariable String name) {
        return repository.findByTitle(name);
    }

    @DeleteMapping("/cancel/{id}")
    public List<BookList> cancelRegistration(@PathVariable long id) {
        repository.deleteById(id);
        return repository.findAll();
    }
    
    @PostMapping("/postxml")
    public void postBooks(@RequestPart("file") MultipartFile file)
    {
    	try {
			bookListService.postXmlBooks(file);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @GetMapping(value ="/convertToXml")
    public String ConvertToXml() throws JAXBException, FileNotFoundException {
        bookListService.convertBookListToXml();
        return "Books converted to xml successfully";
    };

    public static void main(String[] args) {
        SpringApplication.run(BookstoresenlaApplication.class, args);
    }

}