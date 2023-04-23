package me.lofienjoyer.quiet.basemodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entity representing a user's profile
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String username;
    private String description;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserInfo user;

    @OneToMany(mappedBy = "profile")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Profile> following;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Profile> followed;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Post> likedPosts;

}
