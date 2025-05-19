package br.com.project.service;

import br.com.project.dto.request.PhysicalClientRequestDTO;
import br.com.project.dto.response.PhysicalClientResponseDTO;
import br.com.project.model.Person;
import br.com.project.model.PhysicalClient;
import br.com.project.repository.PhoneRepository;
import br.com.project.repository.PhysicalClientRepository;
import br.com.project.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhysicalClientService {

    private final PhysicalClientRepository repository;
    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;

    public PhysicalClientService(PhysicalClientRepository repository, PersonRepository personRepository, PhoneRepository phoneRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public Integer save(PhysicalClientRequestDTO dto) {
        try {
            if (repository.existsByCpf(dto.cpf())) {
                throw new IllegalArgumentException("CPF já cadastrado.");
            }

            if (phoneRepository.existsByPhone(dto.phone())) {
                throw new IllegalArgumentException("Telefone já cadastrado.");
            }

            Person person = new Person();
            person.setStreet(dto.street());
            person.setNumber(dto.number());
            person.setNeighborhood(dto.neighborhood());
            person.setCity(dto.city());
            person.setCep(dto.cep());

            Integer idPessoa = personRepository.saveAndReturnId(person);

            PhysicalClient client = new PhysicalClient();
            client.setId(idPessoa);
            client.setName(dto.name());
            client.setCpf(dto.cpf());
            client.setStreet(dto.street());
            client.setNumber(dto.number());
            client.setNeighborhood(dto.neighborhood());
            client.setCity(dto.city());
            client.setCep(dto.cep());
            client.setPhone(dto.phone() != null ? dto.phone() : List.of());

            repository.save(client);
            return idPessoa;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public PhysicalClientResponseDTO findById(Integer id) {
        try {
            PhysicalClient client = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente físico com ID " + id + " não encontrado."));
            return toResponseDTO(client);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PhysicalClientResponseDTO> findAll() {
        try {
            return repository.findAll().stream()
                    .map(this::toResponseDTO)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(Integer id, PhysicalClientRequestDTO dto) {
        try {
            PhysicalClient existing = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente físico com ID " + id + " não encontrado."));

            Person person = new Person();
            person.setStreet(dto.street());
            person.setNumber(dto.number());
            person.setNeighborhood(dto.neighborhood());
            person.setCity(dto.city());
            person.setCep(dto.cep());

            personRepository.update(person);

            existing.setName(dto.name());
            existing.setCpf(dto.cpf());
            existing.setStreet(dto.street());
            existing.setNumber(dto.number());
            existing.setNeighborhood(dto.neighborhood());
            existing.setCity(dto.city());
            existing.setCep(dto.cep());
            existing.setPhone(dto.phone() != null ? dto.phone() : List.of());

            repository.update(id, existing);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            repository.deleteById(id);
            personRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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
                client.getCep(),
                client.getPhone() != null ? client.getPhone().toArray(new String[0]) : new String[0]
        );
    }
}
