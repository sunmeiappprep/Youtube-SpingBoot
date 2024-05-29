package ca.myapp.service;
import ca.myapp.models.Subscription;
import ca.myapp.models.User;
import ca.myapp.repository.SubscriptionRepository;
import ca.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public Subscription subscribe(Long subscriberId, Long channelId) {
        if (subscriberId.equals(channelId)) {
            throw new IllegalStateException("Cannot subscribe to yourself");
        }

        Subscription existingSubscription = subscriptionRepository.findBySubscriberIdAndChannelId(subscriberId, channelId);
        if (existingSubscription != null) {
            // Unsubscribe the user if already subscribed
            subscriptionRepository.delete(existingSubscription);
            return existingSubscription;
        }

        Optional<User> subscriberOpt = userRepository.findById(subscriberId);
        Optional<User> channelOpt = userRepository.findById(channelId);

        if (subscriberOpt.isEmpty() || channelOpt.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriberOpt.get());
        subscription.setChannel(channelOpt.get());
        subscription.setSubscribedAt(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    public List<User> getSubscribedChannels(Long subscriberId) {
        return subscriptionRepository.findChannelsBySubscriberId(subscriberId);
    }


    public void unsubscribe(Long subscriberId, Long channelId) {
        Subscription subscription = subscriptionRepository.findBySubscriberIdAndChannelId(subscriberId, channelId);
        if (subscription != null) {
            subscriptionRepository.delete(subscription);
        }
    }

    public List<Subscription> getSubscriptions(Long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

    public List<Subscription> getSubscribers(Long channelId) {
        return subscriptionRepository.findByChannelId(channelId);
    }

    public boolean isSubscribed(Long subscriberId, Long channelId) {
        return subscriptionRepository.existsBySubscriberIdAndChannelId(subscriberId, channelId);
    }

    public long countSubscribers(Long channelId) {
        return subscriptionRepository.countByChannelId(channelId);
    }

    public long countSubscriptions(Long subscriberId) {
        return subscriptionRepository.countBySubscriberId(subscriberId);
    }
}