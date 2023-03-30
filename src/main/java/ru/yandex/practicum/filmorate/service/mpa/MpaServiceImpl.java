package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService{
    private final MpaStorage mpaStorage;

    public List<Mpa> getAllMpas() {
        return mpaStorage.getAll();
    }

    public Mpa getMpaById(Long id) {
        return mpaStorage.get(id);
    }
}
