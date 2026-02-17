/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Booking;
import jakarta.persistence.*;
import utils.JpaUtils;

public class BookingDAO {

    public boolean saveBooking(Booking b){

        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();

            Long conflict = em.createQuery("""
            SELECT COUNT(b)
            FROM Booking b
            WHERE b.room.roomId = :roomId
            AND b.status IN ('PENDING','CONFIRMED')
            AND :checkIn < b.checkOut
            AND :checkOut > b.checkIn
            """, Long.class)
            .setParameter("roomId", b.getRoom().getRoomId())
            .setParameter("checkIn", b.getCheckIn())
            .setParameter("checkOut", b.getCheckOut())
            .getSingleResult();

            if(conflict > 0){
                tx.rollback();
                return false;
            }

            em.persist(b);
            tx.commit();
            return true;

        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return false;
        }finally{
            em.close();
        }
    }

    public void confirm(int id){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Booking b = em.find(Booking.class,id);
        b.setStatus("CONFIRMED");
        tx.commit();
        em.close();
    }
}

