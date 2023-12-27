package br.com.rest.repositories.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rest.model.book.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
