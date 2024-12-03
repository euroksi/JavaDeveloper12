package org.example;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketCrudService {

    public Ticket saveTicket(Ticket ticket) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(ticket);
            transaction.commit();
            return ticket;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to save ticket", e);
        } finally {
            session.close();
        }
    }

    public Ticket findTicketById(Long id) {
        Session session = HibernateUtil.getSession();
        try {
            return session.get(Ticket.class, id);
        } finally {
            session.close();
        }
    }

    public List<Ticket> findAllTickets() {
        Session session = HibernateUtil.getSession();
        try {
            return session.createQuery("from Ticket", Ticket.class).list();
        } finally {
            session.close();
        }
    }

    public void deleteTicket(Long id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                session.delete(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to delete ticket", e);
        } finally {
            session.close();
        }
    }
}
