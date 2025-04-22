package br.com.project.service;

import br.com.project.dto.request.LegalClientRequestDTO;
import br.com.project.dto.response.LegalClientResponseDTO;
import br.com.project.model.LegalClient;
import br.com.project.model.Person;
import br.com.project.repository.LegalClientRepository;
import br.com.project.repository.PersonRepository;
import br.com.project.repository.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LegalClientService {

    private final LegalClientRepository repository;
    private final PersonRepository personRepository;

    public LegalClientService(LegalClientRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Transactional
    public Integer save(LegalClientRequestDTO dto) {

        Person person = new Person();
        person.setStreet(dto.street());
        person.setNumber(dto.number());
        person.setNeighborhood(dto.neighborhood());
        person.setCity(dto.city());
        person.setCep(dto.cep());

        Integer idPessoa = personRepository.saveAndReturnId(person);

        LegalClient client = new LegalClient();
        client.setId(idPessoa);
        client.setLegalName(dto.legalName());
        client.setCnpj(dto.cnpj());
        client.setStateRegistration(dto.stateRegistration());
        client.setStreet(dto.street());
        client.setNumber(dto.number());
        client.setNeighborhood(dto.neighborhood());
        client.setCity(dto.city());
        client.setCep(dto.cep());
        client.setPhone(dto.phone() != null ? dto.phone() : List.of());

        repository.save(client);
        return idPessoa;
    }


    public LegalClientResponseDTO findById(Integer id) {
        LegalClient client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente jurídico não encontrado"));
        return toResponseDTO(client);
    }

    public List<LegalClientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void update(Integer id, LegalClientRequestDTO dto) {
        LegalClient existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente jurídico não encontrado"));

        Person person = new Person();
        person.setStreet(dto.street());
        person.setNumber(dto.number());
        person.setNeighborhood(dto.neighborhood());
        person.setCity(dto.city());
        person.setCep(dto.cep());

        personRepository.update(person);

        existing.setLegalName(dto.legalName());
        existing.setCnpj(dto.cnpj());
        existing.setStateRegistration(dto.stateRegistration());
        existing.setStreet(dto.street());
        existing.setNumber(dto.number());
        existing.setNeighborhood(dto.neighborhood());
        existing.setCity(dto.city());
        existing.setCep(dto.cep());
        existing.setPhone(dto.phone() != null ? dto.phone(): List.of());

        repository.update(id, existing);
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
        personRepository.deleteById(id);
    }

    private LegalClientResponseDTO toResponseDTO(LegalClient client) {
        return new LegalClientResponseDTO(
                client.getId(),
                client.getLegalName(),
                client.getCnpj(),
                client.getStateRegistration(),
                client.getStreet(),
                client.getNumber(),
                client.getNeighborhood(),
                client.getCity(),
                client.getCep(),
                client.getPhone() != null ? client.getPhone().toArray(new String[0]) : new String[0]
        );
    }
}
