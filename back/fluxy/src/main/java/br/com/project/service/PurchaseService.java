package br.com.project.service;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.repository.PurchaseRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final MapperUtils mapperUtils;

    public PurchaseService(PurchaseRepository purchaseRepository, MapperUtils mapperUtils) {
        this.purchaseRepository = purchaseRepository;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public PurchaseResponseDTO save(PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = new Purchase();
            purchase.setDate(requestDTO.date());
            purchase.setTime(requestDTO.time());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.type());
            purchase.setProductQuantity(requestDTO.productAmount());
            purchase.setProductId(requestDTO.fkProductId());
            purchase.setClientId(requestDTO.fkClientId());
            purchase.setOperationalEmployeeId(requestDTO.fkOperationalIdEmployee());

            Integer purchaseId = purchaseRepository.save(purchase);
            purchase.setNumber(purchaseId);

            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar compra: " + e.getMessage(), e);
        }
    }

    public PurchaseResponseDTO findByNumber(Integer number) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada com número" + number));
            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar compra: " + e.getMessage());
        }
    }

    public List<PurchaseResponseDTO> findAll() {
        try {
            List<Purchase> purchases = purchaseRepository.findAll();
            return mapperUtils.mapList(purchases, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar compras: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void update(Integer number, PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

            purchase.setClientId(requestDTO.fkClientId());
            purchase.setDate(requestDTO.date());
            purchase.setTime(requestDTO.time());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.type());
            purchase.setProductQuantity(requestDTO.productAmount());
            purchase.setProductId(requestDTO.fkProductId());
            purchase.setClientId(requestDTO.fkClientId());
            purchase.setOperationalEmployeeId(requestDTO.fkOperationalIdEmployee());

            purchase.setNumber(number);
            purchaseRepository.update(purchase);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar compra: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteByNumber(Integer number) {
        try {
            purchaseRepository.deleteByNumber(number);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar compra: " + e.getMessage(), e);
        }
    }
}
