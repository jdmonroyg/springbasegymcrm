package com.epam;

import com.epam.config.ApplicationConfig;
import com.epam.facade.PortalGymFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.time.LocalDate;

/**
 * @author jdmon on 25/07/2025
 * @project Default (Template) Project
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        PortalGymFacade portalGymFacade = context.getBean(PortalGymFacade.class);
        System.out.println();
        System.out.println("----Trainee Operations----");
        System.out.println();
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1993, 2,19),"Street 52c #85c - 20");
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1994,8,1),"Street 72c #32c - 22");
        portalGymFacade.updatedTrainee(6,LocalDate.of(1995,8,1),"");
        portalGymFacade.updatedTrainee(6,LocalDate.of(1995,8,1),"Street 72c #32c - 23");
        System.out.println(portalGymFacade.selectTrainee(6));
        System.out.println(portalGymFacade.selectTrainee(10));
        portalGymFacade.deleteTrainee(6);
        portalGymFacade.selectAllTrainees().forEach(System.out::println);

        System.out.println();
        System.out.println("----Trainer Operations----");
        System.out.println();
        portalGymFacade.createTrainer("Daniel", "Monroy","");
        portalGymFacade.createTrainer("Daniel", "Monroy","zumba");
        System.out.println(portalGymFacade.selectTrainer(4));
        portalGymFacade.selectAllTrainers().forEach(System.out::println);

        System.out.println();
        System.out.println("----Training Operations----");
        System.out.println();
        portalGymFacade.createTraining(3, 8, "",
                "zumba", LocalDate.of(2025,8,2),0);
        portalGymFacade.createTraining(99, 99, "Colombian Zumba",
                "zumba", LocalDate.of(2025,8,2),60);
        portalGymFacade.createTraining(3, 8, "Colombian Zumba",
                "zumba", LocalDate.of(2025,8,2),60);

        System.out.println(portalGymFacade.selectTraining(2));
        portalGymFacade.selectAllTrainings().forEach(System.out::println);
    }
}