package com.ujuezeoke.learning.springbootshiro.controllers;

import com.ujuezeoke.learning.springbootshiro.models.Book;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/book")
public class BookResourceController {
    private Logger LOGGER = LoggerFactory.getLogger(BookResourceController.class);

    @GetMapping
    @RequiresPermissions("books:read")
    public Collection<Book> getBooks() {
        LOGGER.info("Accessing Books");
        return Collections.emptyList();
    }

    @PutMapping
    @RequiresPermissions("books:write")
    public Collection<Book> addBook(@RequestBody Book book){
        LOGGER.info("Adding book {}", book);
        return Collections.emptyList();
    }
}
