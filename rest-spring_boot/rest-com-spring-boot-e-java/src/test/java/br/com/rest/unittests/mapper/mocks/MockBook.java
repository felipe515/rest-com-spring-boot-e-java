package br.com.rest.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.rest.data.book.vo.BookVO;
import br.com.rest.model.book.Book;





public class MockBook {


    public Book  mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
        	books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
        	books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
    	Book book = new Book();
    	book.setAuthor("Author Test" + number);
    	book.setPrice(25D);
    	book.setLaunchDate(new Date());
    	book.setId(number.longValue());
    	book.setTitle("Title Test" + number);
        return book;
    }

    public BookVO mockVO(Integer number) {
    	BookVO book = new BookVO();
    	book.setAuthor("Author Test" + number);
    	book.setPrice(25);
    	book.setLaunchDate(new Date());
    	book.setId(number.longValue());
    	book.setTitle("Title Test" + number);
        return book;
    }

}
