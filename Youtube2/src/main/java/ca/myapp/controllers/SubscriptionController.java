package ca.myapp.controllers;

import ca.myapp.models.Subscription;
import ca.myapp.models.User;
import ca.myapp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public Subscription subscribe(@RequestParam Long subscriberId, @RequestParam Long channelId) {
        return subscriptionService.subscribe(subscriberId, channelId);
    }

    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@RequestParam Long subscriberId, @RequestParam Long channelId) {
        subscriptionService.unsubscribe(subscriberId, channelId);
    }

    @GetMapping("/subscriber/{subscriberId}")
    public List<Subscription> getSubscriptions(@PathVariable Long subscriberId) {
        return subscriptionService.getSubscriptions(subscriberId);
    }

    @GetMapping("/channel/{channelId}")
    public List<Subscription> getSubscribers(@PathVariable Long channelId) {
        return subscriptionService.getSubscribers(channelId);
    }

    @GetMapping("/{subscriberId}/channels")
    public List<User> getSubscribedChannels(@PathVariable Long subscriberId) {
        return subscriptionService.getSubscribedChannels(subscriberId);
    }
}