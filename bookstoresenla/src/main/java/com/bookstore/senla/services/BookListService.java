package com.bookstore.senla.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.senla.dao.BookRepository;
import com.bookstore.senla.model.BookList;

@Service
public class BookListService {
	private final BookRepository bookRepository;

	@Autowired
	public BookListService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void postXmlBooks(MultipartFile file) throws JAXBException {
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Path pathToNewFile = Files.write(path, bytes);
			File newFile = new File(pathToNewFile.toString());

			JAXBContext jaxbContext = JAXBContext.newInstance(com.bookstore.senla.model.Books.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			com.bookstore.senla.model.Books books = (com.bookstore.senla.model.Books) unmarshaller.unmarshal(newFile);
			for (BookList book : books.getBookList()) {
				bookRepository.save(book);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void convertBookListToXml() throws JAXBException, PropertyException, FileNotFoundException {
		List<BookList> bookList = bookRepository.findAll();
		com.bookstore.senla.model.Books books = new com.bookstore.senla.model.Books();
		books.setBookList(bookList);
		JAXBContext jaxbContext = JAXBContext.newInstance(com.bookstore.senla.model.Books.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshaller.marshal(books, new FileOutputStream("booksExport.xml"));
		
		System.out.println("Books.xml is created successfully");
	}
	
	public byte[] convertBookListToXml2(String fileName) throws JAXBException, PropertyException, IOException, URISyntaxException {
		List<BookList> bookList = bookRepository.findAll();
		com.bookstore.senla.model.Books books = new com.bookstore.senla.model.Books();
		books.setBookList(bookList);
		JAXBContext jaxbContext = JAXBContext.newInstance(com.bookstore.senla.model.Books.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(books, new FileOutputStream("src/main/resources/"+fileName));
		return Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
	}
	
	public ResponseEntity<Object> downloadXml() throws FileNotFoundException {
		String filename = "booksExport.xml";
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;

		
	}


}
