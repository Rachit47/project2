package com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.model.Medicine;

public class InventoryService {
	private List<Medicine> medicineStock = new ArrayList<>();

	public InventoryService(List<Medicine> medicines) {
		medicineStock.addAll(medicines);
		System.out.println("Pharma Inventory has been initialized.");
	}

	public List<Medicine> getAvalilableMedicineStock() {
		if (!medicineStock.isEmpty()) {
			return Collections.unmodifiableList(medicineStock);
		}
		return null;
	}
}
