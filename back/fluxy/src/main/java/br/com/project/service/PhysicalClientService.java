package br.com.project.service;

import br.com.project.dto.request.PhysicalClientRequestDTO;
import br.com.project.dto.response.PhysicalClientResponseDTO;
import br.com.project.model.Person;
import br.com.project.model.PhysicalClient;
import br.com.project.repository.PhysicalClientRepository;
import br.com.project.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhysicalClientService {

    private final PhysicalClientRepository repository;
    private final PersonRepository personRepository;

    public PhysicalClientService(PhysicalClientRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Transactional
    public Integer save(PhysicalClientRequestDTO dto) {
        Person person = new Person();
        person.setStreet(dto.street());
        person.setNumber(dto.number());
        person.setNeighborhood(dto.neighborhood());
        person.setCity(dto.city());
        person.setCep(dto.zipCode());

        Integer idPessoa = personRepository.saveAndReturnId(person);

        PhysicalClient client = new PhysicalClient();
        client.setId(idPessoa);
        client.setName(dto.name());
        client.setCpf(dto.cpf());
        client.setStreet(dto.street());
        client.setNumber(dto.number());
        client.setNeighborhood(dto.neighborhood());
        client.setCity(dto.city());
        client.setZipCode(dto.zipCode());
        client.setPhones(dto.phones() != null ? (dto.phones()) : List.of());

        repository.save(client);
        return idPessoa;
    }

    public PhysicalClientResponseDTO findById(Integer id) {
        PhysicalClient client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente físico não encontrado"));
        return toResponseDTO(client);
    }

    public List<PhysicalClientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void update(Integer id, PhysicalClientRequestDTO dto) {
        PhysicalClient existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente físico não encontrado"));

        Person person = new Person();
        person.setStreet(dto.street());
        person.setNumber(dto.number());
        person.setNeighborhood(dto.neighborhood());
        person.setCity(dto.city());
        person.setCep(dto.zipCode());

        personRepository.update(person);

        existing.setName(dto.name());
        existing.setCpf(dto.cpf());
        existing.setStreet(dto.street());
        existing.setNumber(dto.number());
        existing.setNeighborhood(dto.neighborhood());
        existing.setCity(dto.city());
        existing.setZipCode(dto.zipCode());
        existing.setPhones(dto.phones() != null ? dto.phones() : List.of());

        repository.update(id, existing);
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
        personRepository.deleteById(id);
    }

    private PhysicalClientResponseDTO toResponseDTO(PhysicalClient client) {
        return new PhysicalClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getStreet(),
                client.getNumber(),
                client.getNeighborhood(),
                client.getCity(),
                client.getZipCode(),
                client.getPhones() != null ? client.getPhones().toArray(new String[0]) : new String[0]
        );
    }
}
