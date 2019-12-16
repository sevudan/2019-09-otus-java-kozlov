package ru.otus.api.dao;

import ru.otus.api.model.Accaunt;
import ru.otus.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccauntDao {

  Optional<Accaunt> findById(long id);

  long saveAccaunt(Accaunt accauntName);

  SessionManager getSessionManager();
}
