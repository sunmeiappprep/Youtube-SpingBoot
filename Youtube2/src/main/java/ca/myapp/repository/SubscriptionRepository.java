package ca.myapp.repository;
import ca.myapp.models.Subscription;
import ca.myapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findBySubscriberId(Long subscriberId);
    List<Subscription> findByChannelId(Long channelId);
    boolean existsBySubscriberIdAndChannelId(Long subscriberId, Long channelId);

    Subscription findBySubscriberIdAndChannelId(Long subscriberId, Long channelId);
    long countByChannelId(Long channelId);
    long countBySubscriberId(Long subscriberId);

    @Query("SELECT s.channel.id FROM Subscription s WHERE s.subscriber.id = :subscriberId")
    List<Long> findChannelIdsBySubscriberId(@Param("subscriberId") Long subscriberId);

    @Query("SELECT s.channel FROM Subscription s WHERE s.subscriber.id = :subscriberId")
    List<User> findChannelsBySubscriberId(@Param("subscriberId") Long subscriberId);
}
