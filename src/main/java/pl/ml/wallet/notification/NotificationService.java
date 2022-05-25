package pl.ml.wallet.notification;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    public void setFinishedNotification(Notification notification) {
        notification.setFinished(true);
    }

    public List<Notification> findAllByFinished(boolean finished) {
        return notificationRepository.findAllByFinished(finished);
    }
}
