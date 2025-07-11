package com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.service;

import java.util.List;

import com.springlab.checkpoint3.PharmaInventoryAndDispatchManager.model.Medicine;

public class DispatchService {
	private final InventoryService inventory;

	public DispatchService(InventoryService inventory) {
		this.inventory = inventory;
		System.out.println("DispatchService has been initialized");
	}

	public void prepareShipment() {
		List<Medicine> medicineStock = inventory.getAvalilableMedicineStock();
		System.out.println("Preparing the shipment of: ");
		medicineStock.forEach(System.out::println);
	}

	public InventoryService getInventory() {
		return inventory;
	}
}
