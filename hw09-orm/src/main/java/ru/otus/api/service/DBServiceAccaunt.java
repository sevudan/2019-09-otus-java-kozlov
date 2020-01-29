package ru.otus.api.service;

import ru.otus.api.model.Accaunt;

import java.util.Optional;

public interface DBServiceAccaunt {

  long saveAccaunt(Accaunt accaunt);

  Optional<Accaunt> getAccaunt(long id);

}
