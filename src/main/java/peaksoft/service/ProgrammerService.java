package peaksoft.service;

import peaksoft.entity.Programmer;

import java.util.List;
import java.util.Optional;

public interface ProgrammerService {
    String addConstrainUnique();
    String saveProgrammer(Programmer programmer, Long addressID);
    String saveProgrammer(List<Programmer> programmers, Long addressID);

    List<Programmer> getAll();

    Optional<Programmer> getByID(Long id);

    String deleteByID(Long id);

    String deleteAll();

    Programmer update(Long id,Programmer newProgrammer);

    List<Programmer> getProgrammerByCountry(String country);

    String getYoungProgrammer();

    String getSeniorProgrammer();

}
