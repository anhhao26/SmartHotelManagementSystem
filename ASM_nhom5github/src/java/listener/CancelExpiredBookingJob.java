/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

import jakarta.persistence.EntityManager;

import java.util.Timer;
import java.util.TimerTask;

import utils.JpaUtils;

@WebListener
public class CancelExpiredBookingJob implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){

        new Timer().schedule(new TimerTask(){
            public void run(){
                EntityManager em = JpaUtils.getEntityManager();
                em.getTransaction().begin();

                em.createQuery("""
                    UPDATE Booking b
                    SET b.status='EXPIRED'
                    WHERE b.status='PENDING'
                    AND b.createdAt < CURRENT_TIMESTAMP - 600
                """).executeUpdate();

                em.getTransaction().commit();
                em.close();
            }
        },0,60000);
    }
}

