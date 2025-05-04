package br.com.project.service;

import br.com.project.dto.request.PersonRequestDTO;
import br.com.project.dto.response.PersonResponseDTO;
import br.com.project.model.Person;
import br.com.project.model.Phone;
import br.com.project.repository.PersonRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PessoaService {

    private final PersonRepository pessoaRepository;
    private final TelefoneService telefoneService;
    private final MapperUtils mapperUtils;

    public PessoaService(PersonRepository pessoaRepository, TelefoneService telefoneService, MapperUtils mapperUtils) {
        this.pessoaRepository = pessoaRepository;
        this.telefoneService = telefoneService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void save(PersonRequestDTO personRequestDTO) {
        try {
            Person person = mapperUtils.map(personRequestDTO, Person.class);
            pessoaRepository.saveAndReturnId(person);

            List<Phone> phones = personRequestDTO.getPhone().stream()
                    .map(number -> new Phone(person.getIdPerson(), number))
                    .toList();

            phones.forEach(telefoneService::salvar);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pessoa: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Integer id, PersonRequestDTO pessoaRequestDTO) {
        try {
            Person person = mapperUtils.map(pessoaRequestDTO, Person.class);
            person.setIdPerson(id);
            pessoaRepository.update(person);

            telefoneService.deletarPorIdPessoa(id);

            List<Phone> phones = pessoaRequestDTO.getPhone().stream()
                    .map(number -> new Phone(id, number))
                    .toList();

            phones.forEach(telefoneService::salvar);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    public PersonResponseDTO findById(Integer id) {
        try {
            Person person = pessoaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            return mapperUtils.map(person, PersonResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pessoa: " + e.getMessage());
        }
    }

    public List<PersonResponseDTO> listAll() {
        try {
            List<Person> persons = pessoaRepository.findAll();
            return mapperUtils.mapList(persons, PersonResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pessoas: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        try {
            telefoneService.deletarPorIdPessoa(id);
            pessoaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pessoa: " + e.getMessage());
        }
    }
}
