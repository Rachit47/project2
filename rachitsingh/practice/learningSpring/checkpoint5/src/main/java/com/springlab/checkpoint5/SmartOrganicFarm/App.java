package com.springlab.checkpoint5.SmartOrganicFarm;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.checkpoint5.SmartOrganicFarm.components.PlotTracker;
import com.springlab.checkpoint5.SmartOrganicFarm.config.SmartFarmConfig;
import com.springlab.checkpoint5.SmartOrganicFarm.service.PlannerService;

public class App {
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartFarmConfig.class)) {
			PlannerService planner1 = context.getBean(PlannerService.class);
			planner1.plan("corn");

			PlannerService planner2 = context.getBean(PlannerService.class);
			planner2.plan("wheat");

			PlannerService planner3 = context.getBean(PlannerService.class);
			planner3.plan("paddy");

			PlannerService planner4 = context.getBean(PlannerService.class);
			planner4.plan("soy");

			PlotTracker tracker = context.getBean(PlotTracker.class);
			System.out.println("\nAll Available Plots in our farm: ");
			tracker.getRecords().forEach(System.out::println);

			System.out.println("\nProof that each planner service has a different PlotManager assigned to it: ");
			System.out.println("Planner1's Plot Manager: " + System.identityHashCode(planner1));
			System.out.println("Planner2's Plot Manager: " + System.identityHashCode(planner2));
			System.out.println("Planner2's Plot Manager: " + System.identityHashCode(planner3));
			System.out.println("Planner2's Plot Manager: " + System.identityHashCode(planner4));
			System.out.println("\nSame Plot Manager ? " + ((planner1.getPlotManager() == planner2.getPlotManager())
					&& (planner2.getPlotManager() == planner3.getPlotManager())
					&& (planner3.getPlotManager() == planner4.getPlotManager())));
		} catch (BeansException e) {
			e.printStackTrace();
		}

	}
}
