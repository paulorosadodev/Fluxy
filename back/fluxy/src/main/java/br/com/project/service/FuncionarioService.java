package br.com.project.service;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.model.Employer;
import br.com.project.model.Person;
import br.com.project.model.Phone;
import br.com.project.repository.EmployerRepository;
import br.com.project.repository.PersonRepository;
import br.com.project.repository.PhoneRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final EmployerRepository employerRepository;
    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;
    private final MapperUtils mapperUtils;

    public FuncionarioService(EmployerRepository employerRepository, PersonRepository personRepository, PhoneRepository phoneRepository, MapperUtils mapperUtils) {
        this.employerRepository = employerRepository;
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void salvar(EmployeeRequestDTO funcionarioRequestDTO) {
        System.out.println(funcionarioRequestDTO);

        // 1. Primeiro salva a PESSOA manualmente
        Person person = new Person();
        person.setStreet(funcionarioRequestDTO.street());
        person.setNumber(funcionarioRequestDTO.number());
        person.setNeighborhood(funcionarioRequestDTO.neighborhood());
        person.setCity(funcionarioRequestDTO.city());
        person.setCep(funcionarioRequestDTO.cep());

        System.out.println(person);

        Integer idPessoa = personRepository.saveAndReturnId(person);

        // 2. Agora salva o FUNCIONÁRIO manualmente
        Employer employer = new Employer();
        employer.setIdPerson(idPessoa); // seta o id_pessoa como FK
        employer.setEmployeeNumber(gerarMatriculaAleatoria());
        employer.setCpf(funcionarioRequestDTO.cpf());
        employer.setName(funcionarioRequestDTO.name());
        employer.setSalary(funcionarioRequestDTO.salary() != null ? funcionarioRequestDTO.salary().doubleValue() : null);
        employer.setSectorOfActivity(funcionarioRequestDTO.sectorOfActivity());
        employer.setWorkShift(funcionarioRequestDTO.workShift());
        employer.setRole(funcionarioRequestDTO.role());
        employer.setIdSupervisor(funcionarioRequestDTO.fkSupervisor());

        System.out.println(employer);

        employerRepository.save(employer);

        // 3. Agora salva os TELEFONES manualmente
        if (funcionarioRequestDTO.phone() != null && !funcionarioRequestDTO.phone().isEmpty()) {
            List<Phone> phones = funcionarioRequestDTO.phone().stream()
                    .map(number -> new Phone(idPessoa, number))
                    .toList();
            phones.forEach(phoneRepository::save);
        }
    }

    public EmployeeResponseDTO buscarPorId(Integer id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return mapperUtils.map(employer, EmployeeResponseDTO.class);
    }

    public List<EmployeeResponseDTO> listarTodos() {
        List<Employer> employers = employerRepository.findAll();
        return mapperUtils.mapList(employers, EmployeeResponseDTO.class);
    }

    @Transactional
    public void atualizar(Integer id, EmployeeRequestDTO funcionarioRequestDTO) {
        // Primeiro busca o funcionário atual no banco
        Employer funcionarioExistente = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para atualizar"));

        if (funcionarioRequestDTO.cpf() != null) {
            funcionarioExistente.setCpf(funcionarioRequestDTO.cpf());
        }
        if (funcionarioRequestDTO.name() != null) {
            funcionarioExistente.setName(funcionarioRequestDTO.name());
        }
        if (funcionarioRequestDTO.salary() != null) {
            funcionarioExistente.setSalary(funcionarioRequestDTO.salary().doubleValue());
        }
        if (funcionarioRequestDTO.sectorOfActivity() != null) {
            funcionarioExistente.setSectorOfActivity(funcionarioRequestDTO.sectorOfActivity());
        }
        if (funcionarioRequestDTO.workShift() != null) {
            funcionarioExistente.setWorkShift(funcionarioRequestDTO.workShift());
        }
        if (funcionarioRequestDTO.role() != null) {
            funcionarioExistente.setRole(funcionarioRequestDTO.role());
        }
        if (funcionarioRequestDTO.fkSupervisor() != null) {
            funcionarioExistente.setIdSupervisor(funcionarioRequestDTO.fkSupervisor());
        }

        // Atualiza no banco
        employerRepository.update(funcionarioExistente);

        // Atualiza telefones: primeiro deleta todos antigos, depois salva novos
        phoneRepository.deleteByIdPerson(funcionarioExistente.getIdPerson());

        if (funcionarioRequestDTO.phone() != null && !funcionarioRequestDTO.phone().isEmpty()) {
            List<Phone> phones = funcionarioRequestDTO.phone().stream()
                    .map(number -> new Phone(funcionarioExistente.getIdPerson(), number))
                    .toList();
            phones.forEach(phoneRepository::save);
        }
    }

    @Transactional
    public void deletar(Integer id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado para deletar"));

        phoneRepository.deleteByIdPerson(employer.getIdPerson());
        employerRepository.deleteById(id);
    }

    private String gerarMatriculaAleatoria() {
        int numeroAleatorio = (int) (Math.random() * 1_000_000);
        return String.format("EMP%06d", numeroAleatorio);
    }
}
