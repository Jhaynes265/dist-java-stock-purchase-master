package edu.wctc.stockpurchase.controller;

import com.github.fge.jsonpatch.JsonPatch;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.service.StockPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockpurchases")
public class StockPurchaseController {

    private final StockPurchaseService stockPurchaseService;

    @Autowired
    public StockPurchaseController(StockPurchaseService stockPurchaseService) {
        this.stockPurchaseService = stockPurchaseService;
    }

    @GetMapping
    public List<StockPurchase> getAllStockPurchases() {
        return stockPurchaseService.findAll();
    }

    @GetMapping("/{purchaseId}")
    public StockPurchase getStockPurchase(@PathVariable int purchaseId) {
        return stockPurchaseService.findById(purchaseId);
    }

    @PostMapping
    public StockPurchase createStockPurchase(@RequestBody StockPurchase purchase) {
        return stockPurchaseService.createStockPurchase(purchase);
    }

    @PutMapping
    public StockPurchase updateStockPurchase(@RequestBody StockPurchase purchase) {
        return stockPurchaseService.updateStockPurchase(purchase);
    }

    @PatchMapping("/{purchaseId}")
    public StockPurchase patchStockPurchase(@PathVariable int purchaseId, @RequestBody JsonPatch patch) {
        return stockPurchaseService.patchStockPurchase(purchaseId, patch);
    }

    @DeleteMapping("/{purchaseId}")
    public String deleteStockPurchase(@PathVariable int purchaseId) {
        stockPurchaseService.deleteStockPurchase(purchaseId);
        return "Successfully deleted purchase with ID " + purchaseId;
    }
}