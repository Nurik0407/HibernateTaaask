package peaksoft.service;

import peaksoft.entity.Programmer;
import peaksoft.repository.ProgrammerRepository;
import peaksoft.repository.ProgrammerRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ProgrammerServiceImpl implements ProgrammerService{
    ProgrammerRepository programmerRepository = new ProgrammerRepositoryImpl();
    @Override
    public String addConstrainUnique() {
        return programmerRepository.addConstrainUnique();
    }

    @Override
    public String saveProgrammer(Programmer programmer, Long addressID) {
        return programmerRepository.saveProgrammer(programmer, addressID);
    }

    @Override
    public String saveProgrammer(List<Programmer> programmers, Long addressID) {
        return programmerRepository.saveProgrammer(programmers, addressID);
    }

    @Override
    public List<Programmer> getAll() {
        return programmerRepository.getAll();
    }

    @Override
    public Optional<Programmer> getByID(Long id) {
        return programmerRepository.getByID(id);
    }

    @Override
    public String deleteByID(Long id) {
        return programmerRepository.deleteByID(id);
    }

    @Override
    public String deleteAll() {
        return programmerRepository.deleteAll();
    }

    @Override
    public Programmer update(Long id, Programmer newProgrammer) {
        return programmerRepository.update(id, newProgrammer);
    }

    @Override
    public List<Programmer> getProgrammerByCountry(String country) {
        return programmerRepository.getProgrammerByCountry(country);
    }

    @Override
    public String getYoungProgrammer() {
        return programmerRepository.getYoungProgrammer();
    }

    @Override
    public String getSeniorProgrammer() {
        return programmerRepository.getSeniorProgrammer();
    }
}
