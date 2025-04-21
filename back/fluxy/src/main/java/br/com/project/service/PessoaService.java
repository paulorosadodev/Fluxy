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
        Person person = mapperUtils.map(personRequestDTO, Person.class);
        pessoaRepository.saveAndReturnId(person);

        // Salva todos os telefones vinculados
        List<Phone> phones = personRequestDTO.getPhone().stream()
                .map(number -> new Phone(person.getIdPerson(), number))
                .toList();

        phones.forEach(telefoneService::salvar);
    }

    @Transactional
    public void update(Integer id, PersonRequestDTO pessoaRequestDTO) {
        Person person = mapperUtils.map(pessoaRequestDTO, Person.class);
        person.setIdPerson(id);
        pessoaRepository.update(person);

        // Primeiro apaga os telefones antigos
        telefoneService.deletarPorIdPessoa(id);

        // Depois salva os novos telefones
        List<Phone> phones = pessoaRequestDTO.getPhone().stream()
                .map(number -> new Phone(id, number))
                .toList();

        phones.forEach(telefoneService::salvar);
    }

    public PersonResponseDTO findById(Integer id) {
        Person person = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return mapperUtils.map(person, PersonResponseDTO.class);
    }

    public List<PersonResponseDTO> listAll() {
        List<Person> persons = pessoaRepository.findAll();
        return mapperUtils.mapList(persons, PersonResponseDTO.class);
    }

    public void delete(Integer id) {
        telefoneService.deletarPorIdPessoa(id);
        pessoaRepository.deleteById(id);
    }
}
