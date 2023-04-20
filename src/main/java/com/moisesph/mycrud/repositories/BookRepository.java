package com.moisesph.mycrud.repositories;

import com.moisesph.mycrud.controllers.BookController;
import com.moisesph.mycrud.model.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BookMapper mapper = new BookMapper();

    private final SimpleJdbcInsert insert;
    public BookRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insert = new SimpleJdbcInsert(dataSource).withTableName("books").usingGeneratedKeyColumns("id");
    }

    public List<Book> getAllBooks(){
        String sql = "select * from books";
        return namedParameterJdbcTemplate.query(sql, mapper);
    }

    public long createBook(Book newBook) {
        return (long) insert.executeAndReturnKey(
                new MapSqlParameterSource("name", newBook.name)
        );
    }

    public boolean updateBook(Book newBook){
        String sql = "update books set name = :name where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", newBook.name);
        parameterSource.addValue("id", newBook.id);
        int result = namedParameterJdbcTemplate.update(sql, parameterSource);
        return result > 0;
    }

    public boolean deleteBook(long id){
        String sql = "delete from books where id = :id";

        Map<String, ?> params = Map.of("id", id);
        int result = namedParameterJdbcTemplate.update(sql,params);
        return result > 0;
    }


    private static class BookMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Book(id, name);
        }
    }
}
