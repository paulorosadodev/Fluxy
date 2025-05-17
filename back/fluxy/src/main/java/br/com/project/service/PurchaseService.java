package br.com.project.service;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.repository.PurchaseRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final ProductService productService;

    private final MapperUtils mapperUtils;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           FuncionarioService funcionarioService,
                           ClienteService clienteService,
                           ProductService productService,
                           MapperUtils mapperUtils) {
        this.purchaseRepository = purchaseRepository;
        this.funcionarioService = funcionarioService;
        this.clienteService = clienteService;
        this.productService = productService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public PurchaseResponseDTO save(PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = new Purchase();
            purchase.setDate(LocalDate.now());
            purchase.setTime(LocalTime.now());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductAmount(requestDTO.productAmount());
            purchase.setProductId(requestDTO.productId());

            Integer clientId = clienteService.buscarIdPorMatricula(requestDTO.customerId());
            if (clientId == null) {
                    throw new RuntimeException("Cliente não encontrado com CPF " + requestDTO.customerId());
            }

            purchase.setClientId(clientId);
            if (requestDTO.customerId() == null || requestDTO.customerId().isBlank()) {
                throw new RuntimeException("A matrícula do cliente está ausente.");
            }
            purchase.setEmployeeId(funcionarioService.buscarIdPorMatricula(requestDTO.employeeId()));

            Integer purchaseId = purchaseRepository.save(purchase);
            purchase.setNumber(purchaseId);

            productService.decreaseStock(requestDTO.productId(), requestDTO.productAmount());

            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public PurchaseResponseDTO findByNumber(Integer number) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada com número" + number));
            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PurchaseResponseDTO> findAll() {
        try {
            List<Purchase> purchases = purchaseRepository.findAll();
            return mapperUtils.mapList(purchases, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(Integer number, PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

            purchase.setDate(LocalDate.now());
            purchase.setTime(LocalTime.now());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductAmount(requestDTO.productAmount());
            purchase.setProductId(requestDTO.productId());

            Integer customerId = clienteService.buscarIdPorMatricula(requestDTO.customerId());
            if (customerId == null) {
                throw new RuntimeException("Cliente não encontrado com matrícula " + requestDTO.customerId());
            }
            purchase.setClientId(customerId);

            Integer employeeId = funcionarioService.buscarIdPorMatricula(requestDTO.employeeId());
            if (employeeId == null) {
                throw new RuntimeException("Funcionário não encontrado com matrícula " + requestDTO.employeeId());
            }
            purchase.setEmployeeId(employeeId);

            purchase.setNumber(number);
            purchaseRepository.update(purchase);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void deleteByNumber(Integer number) {
        try {
            purchaseRepository.deleteByNumber(number);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
