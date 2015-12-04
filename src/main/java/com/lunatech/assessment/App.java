
package com.lunatech.assessment;

import com.google.inject.Inject;
import com.lunatech.assessment.service.QueryService;
import com.lunatech.assessment.service.ReportService;

import static com.lunatech.assessment.util.Lang.prompt;

public class App {

    @Inject private QueryService queryService;
    @Inject private ReportService reportService;

    public void begin() {
        String option = prompt("\nChoose:\n1) Query\n2) Reports\n");

        if ("1".equals(option)) {
            queryService.query();
        } else if ("2".equals(option)) {
            reportService.report();
        } else {
            System.out.println("Invalid option.");
        }
    }

}
