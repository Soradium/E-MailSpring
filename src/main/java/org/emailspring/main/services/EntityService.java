package org.emailspring.main.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.emailspring.main.dto.MessageDTO;
import org.emailspring.main.entity.Authority;
import org.emailspring.main.entity.Message;
import org.emailspring.main.entity.User;
import org.emailspring.main.repositories.AuthorityRepository;
import org.emailspring.main.repositories.MessageRepository;
import org.emailspring.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService {
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private EntityManager entityManager;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }
    public void saveUserWithAuthority(User user, String authority) {
        userRepository.save(user);
        Authority a = new Authority();
        a.setAuthority(authority);
        a.setUser(user);
        authorityRepository.save(a);
    }
    public User getUser(String username) {
         Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username");
         query.setParameter("username", username);
         return (User) query.getSingleResult();
    }
    public List<Message> getAllSentMessagesPerUser(User user) {
        Query query = entityManager.createQuery(
                "SELECT m FROM Message m JOIN FETCH m.userSender WHERE m.userSender.username = :user"
        );
        query.setParameter("user", user.getUsername());
        return query.getResultList();
    }
    public List<Message> getAllReceivedMessagesPerUser(User user) {
        Query query = entityManager.createQuery(
                "SELECT m FROM Message m JOIN FETCH m.userReceiver WHERE m.userReceiver.username = :user"
        );
        query.setParameter("user", user.getUsername());
        return query.getResultList();
    }
    public void sendMessage(Message message) {
        User sender = entityManager.find(User.class, message.getUserSender().getUsername());
        User receiver = entityManager.find(User.class, message.getUserReceiver().getUsername());

        message.setUserSender(sender);
        message.setUserReceiver(receiver);

        messageRepository.save(message);
    }

}
