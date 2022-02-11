package peaksoft;

import org.hibernate.Session;
import peaksoft.entity.Student;
import peaksoft.util.HibernateUtil;

import javax.persistence.Query;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {

        System.out.println(getStudentName(20, "Aza"));
        //update("Aza", 18);
        //delete("Aza");
    }

    public static int create(Student student) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();
        session.close();
        System.out.println("Добавлен: " + student);
        return student.getId();
    }

    public static Student getById(int id) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.getTransaction().commit();
        session.close();
        return student;
    }

    public static List<Student> getAllStudents() {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        List<Student> studentList = session.createQuery("from Student ").getResultList();
        session.getTransaction().commit();
        System.out.println("Finded: " + studentList.size() + " students ");
        return studentList;
    }

    public static void delete(int id) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.delete(student);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted " + student);
    }



    /*--------------------------------------------------Домашка---------------------------------------*/

    /**
     * 1. Аты Аза жана жашы 20 дан чон болгон баардык жумушчуларды алыныз.
     */

    public static List<Student> getStudentName(int ageStudent, String firstName) throws SQLException {
        Session session = null;
        List<Student> studentList = null;
        try {
            String sql = "FROM Student WHERE  name =:firstName and  age >:ageStudent";
            session = HibernateUtil.getSession().openSession();
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setParameter("ageStudent", ageStudent);
            query.setParameter("firstName", firstName);
            studentList = query.getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return studentList;
    }

    /**
     * 2. Аты Аза болгон жумушчулардын жашын 18ге озгортунуз.
     */

    public static void update(String firstName, int age) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSession().openSession();
            session.beginTransaction();
            String hql = "update Student student SET age = :age WHERE name = :firstName";
            Query query = session.createQuery(hql);
            query.setParameter("firstName", firstName);
            query.setParameter("age", age);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Студентам  с именем " + firstName + " по " + age + " лет");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * 3. Аты Аза болгон жумушчуларды очурунуз.
     */

    public static void delete(String name) throws SQLException {

        Session session = null;
        try {
            session = HibernateUtil.getSession().openSession();
            session.beginTransaction();
            Query query = session.createQuery("delete Student s where name = :name");
            query.setParameter("name", name);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Студенты с именем " + name + " удалены");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
