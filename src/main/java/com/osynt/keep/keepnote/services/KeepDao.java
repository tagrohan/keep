package com.osynt.keep.keepnote.services;

import java.util.List;

import com.osynt.keep.keepnote.entities.Note;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface KeepDao extends CrudRepository<Note,Integer> {


    @Query("select n from Note n order by id desc")
    public List<Note> getNotess();
    
}
