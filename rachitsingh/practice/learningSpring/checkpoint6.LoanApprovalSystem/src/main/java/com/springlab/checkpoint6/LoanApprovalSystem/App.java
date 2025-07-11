package com.springlab.checkpoint6.LoanApprovalSystem;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springlab.checkpoint6.LoanApprovalSystem.config.AppConfig;
import com.springlab.checkpoint6.LoanApprovalSystem.model.LoanApplication;
import com.springlab.checkpoint6.LoanApprovalSystem.model.LoanStatus;
import com.springlab.checkpoint6.LoanApprovalSystem.service.LoanServiceInterface;

public class App 
{
    public static void main( String[] args )
    {
       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
       
//       System.out.println(Arrays.toString(context.getBeanNamesForType(LoanServiceInterface.class, false, false)));
       LoanServiceInterface loanService = context.getBean("loanService",LoanServiceInterface.class);
       LoanApplication application = new LoanApplication("L101", "Raman Reddy", 21000.90, LoanStatus.PENDING);
       
       
       loanService.applyLoan(application);
       loanService.approveLoan("L101");
       System.out.println("Final Status: " + loanService.getApplication("L101").getStatus());
       
       context.close();
    }
}
