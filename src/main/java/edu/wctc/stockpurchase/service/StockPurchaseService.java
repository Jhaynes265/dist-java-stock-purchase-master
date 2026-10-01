package edu.wctc.stockpurchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.exception.InvalidRequestException;
import edu.wctc.stockpurchase.exception.ResourceNotFoundException;
import edu.wctc.stockpurchase.repo.StockPurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockPurchaseService {
    private final StockPurchaseRepository stockPurchaseRepository;
    private final ObjectMapper objectMapper;

    public StockPurchaseService(StockPurchaseRepository stockPurchaseRepository) {
        this.stockPurchaseRepository = stockPurchaseRepository;
        this.objectMapper = new ObjectMapper();
    }

    public List<StockPurchase> findAll() {
        List<StockPurchase> list = new ArrayList<>();
        stockPurchaseRepository.findAll().forEach(list::add);
        return list;
    }

    public StockPurchase findById(int id) {
        return stockPurchaseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("StockPurchase", id)
        );
    }

    public StockPurchase createStockPurchase(StockPurchase purchase) {
        purchase.setId(0);
        return stockPurchaseRepository.save(purchase);
    }

    public StockPurchase updateStockPurchase(StockPurchase purchase) {
        return stockPurchaseRepository.save(purchase);
    }

    public StockPurchase patchStockPurchase(int id, JsonPatch patch) {
        try {
            StockPurchase existingPurchase = findById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(existingPurchase, JsonNode.class));
            StockPurchase patchedPurchase = objectMapper.treeToValue(patched, StockPurchase.class);
            stockPurchaseRepository.save(patchedPurchase);
            return patchedPurchase;
        } catch (JsonPatchException | JsonProcessingException exception) {
            throw new InvalidRequestException("Unable to update Stock Purchase");
        }
    }

    public void deleteStockPurchase(int id) {
        stockPurchaseRepository.deleteById(id);
    }
}