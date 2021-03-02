package com.bookstore.senla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import com.bookstore.senla.dao.BookRepository;
import com.bookstore.senla.model.BookList;
import com.bookstore.senla.services.BookListService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

@SpringBootApplication
@RestController
@CrossOrigin("*")
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
    public List<BookList> findBook(@PathVariable String title) {
        return repository.findByTitle(title);
    }
    
    @GetMapping("/getById/{isbn}")
	public Optional<BookList> getBookById(@PathVariable Long isbn) {
        return repository.findById(isbn);
	}
	
    @GetMapping("/cancel/{isbn}")
    public List<BookList> cancelRegistration(@PathVariable long isbn) {
        repository.deleteById(isbn);
        return repository.findAll();
    }
    
    @PutMapping("/edit/{isbn}")
    public List<BookList> editBook(@PathVariable long isbn, @RequestBody BookList booklistDetails) {
    	BookList editedBook = repository.getOne(isbn);
    	editedBook.setAuthor(booklistDetails.getAuthor());
    	editedBook.setTitle(booklistDetails.getTitle());
    	repository.save(editedBook);
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
    
    @GetMapping(value ="/download")
	public ResponseEntity<InputStreamResource> downloadFile() throws IOException, PropertyException, JAXBException
	{
        bookListService.convertBookListToXml();
        return bookListService.downloadXml();
        
	};
	
    @GetMapping(value ="/downloadXml")
	public ResponseEntity<InputStreamResource> downloadFile2() throws IOException, PropertyException, JAXBException
	{
        bookListService.convertBookListToXml();
        return bookListService.downloadXml();
        
	};
	
    @GetMapping(value ="/downloadYoutube")
	public void downloadFile2(String fileName,HttpServletResponse response) throws Exception
	{
       response.setHeader("Content-Disposition","attachment; filename"+ fileName);
       response.getOutputStream().write(bookListService.writeContentOf(fileName));  
	}
    
    
    
    public static void main(String[] args) {
        SpringApplication.run(BookstoresenlaApplication.class, args);
    }

}