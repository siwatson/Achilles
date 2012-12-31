package integration.tests.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import fr.doan.achilles.annotations.Key;
import fr.doan.achilles.annotations.Lazy;
import fr.doan.achilles.entity.type.MultiKey;
import fr.doan.achilles.entity.type.WideMap;

@Table
public class CompleteBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column
    private String name;

    private String label;

    @Column(name = "age_in_years")
    private Long age;

    @Lazy
    @Column
    private List<String> friends;

    @Column
    private Set<String> followers;

    @Column
    private Map<Integer, String> preferences;

    @Column
    private WideMap<UUID, String> tweets;

    @Column
    private WideMap<UserTweetKey, String> userTweets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }

    public Map<Integer, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<Integer, String> preferences) {
        this.preferences = preferences;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public WideMap<UUID, String> getTweets() {
        return tweets;
    }

    public void setTweets(WideMap<UUID, String> tweets) {
        this.tweets = tweets;
    }

    public WideMap<UserTweetKey, String> getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(WideMap<UserTweetKey, String> userTweets) {
        this.userTweets = userTweets;
    }

    public static class UserTweetKey implements MultiKey {
        @Key(order = 1)
        private String user;

        @Key(order = 2)
        private UUID tweet;

        public UserTweetKey() {
        }

        public UserTweetKey(String user, UUID tweet) {
            super();
            this.user = user;
            this.tweet = tweet;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public UUID getTweet() {
            return tweet;
        }

        public void setTweet(UUID tweet) {
            this.tweet = tweet;
        }

    }
}
