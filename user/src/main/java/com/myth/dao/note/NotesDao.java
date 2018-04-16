package com.myth.dao.note;

import com.myth.base.BaseRepository;
import com.myth.domain.note.Notes;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesDao extends BaseRepository<Notes,Integer> {


}

