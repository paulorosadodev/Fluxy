package br.com.project.service;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.model.Employer;
import br.com.project.model.Person;
import br.com.project.model.Phone;
import br.com.project.repository.EmployerRepository;
import br.com.project.repository.PersonRepository;
import br.com.project.repository.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final EmployerRepository employerRepository;
    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;

    public FuncionarioService(EmployerRepository employerRepository,
                              PersonRepository personRepository,
                              PhoneRepository phoneRepository) {
        this.employerRepository = employerRepository;
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public void salvar(EmployeeRequestDTO dto) {
        // 1) Salva a pessoa e captura o ID
        Person person = new Person();
        person.setStreet(dto.street());
        person.setNumber(dto.number());
        person.setNeighborhood(dto.neighborhood());
        person.setCity(dto.city());
        person.setCep(dto.cep());
        Integer idPessoa = personRepository.saveAndReturnId(person);

        // 2) Salva o funcionário
        Employer employer = new Employer();
        employer.setIdPerson(idPessoa);
        employer.setEmployeeNumber(gerarMatriculaAleatoria());
        employer.setCpf(dto.cpf());
        employer.setName(dto.name());
        employer.setSalary(dto.salary() != null ? dto.salary().doubleValue() : null);
        employer.setSectorOfActivity(dto.sectorOfActivity());
        employer.setWorkShift(dto.workShift());
        employer.setRole(dto.role());
        employer.setIdSupervisor(dto.fkSupervisor());
        employerRepository.save(employer);

        // 3) Salva os telefones vinculados
        if (dto.phone() != null && !dto.phone().isEmpty()) {
            for (String num : dto.phone()) {
                if (num != null && !num.isBlank()) {
                    phoneRepository.save(new Phone(idPessoa, num));
                }
            }
        }

    }

    public EmployeeResponseDTO buscarPorId(Integer id) {
        Employer e = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return toResponseDTO(e);
    }

    public List<EmployeeResponseDTO> listarTodos() {
        return employerRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void atualizar(Integer id, EmployeeRequestDTO dto) {
        Employer existing = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para atualizar"));

        if (dto.cpf() != null) existing.setCpf(dto.cpf());
        if (dto.name() != null) existing.setName(dto.name());
        if (dto.salary() != null) existing.setSalary(dto.salary().doubleValue());
        if (dto.sectorOfActivity() != null) existing.setSectorOfActivity(dto.sectorOfActivity());
        if (dto.workShift() != null) existing.setWorkShift(dto.workShift());
        if (dto.role() != null) existing.setRole(dto.role());
        if (dto.fkSupervisor() != null) existing.setIdSupervisor(dto.fkSupervisor());

        employerRepository.update(existing);

        // Atualiza telefones
        phoneRepository.deleteByIdPerson(existing.getIdPerson());
        if (dto.phone() != null && !dto.phone().isEmpty()) {
            for (String num : dto.phone()) {
                phoneRepository.save(new Phone(existing.getIdPerson(), num));
            }
        }
    }

    @Transactional
    public void deletar(Integer id) {
        Employer existing = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para deletar"));
        phoneRepository.deleteByIdPerson(existing.getIdPerson());
        employerRepository.deleteById(id);
    }

    private EmployeeResponseDTO toResponseDTO(Employer e) {
        Person p = e.getPerson();
        String[] phones = e.getPhones() != null
                ? e.getPhones().toArray(new String[0])
                : new String[0];

        return new EmployeeResponseDTO(
                e.getIdEmployee(),
                e.getEmployeeNumber(),
                e.getCpf(),
                e.getName(),
                e.getSalary() != null ? e.getSalary().intValue() : null,
                e.getSectorOfActivity(),
                e.getWorkShift(),
                e.getRole(),
                e.getIdSupervisor(),
                p != null ? p.getStreet() : null,
                p != null ? p.getNumber() : null,
                p != null ? p.getNeighborhood() : null,
                p != null ? p.getCity() : null,
                p != null ? p.getCep() : null,
                phones
        );
    }

    private String gerarMatriculaAleatoria() {
        int num = (int) (Math.random() * 1_000_000);
        return String.format("EMP%06d", num);
    }
}