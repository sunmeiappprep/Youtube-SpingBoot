package ca.myapp.controllers;

import ca.myapp.models.Subscription;
import ca.myapp.models.User;
import ca.myapp.service.SubscriptionService;
import ca.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @PostMapping("/subscribe")
    public Subscription subscribe(@RequestParam Long channelId) {
        User currentUser = userService.getAuthenticatedUser();
        return subscriptionService.subscribe(currentUser.getId(), channelId);
    }

    @GetMapping("/check")
    public boolean checkIfSubscribed(@RequestParam Long channelId) {
        User currentUser = userService.getAuthenticatedUser();
        return subscriptionService.isSubscribed(currentUser.getId(), channelId);
    }

    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@RequestParam Long channelId) {
        User currentUser = userService.getAuthenticatedUser();
        subscriptionService.unsubscribe(currentUser.getId(), channelId);
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