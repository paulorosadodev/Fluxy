package br.com.project.service;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.model.Employer;
import br.com.project.model.Person;
import br.com.project.model.Phone;
import br.com.project.model.User;
import br.com.project.repository.EmployerRepository;
import br.com.project.repository.PersonRepository;
import br.com.project.repository.PhoneRepository;
import br.com.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final EmployerRepository employerRepository;
    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(EmployerRepository employerRepository,
                              PersonRepository personRepository,
                              PhoneRepository phoneRepository,
                              UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.employerRepository = employerRepository;
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void salvar(EmployeeRequestDTO dto) {
        try {
            if (employerRepository.existsByCpf(dto.cpf())) {
                throw new IllegalArgumentException("CPF já cadastrado");
            }

            Person person = new Person();
            person.setStreet(dto.street());
            person.setNumber(dto.number());
            person.setNeighborhood(dto.neighborhood());
            person.setCity(dto.city());
            person.setCep(dto.cep());
            Integer idPessoa = personRepository.saveAndReturnId(person);

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
            String systemRole = mapearRoleSistema(dto.role(), dto.sectorOfActivity());
            if (!systemRole.equals("none")) {
                String senhaSomenteDigitos = dto.cpf().replaceAll("\\D", "");
                User user = new User();
                user.setName(employer.getEmployeeNumber());
                user.setRole(systemRole);
                user.setPassword(passwordEncoder.encode(senhaSomenteDigitos));
                userRepository.save(user);
            }

            if (dto.phone() != null && !dto.phone().isEmpty()) {
                for (String num : dto.phone()) {
                    if (num != null && !num.isBlank()) {
                        phoneRepository.save(new Phone(idPessoa, num));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }
    }

    private String mapearRoleSistema(String role, String sector) {
        role = role.toLowerCase();
        sector = sector != null ? sector.toLowerCase() : "";

        if (role.equals("vigilante") || sector.equals("limpeza")){
            return "none";
        }

        if (role.contains("gerente")) {
            return "admin";
        }
        if (role.equals("caixa")) {
            return "purchases-customers-products";
        }
        if (role.equals("repositor")) {
            return "products-productSupplies-suppliers";
        }
        if ((role.equals("açougueiro") && sector.equals("açougue")) ||
                (role.equals("padeiro") && sector.equals("padaria"))) {
            return "products-productSupplies";
        }
        if (role.equals("auxiliar")) {
            if (sector.equals("estoque")) {
                return "products-productSupplies-suppliers";
            }
            if (sector.equals("administração")) {
                return "employees-customers-products-productSupplies-suppliers-purchases";
            }
            return "customers-products";
        }
        if (role.equals("estagiário")) {
            if (sector.equals("estoque")) {
                return "products-productSupplies-suppliers";
            }
            if (sector.equals("administração")) {
                return "employees-customers-products-productSupplies-suppliers-purchases";
            }
            return "customers-products";
        }
        return "none";
    }

    public EmployeeResponseDTO buscarPorId(Integer id) {
        try {
            Employer e = employerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário com ID " + id + " não encontrado"));
            return toResponseDTO(e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionário por ID: " + e.getMessage(), e);
        }
    }

    public List<EmployeeResponseDTO> listarTodos() {
        try {
            return employerRepository.findAll().stream()
                    .map(this::toResponseDTO)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void atualizar(Integer id, EmployeeRequestDTO dto) {
        try {
            Employer existing = employerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário com ID " + id + " não encontrado"));

            if (dto.cpf() != null && !dto.cpf().equals(existing.getCpf())) {
                if (employerRepository.existsByCpf(dto.cpf())) {
                    throw new IllegalArgumentException("CPF já cadastrado");
                }
                existing.setCpf(dto.cpf());
            }

            if (dto.cpf() != null) existing.setCpf(dto.cpf());
            if (dto.name() != null) existing.setName(dto.name());
            if (dto.salary() != null) existing.setSalary(dto.salary().doubleValue());
            if (dto.sectorOfActivity() != null) existing.setSectorOfActivity(dto.sectorOfActivity());
            if (dto.workShift() != null) existing.setWorkShift(dto.workShift());
            if (dto.role() != null) existing.setRole(dto.role());
            if (dto.fkSupervisor() != null) existing.setIdSupervisor(dto.fkSupervisor());

            Person person = existing.getPerson();
            if (dto.street() != null) person.setStreet(dto.street());
            if (dto.number() != null) person.setNumber(dto.number());
            if (dto.neighborhood() != null) person.setNeighborhood(dto.neighborhood());
            if (dto.city() != null) person.setCity(dto.city());
            if (dto.cep() != null) person.setCep(dto.cep());

            employerRepository.update(existing);

            phoneRepository.deleteByIdPerson(existing.getIdPerson());
            if (dto.phone() != null && !dto.phone().isEmpty()) {
                for (String num : dto.phone()) {
                    phoneRepository.save(new Phone(existing.getIdPerson(), num));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deletar(Integer id) {
        try {
            Employer existing = employerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário com ID " + id + " não encontrado"));
            phoneRepository.deleteByIdPerson(existing.getIdPerson());
            employerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage(), e);
        }
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

    public Integer buscarIdPorMatricula(String matricula) {
        return employerRepository.findEmployeeIdByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Funcionário com matrícula " + matricula + " não encontrado"));
    }
}
